<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>

<html>
<head>
    <title>Create Account</title>
    <base href="/BankSystemProject/" target="_blank" />
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <script src="js/jquery-3.6.0.min.js"></script>
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
                            <div class="mb-4 text-center">
                                <h3>Create Account</h3>
                            </div>
                            <form id="RegisterForm" action="${pageContext.request.contextPath}/accounts?username=${username}" method="post">
                                <div class="form-group first">
                                    <label for="accountNumber" class="label">Account Number</label>
                                    <input type="text" id="accountNumber" name="accountNumber" class="form-control"/>
                                </div>
                                <div class="form-group last mb-4">
                                    <label for="accountHolderNumber" class="label">Account Holder Number</label>
                                    <input type="text" id="accountHolderNumber" name="accountHolderNumber" class="form-control" />
                                </div>

                                <div class="form-group last mb-4">
                                    <label for="balance" class="label">Balance</label>
                                    <input type="text" id="balance" name="balance" class="form-control" />
                                </div>

                                <div class="form-group last mb-4">
                                    <label for="type" class="label">Type</label>
                                    <select id="type" name="type" class="form-control">
                                        <option value="" selected="selected" disabled></option>
                                        <option value="Checking">Checking</option>
                                        <option value="Saving">Saving</option>
                                    </select>
                                </div>

                                <input type="hidden" name="username" class="form-control" value="${username}" />

                                <div id="overdraftLimitField" class="form-group last mb-4" style="display: none;">
                                    <label for="overdraftLimit" class="label">Overdraft Limit</label>
                                    <input type="text" id="overdraftLimit" name="overdraftLimit" class="form-control" />
                                </div>

                                <div id="monthlyInterestRateField" class="form-group last mb-4" style="display: none;">
                                    <label for="monthlyInterestRate" class="label">Monthly Interest Rate</label>
                                    <input type="text" id="monthlyInterestRate" name="monthlyInterestRate" class="form-control" />
                                </div>

                                <div id="minimumBalanceField" class="form-group last mb-4" style="display: none;">
                                    <label for="minimumBalance" class="label">Minimum Balance</label>
                                    <input type="text" id="minimumBalance" name="minimumBalance" class="form-control" />
                                </div>

                                <div id="minimumBalanceInMonthField" class="form-group last mb-4" style="display: none;">
                                    <label for="minimumBalanceInMonth" class="label">Minimum Balance in Month</label>
                                    <input type="text" id="minimumBalanceInMonth" name="minimumBalanceInMonth" class="form-control" />
                                </div>

                                <input type="submit" value="Create Account" class="btn btn-block btn-warning">
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.getElementById('type').addEventListener('change', function() {
            var selectedType = this.value;
            document.getElementById('overdraftLimitField').style.display = (selectedType === 'Checking') ? 'block' : 'none';
            document.getElementById('monthlyInterestRateField').style.display = (selectedType === 'Saving') ? 'block' : 'none';
            document.getElementById('minimumBalanceField').style.display = (selectedType === 'Saving') ? 'block' : 'none';
            document.getElementById('minimumBalanceInMonthField').style.display = (selectedType === 'Saving') ? 'block' : 'none';
        });
    </script>

    </body>
</html>
