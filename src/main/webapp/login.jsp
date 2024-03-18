<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
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
            width: 120px;
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

        .success {
            background-color: #dff0d8;
            border: 1px solid #3c763d;
            padding: 10px;
            margin-bottom: 30px;
            font-size: 80%;
        }
        .error {
            background-color: #f2dede;
            border: 1px solid #a94442;
            padding: 10px;
            margin-bottom: 30px;
            font-size: 80%;
        }
    </style>
</head>
<body>

<form id="loginForm" action="BankUserLoginServlet" method="post">
    <div id="messageContainer"></div>
    <div class="form-row">
        <label for="username" class="label">Username</label>
        <span class="colon">:</span>
        <input type="text" id="username" name="username"/>
    </div>
    <div class="form-row">
        <label for="password" class="label">Password</label>
        <span class="colon">:</span>
        <input type="password" id="password" name="password"/>
    </div>

    <input id="login" type="submit" value="Login"/>
    <input id="register" type="submit" formaction="BankUserRegisterServlet" value="Register"/>
</form>
<script>
    const messageType = '<%= request.getAttribute("messageType") %>';
    const messageText = '<%= request.getAttribute("messageText") %>';

    if (messageType === "success") {
        document.getElementById("messageContainer").innerHTML = '<div class="success">' + messageText + '</div>';
    } else if (messageType === "error") {
        document.getElementById("messageContainer").innerHTML = '<div class="error">' + messageText + '</div>';
    }
</script>
</body>
</html>
