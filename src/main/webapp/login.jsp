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
  Username: <input type="text" name="username" /> <br/>
  Password: <input type="password" name="password" /> <br/>
  <input id="login" type="submit" value="Login" />
  <input id="register" type="submit" formaction="BankUserRegisterServlet" value="Register" />
</form>
<div id="message"></div>
</body>
</html>
