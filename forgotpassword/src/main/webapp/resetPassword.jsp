<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password</title>
    <style>
        body { font-family: Arial; background: #f0f0f0; }
        .container { width: 400px; margin: 100px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        input[type="password"] { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #9C27B0; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #7B1FA2; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Reset Password</h2>
        <% 
            String error = request.getParameter("error");
            if(error != null) { 
        %>
            <p class="error"><%= error %></p>
        <% } %>
        
        <form action="ResetPasswordServlet" method="post">
            <input type="password" name="newPassword" placeholder="New Password" minlength="6" required>
            <input type="password" name="confirmPassword" placeholder="Confirm New Password" required>
            <button type="submit">Reset Password</button>
        </form>
    </div>
</body>
</html>
