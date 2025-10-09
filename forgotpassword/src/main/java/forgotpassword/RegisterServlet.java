package forgotpassword;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!password.equals(confirmPassword)) {
            response.sendRedirect("register.jsp?error=Passwords do not match");
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.emailExists(email)) {
            response.sendRedirect("register.jsp?error=Email already registered");
            return;
        }
        
        User user = new User(username, email, password, phone);
        if (userDAO.registerUser(user)) {
            String otp = EmailUtil.generateOTP();
            OTPDao otpDao = new OTPDao();
            if (otpDao.saveOTP(email, otp, "REGISTRATION")) {
                EmailUtil.sendOTPEmail(email, otp, "REGISTRATION");
                HttpSession session = request.getSession();
                session.setAttribute("verifyEmail", email);
                session.setAttribute("otpPurpose", "REGISTRATION");
                response.sendRedirect("verifyOTP.jsp");
            }
        } else {
            response.sendRedirect("register.jsp?error=Registration failed");
        }
    }
}
