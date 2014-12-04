'use strict';

/* Controllers */

var ditControllers = angular.module('ditControllers', []);

ditControllers.controller('indexController', ['$scope', 'Phone', '$http', '$window',
    function ($scope, Phone, $http, $window) {
        $scope.phones = Phone.query();
        $scope.orderProp = 'age';

        $scope.facebookClicked = function () {
            alert("facebook clicked");
        };

        $scope.twitterClicked = function () {
            var responsePromise = $http.get("http://dev.dit.com:8080/dit-api/public/client/auth/twitter/url");

            responsePromise.success(function (data, status, headers, config) {
                $window.location.href = data.payLoad;
            });
            responsePromise.error(function (data, status, headers, config) {
                console.log("error")
            });
        };
    }]);

ditControllers.controller('PhoneDetailCtrl', ['$scope', '$routeParams', 'Phone',
    function ($scope, $routeParams, Phone) {
        $scope.phone = Phone.get({phoneId: $routeParams.phoneId}, function (phone) {
            $scope.mainImageUrl = phone.images[0];
        });

        $scope.setImage = function (imageUrl) {
            console.log("aaa");
            alert("aaaaa");
            $scope.mainImageUrl = imageUrl;
        };
    }]);

ditControllers.controller('twitterController', ['$scope', '$window', '$http',
    function ($scope, $window, $http) {
        var params = location.search.split('oauth_token=')[1];
        var tokens = params.split('&');
        var oauthToken = tokens[0];
        var oauthVerifier = tokens[1].split('=')[1];

        var responsePromise = $http.get("http://dev.dit.com:8080/dit-api/public/client/auth/twitter/callback?oauth_token=" + oauthToken + "&oauth_verifier=" + oauthVerifier);

        responsePromise.success(function (data, status, headers, config) {
            $window.location.href = "http://www.google.com";
        });
        responsePromise.error(function (data, status, headers, config) {
            console.log("error")
        });

//        $window.location.href = 'http://www.google.com';

    }
]);
