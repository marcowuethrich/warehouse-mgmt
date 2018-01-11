/**
 * Show the hidden danger alertbox with a default text
 * @param msg
 */
function showDangerAlert(msg) {
    var alertBox = $('#alertD');
    var msgField = $('#alertDMessage');

    msgField.text(msg);
    alertBox.removeClass('hidden');
}

/**
 * Show the hidden success alertbox with a default text
 * @param msg
 */
function showSuccessAlert(msg) {
    var alertBox = $('#alertS');
    var msgField = $('#alertSMessage');

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