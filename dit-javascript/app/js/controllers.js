'use strict';

/* Controllers */

var ditControllers = angular.module('ditControllers', []);

ditControllers.controller('indexController', ['$scope', 'Phone', '$http', '$window', '$rootScope',
    function ($scope, Phone, $http, $window, $rootScope) {
        $scope.phones = Phone.query();
        $scope.orderProp = 'age';

        console.log($rootScope.name);


        $scope.facebookClicked = function () {
            alert("facebook clicked");
        };

        $scope.twitterClicked = function () {
//            console.log($rootScope.name);
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

ditControllers.controller('twitterController', ['$scope', '$window', '$http','$rootScope',
    function ($scope, $window, $http, $rootScope) {
        console.log("insideeeeeeeeeee");
        var params = location.search.split('oauth_token=')[1];
        var tokens = params.split('&');
        var oauthToken = tokens[0];
        var oauthVerifier = tokens[1].split('=')[1];

        var responsePromise = $http.get("http://dev.dit.com:8080/dit-api/public/client/auth/twitter/callback?oauth_token=" + oauthToken + "&oauth_verifier=" + oauthVerifier);

        responsePromise.success(function (data, status, headers, config) {
            $rootScope.name = 'test';
            $window.location.href = "http://dev.dit.com:8000/app/#/index";
        });
        responsePromise.error(function (data, status, headers, config) {
            console.log("error")
        });

//        $window.location.href = 'http://www.google.com';

    }
]);
