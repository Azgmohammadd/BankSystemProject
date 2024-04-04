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
        <base href="/BankSystemProject/" target="_blank" />
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <script src="js/jquery-3.6.0.min.js" />
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script>
            function setSelectedAccount(accountNumber) {
                document.getElementById('selectedAccountNumber').value = accountNumber;
            }

            $(document).ready(function() {
                $('.info-icon').click(function() {
                    var accountNumber = $(this).closest('.card').find('.card-header').text().trim();
                    setSelectedAccount(accountNumber);
                    $('#detailPopupLabel').text(accountNumber);
                    $('#detailPopup').modal('show');
                    $.ajax({
                        url: 'http://localhost:8080/BankSystemProject/accountTransactions',
                        type: 'GET',
                        dataType: 'json',
                        success: function(data) {
                            // Construct HTML for transactions
                            var html = '';
                            for (var i = 0; i < data.length; i++) {
                                html += `<div class="card">
                                            <div class="card-header">
                                                <span class="card-title"></span>
                                                <p class="card-title">${data[i].transactionType}</p>
                                            </div>
                                            <div class="card-body">
                                                <p class="card-text">${new Date(data[i].transactionDate)}</p>
                                                <p class="card-text">${data[i].amount}$</p>
                                            </div>
                                            <div class="card-footer">`;
                                            if (data[i].transactionType === 'DEPOSITS') {
                                                html += `<a class="btn btn-success card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/${data[i].transactionId}/">detail</a>`;
                                            } else if (data[i].transactionType === 'WITHDRAWALS') {
                                                html += `<a class="btn btn-warning card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/${data[i].transactionId}/">detail</a>`;
                                            } else if (data[i].transactionType === 'GET_BALANCE') {
                                                html += `<a class="btn btn-info card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/${data[i].transactionId}/">detail</a>`;
                                            }
                                            html += `</div>
                                        </div>`;
                            }
                            // Insert HTML into the DOM
                            $('#transactions-container').html(html);                        },
                        error: function (xhr, status, error) {
                            console.error('Error fetching transactions:', error);
                        }
                    });

                });

                $('.transaction_detail_icon').click(function() {
                    $('#transaction_detail_popup').modal('show');
                });
            });
        </script>
    </head>
    <body>
    <div class="container py-2">
        <h2>User Accounts</h2>
        <section>
            <div class="row">
                <c:forEach var="account" items="${user}">
                    <div class="col-md-4 card p-0">
                            <div class="card-header">${account.accountNumber}</div>
                            <div class="card-body">
                                <div class="card-title">${account.accountHolderNumber}</div>
                                <div class="card-text">
                                    <p>Balance: ${account.balance}</p>
                                    <p>Type: ${account.type}</p>
                                </div>
                            </div>
                            <div class="card-footer d-flex justify-content-end">
                                <button class="btn btn-info info-icon" type="button">Select</button>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </section>

            <!-- Hidden field to store selected account number -->
            <input type="hidden" id="selectedAccountNumber" name="selectedAccountNumber" value="" />

            <!-- Detail Popup -->
            <div class="modal fade" id="detailPopup" tabindex="-1" role="dialog" aria-labelledby="detailPopupLabel" aria-hidden="true">
                <div class="modal-dialog my-3" role="document" style="width:750px;">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="detailPopupLabel">${account.accountNumber}</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <ul class="nav nav-tabs row">
                                <li class="col-6 nav-item active"><a class="nav-link" data-toggle="tab" href="#transactions">Transaction</a></li>
                                <li class="col-6 nav-item"><a class="nav-link" data-toggle="tab" href="#history">History</a></li>
                            </ul>

                            <div class="tab-content my-2">
                                <div id="transactions" class="tab-pane fade show active">
                                    <h3>Transaction</h3>
                                    <button type="button" class="btn btn-warning btn-block">withdraw</button>
                                    <button type="button" class="btn btn-success btn-block">deposit</button>
                                    <button type="button" class="btn btn-info btn-block">Balance</button>
                                </div>
                                <div id="history" class="tab-pane fade">
                                    <h3>History</h3>
                                    <div id="transactions-container"></div>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>