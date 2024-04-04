<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login Page</title>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/style.css" />
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </head>
<body>
<div class="content">
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <img src="images/undraw_remotely_2j6y.svg" alt="Image" class="img-fluid">
            </div>
            <div class="col-md-6 contents">
                <div class="row justify-content-center">
                    <div class="col-md-8">
                        <div class="mb-4 d-flex justify-content-center">
                            <h3>Sign In</h3>
                        </div>
                        <form id="loginForm" action="${pageContext.request.contextPath}/login" method="post">
                            <div id="messageContainer"></div>

                            <div class="form-group first">
                                <label for="username" class="label">Username</label>
                                <input type="text" id="username" name="username" class="form-control"/>
                            </div>
                            <div class="form-group last mb-4">
                                <label for="password" class="label">Password</label>
                                <input type="password" id="password" name="password" class="form-control" />
                            </div>
<%--                            <div class="d-flex mb-5 align-items-center">--%>
<%--                                <label class="control control--checkbox mb-0"><span class="caption">Remember me</span>--%>
<%--                                    <input type="checkbox" checked="checked">--%>
<%--                                    <div class="control__indicator"></div>--%>
<%--                                </label>--%>
<%--                                <span class="ml-auto"><a href="#" class="forgot-pass">Forgot Password</a></span>--%>
<%--                            </div>--%>
                            <input type="submit" value="Log In" class="btn btn-block btn-primary">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
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
