function setSelectedAccount(accountNumber) {
  document.getElementById('selectedAccountNumber').value = accountNumber;
}

function selectAccount(accountNumber) {
  // Update the action attribute of each form
  var forms = document.querySelectorAll('.transaction-form');
  for (var i = 0; i < forms.length; i++) {
    forms[i].action = forms[i].action.split('?')[0] + '?selectedAccountNumber=' + accountNumber;
  }
  // Update the hidden input field with the selected account number
  var hiddenInputs = document.querySelectorAll('.selected-account-number');
  for (var j = 0; j < hiddenInputs.length; j++) {
    hiddenInputs[j].value = accountNumber;
  }

  // Get the amount input field value
  var amountInput = document.querySelector('.withdrawInput');
  // You should set this value based on your application logic, here I'm setting it to a static value for demonstration purposes
  var amount = amountInput.value;

  // Set the value of the amount input field
  amountInput.value = amount;

  // Set the amount as a request attribute
  var form = document.querySelector('.transaction-form');
  form.setAttribute('data-amount', amount);

  // Submit the form
  forms[0].submit(); // Assuming you only have one form to submit
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
          html += '<div class="card">';
          html += '<div class="card-header">';
          html += '<span class="card-title"></span>';
          html += '<p class="card-title">' + data[i].transactionType + '</p>';
          html += '</div>';
          html += '<div class="card-body">';
          html += '<p class="card-text">' + new Date(data[i].transactionDate) + '</p>';
          html += '<p class="card-text">' + data[i].amount + '$</p>';
          html += '</div>';
          html += '<div class="card-footer">';
          if (data[i].transactionType === 'DEPOSITS') {
            html += '<a class="btn btn-success card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/' + data[i].transactionId + '/">detail</a>';
          } else if (data[i].transactionType === 'WITHDRAWALS') {
            html += '<a class="btn btn-warning card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/' + data[i].transactionId + '/">detail</a>';
          } else if (data[i].transactionType === 'GET_BALANCE') {
            html += '<a class="btn btn-info card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/' + data[i].transactionId + '/">detail</a>';
          }
          html += '</div>';
          html += '</div>';
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
