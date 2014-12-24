$(document).ready( function() {
    $('#datePicker').val(new Date().toDateInputValue());

    $('.selectpicker').selectpicker({
        style: 'btn-info',
        size: 4
    });


});

Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});