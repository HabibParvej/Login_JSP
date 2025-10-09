package forgotpassword;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("resetEmail");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!newPassword.equals(confirmPassword)) {
            response.sendRedirect("resetPassword.jsp?error=Passwords do not match");
            return;
        }
        
        UserDAO userDAO = new UserDAO();
        if (userDAO.updatePassword(email, newPassword)) {
            session.invalidate();
            response.sendRedirect("login.jsp?success=Password reset successful! Please login");
        } else {
            response.sendRedirect("resetPassword.jsp?error=Password reset failed");
        }
    }
}
