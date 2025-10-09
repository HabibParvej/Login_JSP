package forgotpassword;

import java.util.Properties;
import java.util.Random;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {
    
    public static String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
    
    public static boolean sendOTPEmail(String toEmail, String otp, String purpose) {
        final String fromEmail = "thelaughingmachine26@gmail.com";
        final String password = "zazi zeav ragl hqec ";
        
        String subject = "";
        String body = "";
        
        if("REGISTRATION".equals(purpose)) {
            subject = "Email Verification OTP";
            body = "Your OTP for email verification is: " + otp + "\n\nThis OTP is valid for 10 minutes.";
        } else if("PASSWORD_RESET".equals(purpose)) {
            subject = "Password Reset OTP";
            body = "Your OTP for password reset is: " + otp + "\n\nThis OTP is valid for 10 minutes.";
        }
        
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
