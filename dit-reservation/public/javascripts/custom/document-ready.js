$(document).ready( function() {
    $('#datePicker').val(new Date().toDateInputValue());

    $('.selectpicker').selectpicker({
        style: 'btn-info',
        size: 4
    });

    console.log(document.URL);
    if(document.URL.toString().indexOf("#access_token") > -1){
        var params = document.URL.split("#");
        var token = params[1].split("=");

        var accessToken = token[1].split("&");
        window.location.href="http://dev.dit.com:9001/oauth/facebook/callback/" + accessToken[0];
    }


});

Date.prototype.toDateInputValue = (function() {
    var local = new Date(this);
    local.setMinutes(this.getMinutes() - this.getTimezoneOffset());
    return local.toJSON().slice(0,10);
});