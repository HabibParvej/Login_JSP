<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forgot Password</title>
    <style>
        body { font-family: Arial; background: #f0f0f0; }
        .container { width: 400px; margin: 100px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        input[type="email"] { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #f44336; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #da190b; }
        .info { text-align: center; color: #666; margin: 15px 0; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Forgot Password</h2>
        <% 
            String error = request.getParameter("error");
            if(error != null) { 
        %>
            <p class="error"><%= error %></p>
        <% } %>
        
        <p class="info">Enter your registered email to receive OTP</p>
        
        <form action="ForgotPasswordServlet" method="post">
            <input type="email" name="email" placeholder="Enter your email" required>
            <button type="submit">Send OTP</button>
        </form>
        
        <div class="info">
            <a href="login.jsp">Back to Login</a>
        </div>
    </div>
</body>
</html>
