<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Check if user is logged in
    if(session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    String username = (String) session.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome - Dashboard</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body { 
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            padding: 20px;
        }
        
        .container { 
            width: 100%;
            max-width: 600px;
            background: white;
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 10px 40px rgba(0,0,0,0.2);
            text-align: center;
        }
        
        .welcome-icon {
            font-size: 60px;
            margin-bottom: 20px;
        }
        
        h1 { 
            color: #333;
            margin-bottom: 10px;
            font-size: 32px;
        }
        
        .username {
            color: #667eea;
            font-weight: bold;
        }
        
        .success-message {
            color: #4CAF50;
            background: #e8f5e9;
            padding: 15px;
            border-radius: 8px;
            margin: 20px 0;
            border-left: 4px solid #4CAF50;
        }
        
        .info-box {
            background: #f5f5f5;
            padding: 20px;
            border-radius: 10px;
            margin: 25px 0;
            text-align: left;
        }
        
        .info-box h3 {
            color: #555;
            margin-bottom: 15px;
            font-size: 18px;
        }
        
        .info-item {
            display: flex;
            justify-content: space-between;
            padding: 10px 0;
            border-bottom: 1px solid #ddd;
        }
        
        .info-item:last-child {
            border-bottom: none;
        }
        
        .info-label {
            color: #666;
            font-weight: 500;
        }
        
        .info-value {
            color: #333;
            font-weight: 600;
        }
        
        .btn-container {
            margin-top: 30px;
            display: flex;
            gap: 15px;
            justify-content: center;
        }
        
        button, .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            font-weight: 500;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }
        
        .logout-btn { 
            background: #f44336;
            color: white;
        }
        
        .logout-btn:hover { 
            background: #da190b;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(244, 67, 54, 0.3);
        }
        
        .profile-btn {
            background: #2196F3;
            color: white;
        }
        
        .profile-btn:hover {
            background: #0b7dda;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(33, 150, 243, 0.3);
        }
        
        .footer {
            margin-top: 30px;
            color: #999;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="welcome-icon">👋</div>
        <h1>Welcome, <span class="username"><%= username %></span>!</h1>
        
        <div class="success-message">
            ✓ You have successfully logged into the system
        </div>
        
        <div class="info-box">
            <h3>Session Information</h3>
            <div class="info-item">
                <span class="info-label">Username:</span>
                <span class="info-value"><%= username %></span>
            </div>
            <div class="info-item">
                <span class="info-label">Session ID:</span>
                <span class="info-value"><%= session.getId().substring(0, 12) %>...</span>
            </div>
            <div class="info-item">
                <span class="info-label">Login Time:</span>
                <span class="info-value"><%= new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date()) %></span>
            </div>
        </div>
        
        <p style="color: #666; margin: 20px 0;">
            Your account is now active. You can access all features of the application.
        </p>
        
        <div class="btn-container">
            <button class="profile-btn" onclick="alert('Profile page coming soon!')">
                View Profile
            </button>
            <form action="LogoutServlet" method="post" style="display: inline;">
                <button type="submit" class="logout-btn">Logout</button>
            </form>
        </div>
        
        <div class="footer">
            <p>© 2025 Forgot Password System. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
