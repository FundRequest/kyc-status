(function () {
  jQuery(document).ready(function () {
    $(".icon_info").tooltip();
    var $result = $('#result');
    var $noResult = $('#no-result');
    var $status = $('#status');
    var $referralCount = $('#referralCount');
    var $messageRow = $('#messageRow');
    var $message = $('#message');
    var $statusDescription = $('#statusDescription');
    $('#statusForm').submit(function (e) {
      e.preventDefault();
      $result.hide();
      $noResult.hide();
      var address = $('#address').val();
      $.get("kyc/" + address, function (data) {
        if (data) {
          $messageRow.hide();
          $status.text(data['status']['label']);
          $statusDescription.attr('data-original-title', data['status']['message']);
          $statusDescription.attr('title', data['status']['message']);
          $referralCount.text(data['referralCount']);
          if (data['message']) {
            $message.text(data['message']);
            $messageRow.show();
          }
          $result.show();
        } else {
          $noResult.show();
        }

      });

    });
  });
})();