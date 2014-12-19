function facebookClicked() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/dit-api/public/client/auth/facebook/url",
        dataType: 'json',
        success: function (msg) {
            window.location.href = msg.payLoad;
        },
        error: function (err) {
            console.log(err);
        }
    });
}

function twitterClicked() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/dit-api/public/client/auth/twitter/url",
        dataType: 'json',
        success: function (msg) {
            window.location.href = msg.payLoad;
        },
        error: function (err) {
            console.log(err);
        }
    });
}


function googleClicked() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/dit-api/public/client/auth/google/url",
        dataType: 'json',
        success: function (msg) {
            window.location.href = msg.payLoad;
        },
        error: function (err) {
            console.log(err);
        }
    });
}
