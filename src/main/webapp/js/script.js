function setSelectedAccount(accountNumber) {
  var inputs = document.querySelectorAll(".hidden_acc_id");

  inputs.forEach(input => {
    input.value = accountNumber;
  });
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
            html += '<a class="btn btn-success btn-block card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/' + data[i].transactionId + '/">detail</a>';
          } else if (data[i].transactionType === 'WITHDRAWALS') {
            html += '<a class="btn btn-warning btn-block card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/' + data[i].transactionId + '/">detail</a>';
          } else if (data[i].transactionType === 'GET_BALANCE') {
            html += '<a class="btn btn-info btn-block card-link transaction_detail_icon" href="/BankSystemProject/transaction_detail/' + data[i].transactionId + '/">detail</a>';
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
