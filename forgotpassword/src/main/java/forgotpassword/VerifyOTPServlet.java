package forgotpassword;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/VerifyOTPServlet")
public class VerifyOTPServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("verifyEmail");
        String purpose = request.getParameter("purpose");
        String otp = request.getParameter("otp");
        
        OTPDao otpDao = new OTPDao();
        if (otpDao.verifyOTP(email, otp, purpose)) {
            if ("REGISTRATION".equals(purpose)) {
                UserDAO userDAO = new UserDAO();
                userDAO.verifyUserEmail(email);
                response.sendRedirect("login.jsp?success=Registration successful! Please login");
            } else if ("PASSWORD_RESET".equals(purpose)) {
                session.setAttribute("resetEmail", email);
                response.sendRedirect("resetPassword.jsp");
            }
        } else {
            response.sendRedirect("verifyOTP.jsp?error=Invalid or expired OTP");
        }
    }
}
