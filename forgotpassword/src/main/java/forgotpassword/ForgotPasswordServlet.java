package forgotpassword;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String email = request.getParameter("email");
        
        UserDAO userDAO = new UserDAO();
        if (!userDAO.emailExists(email)) {
            response.sendRedirect("forgotPassword.jsp?error=Email not found");
            return;
        }
        
        String otp = EmailUtil.generateOTP();
        OTPDao otpDao = new OTPDao();
        if (otpDao.saveOTP(email, otp, "PASSWORD_RESET")) {
            EmailUtil.sendOTPEmail(email, otp, "PASSWORD_RESET");
            HttpSession session = request.getSession();
            session.setAttribute("verifyEmail", email);
            session.setAttribute("otpPurpose", "PASSWORD_RESET");
            response.sendRedirect("verifyOTP.jsp");
        } else {
            response.sendRedirect("forgotPassword.jsp?error=Failed to send OTP");
        }
    }
}
