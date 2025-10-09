<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Verify OTP</title>
    <style>
        body { font-family: Arial; background: #f0f0f0; }
        .container { width: 400px; margin: 100px auto; background: white; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }
        h2 { text-align: center; color: #333; }
        input[type="text"] { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ddd; border-radius: 5px; box-sizing: border-box; text-align: center; font-size: 24px; letter-spacing: 10px; }
        button { width: 100%; padding: 10px; background: #FF9800; color: white; border: none; border-radius: 5px; cursor: pointer; }
        button:hover { background: #e68900; }
        .info { text-align: center; color: #666; margin: 15px 0; }
        .error { color: red; text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Verify OTP</h2>
        <% 
            String email = (String) session.getAttribute("verifyEmail");
            String purpose = (String) session.getAttribute("otpPurpose");
            String error = request.getParameter("error");
            if(error != null) { 
        %>
            <p class="error"><%= error %></p>
        <% } %>
        
        <p class="info">Enter the 6-digit OTP sent to <%= email %></p>
        
        <form action="VerifyOTPServlet" method="post">
            <input type="text" name="otp" placeholder="Enter OTP" maxlength="6" required>
            <input type="hidden" name="purpose" value="<%= purpose %>">
            <button type="submit">Verify</button>
        </form>
        
        <div class="info">
            <a href="ResendOTPServlet">Resend OTP</a>
        </div>
    </div>
</body>
</html>
