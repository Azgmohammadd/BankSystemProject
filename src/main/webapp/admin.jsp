<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Admin</title>
        <base href="/BankSystemProject/" target="_blank" />
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <script src="js/jquery-3.6.0.min.js" />
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">
                <div class="col-md-8 my-3">
                    <a href="${pageContext.request.contextPath}/logout" >logout</a>
                    <h4 class="display-4">All Accounts</h4>
                </div>
            </div>
            <div class="row">

            <c:forEach var="user" items="${users}">
                    <div class="col-md-4">
                        <div class="card my-3 p-0">
                            <div class="card-header">
                                <p class="card-title">Username:
                                    <span class="text-primary">
                                        ${user.username}
                                    </span>
                                </p>
                                <p class="card-title">Full Name:
                                    <span class="text-primary">
                                         ${user.firstName} ${user.lastName}
                                    </span>
                                </p>
                            </div>
                            <div class="card-body">
                                <p class="card-text">
                                    National Code: ${user.nationalCode}
                                </p>
                                <p class="card-text">
                                    Accounts Number:
                                </p>
                                <ul>
                                    <c:forEach var="accountNumber" items="${user.bankAccountsId}">
                                        <li class="font-weight-bold">
                                            ${accountNumber}
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                            <div class="card-footer">
                                <a class="card-link" href="/BankSystemProject/user?username=${user.username}">User page</a>
                            </div>
                        </div>
                    </div>
            </c:forEach>
            </div>
        </div>
    </body>
</html>
