<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login Page</title>
    <style>
        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 10px;
        }
        .form-row {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        .label {
            width: 120px; /* Adjust as needed */
            text-align: right;
        }
        .colon {
            width: 10px;
            text-align: center;
        }
        input[type="submit"] {
            margin-top: 10px;
        }
        #login {
            background-color: blue;
            color: white;
        }
        #register {
            background-color: green;
            color: white;
        }
    </style>
</head>
<body>
<form action="BankUserLoginServlet" method="post">
    <div class="form-row">
        <label for="username" class="label">Username</label>
        <span class="colon">:</span>
        <input type="text" id="username" name="username" />
    </div>
    <div class="form-row">
        <label for="password" class="label">Password</label>
        <span class="colon">:</span>
        <input type="password" id="password" name="password" />
    </div>
    <div class="form-row">
        <label for="isAdmin" class="label">Admin User</label>
        <span class="colon">:</span>
        <input type="checkbox" id="isAdmin" name="isAdmin" value="true" />
    </div>
    <input id="login" type="submit" value="Login" />
    <input id="register" type="submit" formaction="BankUserRegisterServlet" value="Register" />
</form>
<div id="message"></div>
</body>
</html>
