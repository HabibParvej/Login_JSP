# JSP Login System with Email OTP Verification

A secure authentication system built with JSP, Servlets, MySQL, and JavaMail API featuring user registration, login, password reset, and email-based OTP verification.

![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white)
![JSP](https://img.shields.io/badge/JSP-007396?style=flat&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white)
![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=flat&logo=apache-tomcat&logoColor=black)

---

## Table of Contents

- [Problem Statement](#problem-statement)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [System Architecture](#system-architecture)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Gmail Configuration](#gmail-configuration)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Application Flow](#application-flow)
- [Screenshots](#screenshots)
- [Troubleshooting](#troubleshooting)
- [Future Enhancements](#future-enhancements)
- [Contributing](#contributing)
- [License](#license)

---

## Problem Statement

In modern web applications, user authentication and account security are critical concerns. Traditional password-only authentication systems are vulnerable to various security threats. This project addresses the need for:

- **Secure user authentication** with email verification
- **Password recovery mechanism** without compromising security
- **Prevention of unauthorized access** through OTP-based verification
- **User-friendly interface** for registration and login processes
- **Time-bound OTP validation** to prevent replay attacks

The system implements a multi-layered security approach using **One-Time Passwords (OTP)** sent via email to verify user identity during registration and password reset operations.

---

## Features

### 1. User Registration
- Create new user accounts with email verification
- Password confirmation validation
- Duplicate email detection
- 6-digit OTP sent to registered email
- OTP expiration after 10 minutes
- Account activation only after email verification

### 2. User Login
- Secure authentication with email and password
- Session management
- Access only for verified users
- Protection against unauthorized access

### 3. Forgot Password
- Password reset functionality via email
- OTP-based identity verification
- Secure password update mechanism
- Old OTP invalidation when new OTP is requested

### 4. OTP Verification
- 6-digit numeric OTP generation
- Email delivery via Gmail SMTP
- Time-bound validity (10 minutes)
- Resend OTP functionality
- One-time use validation
- Automatic cleanup of expired OTPs

### 5. Security Features
- Email-based authentication
- Session timeout management
- SQL injection prevention (PreparedStatements)
- OTP expiration mechanism
- Password validation
- Secure logout functionality

---

## Technologies Used

| Technology | Version | Purpose |
|------------|---------|---------|
| **Java** | 8+ | Backend logic and servlets |
| **JSP** | 2.3 | Dynamic web pages |
| **Servlets** | 3.1 | HTTP request handling |
| **MySQL** | 5.7/8.0 | Database management |
| **JDBC** | 4.0+ | Database connectivity |
| **JavaMail API** | 1.6.2 | Email OTP delivery |
| **Apache Tomcat** | 9.0 | Application server |
| **HTML5/CSS3** | - | Frontend design |
| **Eclipse IDE** | 2020+ | Development environment |

---

---

## Prerequisites

Before you begin, ensure you have the following installed:

- **Java Development Kit (JDK)** 8 or higher
  - [Download JDK](https://www.oracle.com/java/technologies/downloads/)
- **Apache Tomcat** 9.0
  - [Download Tomcat](https://tomcat.apache.org/download-90.cgi)
- **MySQL Server** 5.7 or 8.0
  - [Download MySQL](https://dev.mysql.com/downloads/mysql/)
- **Eclipse IDE** (or any Java IDE)
  - [Download Eclipse](https://www.eclipse.org/downloads/)
- **Gmail Account** with 2-Step Verification enabled
- **Internet Connection** for sending emails

---

## Installation

### Step 1: Clone or Download the Project
git clone https://github.com/HabibParvej/Login_JSP


Or download as ZIP and extract.

### Step 2: Import Project into Eclipse

1. Open **Eclipse IDE**
2. Go to **File → Import → Existing Projects into Workspace**
3. Select the project folder
4. Click **Finish**

### Step 3: Add Required JAR Files

Download and add these JAR files to **WebContent/WEB-INF/lib/**:

1. **MySQL Connector** (version 8.0.x)
   - [Download](https://dev.mysql.com/downloads/connector/j/)
   - File: `mysql-connector-java-8.0.x.jar`

2. **JavaMail API** (version 1.6.2)
   - [Download](https://repo1.maven.org/maven2/com/sun/mail/javax.mail/1.6.2/javax.mail-1.6.2.jar)
   - File: `javax.mail-1.6.2.jar`

3. **Java Activation Framework** (version 1.1.1)
   - [Download](https://repo1.maven.org/maven2/javax/activation/activation/1.1.1/activation-1.1.1.jar)
   - File: `activation-1.1.1.jar`

After adding JARs:
- Right-click project → **Refresh**
- Right-click project → **Properties → Java Build Path → Libraries**
- Verify JARs are listed

---

## Database Setup

### Step 1: Create Database

Open MySQL Command Line or MySQL Workbench and execute:


### Step 2: Create Users Table

CREATE TABLE IF NOT EXISTS users (
user_id INT PRIMARY KEY AUTO_INCREMENT,
username VARCHAR(50) UNIQUE NOT NULL,
email VARCHAR(100) UNIQUE NOT NULL,
password VARCHAR(255) NOT NULL,
phone VARCHAR(15),
is_verified BOOLEAN DEFAULT FALSE,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

### Step 3: Create OTP Verification Table

CREATE TABLE IF NOT EXISTS otp_verification (
otp_id INT PRIMARY KEY AUTO_INCREMENT,
email VARCHAR(100) NOT NULL,
otp_code VARCHAR(6) NOT NULL,
purpose VARCHAR(20) NOT NULL,
created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
expires_at TIMESTAMP NOT NULL,
is_used BOOLEAN DEFAULT FALSE
);

### Step 4: Verify Tables
SHOW TABLES;
DESCRIBE users;
DESCRIBE otp_verification;

### Step 5: Configure Database Connection

Edit **src/forgotpassword/DBConnection.java**:

useSSL=false&serverTimezone=Asia/Kolkata";
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password"; // Change this!

---

## Gmail Configuration

### Step 1: Enable 2-Step Verification

1. Go to [Google Account Security](https://myaccount.google.com/security)
2. Scroll to **"2-Step Verification"**
3. Click **"Get Started"** and follow instructions

### Step 2: Generate App Password

1. Go to [App Passwords](https://myaccount.google.com/apppasswords)
2. Select **"Mail"** and **"Other"** (type "JavaMail")
3. Click **"Generate"**
4. Copy the **16-character password** (e.g., `abcd efgh ijkl mnop`)

### Step 3: Configure Email Settings

Edit **src/forgotpassword/EmailUtil.java**:

private static final String FROM_EMAIL = "your_email@gmail.com"; // Your Gmail
private static final String APP_PASSWORD = "xxxx xxxx xxxx xxxx"; // App Password

**Important:** Use the **App Password**, NOT your regular Gmail password!

---
## How to Run

### Method 1: Using Eclipse

1. **Configure Tomcat Server**
   - Window → Preferences → Server → Runtime Environments
   - Add → Apache Tomcat v9.0
   - Browse to Tomcat installation directory

2. **Add Project to Tomcat**
   - Right-click project → **Run As → Run on Server**
   - Select **Tomcat v9.0 Server**
   - Click **Finish**

3. **Access Application**
   - Browser automatically opens: `http://localhost:8080/forgotpassword/`
   - Or manually visit: `http://localhost:8080/forgotpassword/login.jsp`

### Method 2: Manual Deployment

1. **Export WAR File**
   - Right-click project → **Export → WAR file**
   - Choose destination and export

2. **Deploy to Tomcat**
   - Copy WAR file to `<TOMCAT_HOME>/webapps/`
   - Start Tomcat: `bin/startup.sh` (Linux) or `bin/startup.bat` (Windows)

3. **Access Application**
   - Visit: `http://localhost:8080/forgotpassword/`

---

## Application Flow

### Registration Flow

User visits register.jsp
↓

Fills registration form (username, email, phone, password)
↓

Submits form → RegisterServlet
↓

System checks if email already exists
↓

Creates user account (is_verified = false)
↓

Generates 6-digit OTP
↓

Saves OTP to database (expires in 10 minutes)
↓

Sends OTP to user's email via Gmail SMTP↓

Redirects to verifyOTP.jsp
↓

User enters OTP → VerifyOTPServlet
↓

System validates OTP (checks code, expiry, used status)
↓

If valid: Updates is_verified = true
↓

Redirects to login.jsp with success message
### Login Flow

User visits login.jsp
↓

Enters email and password
↓

Submits form → LoginServlet
↓

System validates credentials and checks is_verified = true
↓

If valid: Creates session with username
↓

Redirects to welcome.jsp (dashboard)
### Forgot Password Flow

User clicks "Forgot Password?" on login.jsp
↓

Redirects to forgotPassword.jsp
↓

User enters registered email
↓

Submits form → ForgotPasswordServlet
↓

System checks if email exists in database
↓

Generates new OTP
↓

Saves OTP with purpose = "PASSWORD_RESET"
↓

Sends OTP to user's email
↓

Redirects to verifyOTP.jspUser enters OTP → VerifyOTPServlet
↓

If valid: Redirects to resetPassword.jsp
↓

User enters new password → ResetPasswordServlet
↓

System updates password in database
↓

Redirects to login.jsp with success message
---

## Screenshots

### Login Page
The main entry point where users authenticate using email and password.

### Registration Page
New users create accounts by providing username, email, phone, and password.

### OTP Verification Page
Users enter the 6-digit OTP received via email to verify their identity.

### Welcome Dashboard
Personalized dashboard displayed after successful login.

### Password Reset Page
Secure interface for users to set a new password after OTP verification.

---

## Troubleshooting

### Common Issues and Solutions

#### 1. HTTP 500 - Database Connection Error

**Error:** `Cannot invoke "java.sql.Connection.prepareStatement(String)" because "conn" is null`

**Solution:**
- Ensure MySQL server is running
- Verify database name is `logindb`
- Check username/password in `DBConnection.java`
- Confirm `mysql-connector-java.jar` is in `WEB-INF/lib`
- Restart Tomcat after adding JAR

#### 2. HTTP 404 - Servlet Not Found

**Error:** `The requested resource [/forgotpassword/RegisterServlet] is not available`

**Solution:**
- Clean project: **Project → Clean**
- Remove and re-add project to Tomcat
- Restart Tomcat server
- Verify `@WebServlet` annotation exists on servlet

#### 3. OTP Not Received

**Possible Causes:**
- Gmail App Password not configured
- Using regular password instead of App Password
- 2-Step Verification not enabled
- Incorrect email configuration

**Solution:**
- Enable 2-Step Verification on Gmail
- Generate and use App Password (not regular password)
- Check spam/junk folder
- Enable debug mode in `EmailUtil.java` (`session.setDebug(true)`)
- Check Tomcat console for email sending logs

#### 4. OTP Invalid Even When Correct

**Possible Causes:**
- Timezone mismatch between Java and MySQL
- Multiple OTPs exist for same email
- OTP already used or expired

**Solution:**
- Add timezone to database URL: `serverTimezone=Asia/Kolkata`
- Clear old OTP records: `DELETE FROM otp_verification;`
- Check Tomcat console for debug logs
- Ensure OTP is entered within 10 minutes

#### 5. Session Expired Error

**Solution:**
- Increase session timeout in `web.xml`:




