(function () {
  $(document).ajaxSend(function (event, request, settings) {
    $('#loader').show();
  });

  $(document).ajaxComplete(function (event, request, settings) {
    $('#loader').hide();
  });
  jQuery(document).ready(function () {
    $(".icon_info").tooltip();
    var $result = $('#result');
    var $noResult = $('#no-result');
    var $status = $('#status');
    var $referralCount = $('#referralCount');
    var $messageRow = $('#messageRow');
    var $message = $('#message');
    var $statusDescription = $('#statusDescription');
    var $pendingStep = $('#pendingStep');
    var $approvedStep = $('#approvedStep');
    var $whitelistLink = $('#whitelist-link');

    $('#statusForm').submit(function (e) {
      e.preventDefault();
      $result.hide();
      $noResult.hide();
      var address = $('#address').val();
      $.get("kyc/status/" + address, function (data) {
        if (data) {
          $messageRow.hide();
          $status.html(data['status']['label']);
          $statusDescription.attr('data-original-title', data['status']['message']);
          $statusDescription.attr('title', data['status']['message']);
          $referralCount.text(data['referralCount']);
          if (data['message']) {
            $message.text(data['message']);
            $messageRow.show();
          }


          $pendingStep.removeClass('step-done');
          $approvedStep.removeClass('step-done');
          if(data['status']['label'] === 'Pending' || data['status']['label'] === 'Approved') {
            $pendingStep.addClass('step-done');
          }
          if(data['status']['label'] === 'Approved') {
            $approvedStep.addClass('step-done');
          }

          $result.show();
          $whitelistLink.hide();
        } else {
          $noResult.show();
        }

      });

    });
  });
})();