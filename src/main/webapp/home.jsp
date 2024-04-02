<%@ page import="com.java.banksystemproject.dao.impl.JDBC.TransactionDaoJDBC" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<html>
<head>
    <title>Hello page</title>
    <style>
        .info-icon {
            cursor: pointer;
        }
    </style>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>    <script>
        $(document).ready(function() {
            $('.info-icon').click(function() {
                $('#detailPopup').modal('show');
                <c:set var="id" value="IDDD" />
                <c:set var="transactions" value="<%= new TransactionDaoJDBC().getAll()%>" />
            });
        });
    </script>
</head>
<body>
<table class="table table-dark">
    <thead>
    <th>Account Number</th>
    <th>Account Holder Number</th>
    <th>Balance</th>
    <th>Type</th>
    <th>Detail</th>
    </thead>
    <tbody>
    <c:forEach var="account" items="${accounts}" >
        <tr>
            <td>${account.accountNumber}</td>
            <td>${account.accountHolderNumber}</td>
            <td>${account.balance}</td>
            <td>${account.type}</td>
            <td><span class="info-icon" data-account-id="${account.accountNumber}">&#9432;</span></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<!-- Detail Popup -->
<div class="modal fade" id="detailPopup" tabindex="-1" role="dialog" aria-labelledby="detailPopupLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="detailPopupLabel">Detail Information</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-warning">
                    <thead>
                        <th>transactionId</th>
                        <th>transactionType</th>
                        <th>amount</th>
                        <th>transactionDate</th>
                        <th>sourceAccountNumber</th>
                        <th>targetAccountNumber</th>
                        <th>status</th>
                        <th>fee</th>
                    </thead>
                    <tbody>
                    <c:forEach var="transaction" items="${transactions}" >
                        <tr>
                            <td>${transaction.transactionId}</td>
                            <td>${transaction.transactionType}</td>
                            <td>${transaction.amount}</td>
                            <td>${transaction.transactionDate}</td>
                            <td>${transaction.sourceAccountNumber}</td>
                            <td>${transaction.targetAccountNumber}</td>
                            <td>${transaction.status}</td>
                            <td>${transaction.fee}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

</body>
</html>
