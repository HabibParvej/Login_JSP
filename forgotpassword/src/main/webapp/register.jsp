<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <style>
        body { font-family: Arial; background: #f0f0f0; }
        .container { width: 400px; margin: 50px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        input[type="text"], input[type="password"], input[type="email"] { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #2196F3; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #0b7dda; }
        .link { text-align: center; margin-top: 15px; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Register</h2>
        <% 
            String error = request.getParameter("error");
            if(error != null) { 
        %>
            <p class="error"><%= error %></p>
        <% } %>
        
        <form action="RegisterServlet" method="post">
            <input type="text" name="username" placeholder="Username" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="text" name="phone" placeholder="Phone Number" required>
            <input type="password" name="password" placeholder="Password" minlength="6" required>
            <input type="password" name="confirmPassword" placeholder="Confirm Password" required>
            <button type="submit">Register</button>
        </form>
        
        <div class="link">
            <a href="login.jsp">Already have an account? Login</a>
        </div>
    </div>
</body>
</html>
