package forgotpassword;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ResendOTPServlet")
public class ResendOTPServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Handles GET requests - when user clicks "Resend OTP" link
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        // Check if session exists
        if (session == null) {
            response.sendRedirect("login.jsp?error=Session expired. Please try again");
            return;
        }
        
        // Get email and purpose from session
        String email = (String) session.getAttribute("verifyEmail");
        String purpose = (String) session.getAttribute("otpPurpose");
        
        // Validate session data
        if (email == null || email.isEmpty()) {
            response.sendRedirect("login.jsp?error=Invalid session. Please register again");
            return;
        }
        
        if (purpose == null || purpose.isEmpty()) {
            purpose = "REGISTRATION"; // Default purpose
        }
        
        try {
            // Generate new OTP
            String newOTP = EmailUtil.generateOTP();
            System.out.println("Resending OTP: " + newOTP + " to: " + email);
            
            // Save new OTP to database
            OTPDao otpDao = new OTPDao();
            boolean otpSaved = otpDao.saveOTP(email, newOTP, purpose);
            
            if (otpSaved) {
                // Send email with new OTP
                boolean emailSent = EmailUtil.sendOTPEmail(email, newOTP, purpose);
                
                if (emailSent) {
                    System.out.println("✓ OTP resent successfully to: " + email);
                    response.sendRedirect("verifyOTP.jsp?success=OTP has been resent to your email");
                } else {
                    System.err.println("✗ Failed to send OTP email to: " + email);
                    response.sendRedirect("verifyOTP.jsp?error=Failed to send OTP. Please try again");
                }
            } else {
                System.err.println("✗ Failed to save OTP to database");
                response.sendRedirect("verifyOTP.jsp?error=System error. Please try again");
            }
            
        } catch (Exception e) {
            System.err.println("✗ Exception in ResendOTPServlet: " + e.getMessage());
            e.printStackTrace();
            response.sendRedirect("verifyOTP.jsp?error=An error occurred. Please try again");
        }
    }
    
    /**
     * Handles POST requests - redirects to doGet
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
