<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Transaction Detail</title>
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </head>
    <body>
        <div class="row d-flex justify-content-center align-items-center">
            <div class="col-md-4 card">
                <div class="card-header">
                    <p class="card-title display-4 text-center text-secondary">
                        Transaction Detail
                    </p>
                </div>
                <div class="card-body">
                    <div class=" card-text d-flex justify-content-between">
                        <p class="text-info">source account number</p>
                        <p class=>${transaction_detail.sourceAccountNumber}</p>
                    </div>
                    <div class=" card-text d-flex justify-content-between">
                        <p class="text-info">Date</p>
                        <p class="card-text">${transaction_detail.transactionDate}</p>
                    </div>
                    <div class=" card-text d-flex justify-content-between">
                        <p class="text-info">transactionId</p>
                        <p class="card-text">${transaction_detail.transactionId}</p>
                    </div>
                    <div class=" card-text d-flex justify-content-between">
                        <p class="text-info">amount</p>
                        <p class="card-text">${transaction_detail.amount}</p>
                    </div>
                    <div class=" card-text d-flex justify-content-between">
                        <p class="text-info">type</p>
                        <p class="card-text">${transaction_detail.transactionType}</p>
                    </div>
                    <div class=" card-text d-flex justify-content-between">
                        <p class="text-info">status</p>
                        <p class="card-text">${transaction_detail.status}</p>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
