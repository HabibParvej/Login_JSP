package forgotpassword;

import java.sql.*;
import java.time.LocalDateTime;

public class OTPDao {
    
    // Mark all previous OTPs as used before saving new one
    public boolean saveOTP(String email, String otp, String purpose) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            
            // First, mark all previous OTPs for this email and purpose as used
            String invalidateQuery = "UPDATE otp_verification SET is_used = true WHERE email = ? AND purpose = ? AND is_used = false";
            PreparedStatement pstInvalidate = conn.prepareStatement(invalidateQuery);
            pstInvalidate.setString(1, email);
            pstInvalidate.setString(2, purpose);
            pstInvalidate.executeUpdate();
            pstInvalidate.close();
            
            // Now insert new OTP
            String insertQuery = "INSERT INTO otp_verification (email, otp_code, purpose, expires_at) VALUES (?, ?, ?, ?)";
            PreparedStatement pstInsert = conn.prepareStatement(insertQuery);
            pstInsert.setString(1, email);
            pstInsert.setString(2, otp);
            pstInsert.setString(3, purpose);
            pstInsert.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now().plusMinutes(10)));
            
            int result = pstInsert.executeUpdate();
            pstInsert.close();
            
            System.out.println("✓ OTP saved: " + otp + " for " + email);
            return result > 0;
            
        } catch (SQLException e) {
            System.err.println("✗ Failed to save OTP");
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public boolean verifyOTP(String email, String otp, String purpose) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            
            // Query to check valid OTP - removed timezone issues
            String query = "SELECT otp_id, otp_code, created_at, expires_at FROM otp_verification " +
                          "WHERE email = ? AND otp_code = ? AND purpose = ? AND is_used = false " +
                          "ORDER BY created_at DESC LIMIT 1";
            
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, email);
            pst.setString(2, otp);
            pst.setString(3, purpose);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                int otpId = rs.getInt("otp_id");
                Timestamp expiresAt = rs.getTimestamp("expires_at");
                Timestamp now = new Timestamp(System.currentTimeMillis());
                
                System.out.println("OTP Found - ID: " + otpId);
                System.out.println("Current Time: " + now);
                System.out.println("Expires At: " + expiresAt);
                
                // Check if OTP is still valid
                if (now.before(expiresAt)) {
                    markOTPAsUsed(otpId);
                    rs.close();
                    pst.close();
                    System.out.println("✓ OTP verified successfully");
                    return true;
                } else {
                    rs.close();
                    pst.close();
                    System.out.println("✗ OTP expired");
                    return false;
                }
            }
            
            rs.close();
            pst.close();
            System.out.println("✗ No matching OTP found for: " + email + " | OTP: " + otp);
            
            // Debug: Show what OTPs exist for this email
            debugShowOTPs(email, purpose);
            
        } catch (SQLException e) {
            System.err.println("✗ Error verifying OTP");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    
    private void markOTPAsUsed(int otpId) {
        String query = "UPDATE otp_verification SET is_used = true WHERE otp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, otpId);
            pst.executeUpdate();
            System.out.println("✓ OTP marked as used: " + otpId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Debug method to show all OTPs for an email
    private void debugShowOTPs(String email, String purpose) {
        String query = "SELECT otp_id, otp_code, purpose, is_used, created_at, expires_at " +
                      "FROM otp_verification WHERE email = ? AND purpose = ? ORDER BY created_at DESC LIMIT 5";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setString(1, email);
            pst.setString(2, purpose);
            ResultSet rs = pst.executeQuery();
            
            System.out.println("=== DEBUG: Recent OTPs for " + email + " ===");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("otp_id") + 
                                 " | OTP: " + rs.getString("otp_code") +
                                 " | Used: " + rs.getBoolean("is_used") +
                                 " | Expires: " + rs.getTimestamp("expires_at"));
            }
            System.out.println("===========================================");
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Clean up expired OTPs (optional - call periodically)
    public void cleanupExpiredOTPs() {
        String query = "DELETE FROM otp_verification WHERE expires_at < NOW()";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            int deleted = pst.executeUpdate();
            System.out.println("✓ Cleaned up " + deleted + " expired OTPs");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
