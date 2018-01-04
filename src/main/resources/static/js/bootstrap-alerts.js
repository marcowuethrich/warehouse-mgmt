/**
 * Show the hidden alertbox with a default text
 * @param msg
 */
function showDangerAlert(msg) {
    var alertBox = $('#alertD');
    var msgField = $('#alertDMessage');

    msgField.text(msg);
    alertBox.removeClass('hidden');
}

/**
 * Hide the danger alert
 */
function hidenDangerAlert() {
    var alertBox = $('#alertD');
    alertBox.addClass('hidden');
}