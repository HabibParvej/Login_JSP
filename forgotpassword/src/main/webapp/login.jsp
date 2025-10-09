<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        body { font-family: Arial; background: #f0f0f0; }
        .container { width: 400px; margin: 100px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        input[type="text"], input[type="password"], input[type="email"] { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background: #4CAF50; color: white; border: none; border-radius: 5px; cursor: pointer; margin: 5px 0; }
        button:hover { background: #45a049; }
        .link { text-align: center; margin-top: 15px; }
        .error { color: red; text-align: center; }
        .success { color: green; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Login</h2>
        <% 
            String error = request.getParameter("error");
            String success = request.getParameter("success");
            if(error != null) { 
        %>
            <p class="error"><%= error %></p>
        <% } 
            if(success != null) { 
        %>
            <p class="success"><%= success %></p>
        <% } %>
        
        <form action="LoginServlet" method="post">
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required>
            <button type="submit">Login</button>
        </form>
        
        <div class="link">
            <a href="register.jsp">New User? Register Here</a><br>
            <a href="forgotPassword.jsp">Forgot Password?</a>
        </div>
    </div>
</body>
</html>
