<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>

<html>
    <head>
        <title><c:out value="${username} page" /></title>
        <style>
            .info-icon {
                cursor: pointer;
            }
        </style>
        <base href="/BankSystemProject/" target="_blank" />
        <link rel="stylesheet" href="css/bootstrap.min.css" />
        <script src="js/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script src="js/script.js"></script>
    </head>
    <body>
        <div class="container py-2 my-2">
            <h4 class="display-4"><c:out value="${username} accounts" /></h4>
            <c:if test="${empty accounts}">
                <a href="${pageContext.request.contextPath}/accounts?username=${username}" class="text-muted">Create an account</a>

            </c:if>
            <section>
                <div class="row">
                    <c:forEach var="account" items="${accounts}">
                        <div class="col-md-4 card p-0 my-3">
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
                <div class="modal-dialog my-3" role="document" style="width:auto">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="detailPopupLabel"></h5>
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

                                    <button class="btn btn-warning btn-block" type="button" data-toggle="collapse" data-target="#withdrawCollapse" aria-expanded="false" aria-controls="withdrawCollapse">
                                        withdraw
                                    </button>
                                    <div class="collapse" id="withdrawCollapse">
                                        <div class="card card-body">
                                            <form action="${pageContext.request.contextPath}/transaction/withdraw" method="post">
                                                <div class="form-group">
                                                    <label for="withdrawInput">Amount</label>
                                                    <input type="text" class="form-control" id="withdrawInput" name="amount" placeholder="Enter amount">
                                                    <input type="hidden" class="hidden_acc_id"  name="selectedAccountNumber" value="${account.accountNumber}" />
                                                    <small id="balanceWarning" class="form-text text-muted">Please make sure you have enough balance</small>
                                                </div>
                                                <input type="submit" class="btn btn-warning btn-block" value="Submit" />
                                            </form>
                                        </div>
                                    </div>

                                    <button class="btn btn-success btn-block my-2" type="button" data-toggle="collapse" data-target="#depositCollapse" aria-expanded="false" aria-controls="depositCollapse">
                                        deposit
                                    </button>
                                    <div class="collapse" id="depositCollapse">
                                        <div class="card card-body">
                                            <form action="${pageContext.request.contextPath}/transaction/deposit" method="post">
                                                <div class="form-group">
                                                    <label for="depositInput">Amount</label>
                                                        <input type="text" class="form-control" id="depositInput" name="amount" placeholder="Enter amount">
                                                        <input type="hidden" class="hidden_acc_id" name="selectedAccountNumber" value="${account.accountNumber}" />
                                                </div>
                                                <input type="submit" class="btn btn-success btn-block" value="Submit" />
                                            </form>
                                        </div>
                                    </div>

                                    <form action="${pageContext.request.contextPath}/transaction/balance" method="post">
                                        <div class="form-group">
                                            <input type="hidden" class="hidden_acc_id" name="selectedAccountNumber" value="${account.accountNumber}" />
                                            <input type="hidden" class="form-control" id="balanceInput" name="amount" value="0">
                                            <input type="submit" class="btn btn-info btn-block" value="balance" />
                                        </div>
                                    </form>

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