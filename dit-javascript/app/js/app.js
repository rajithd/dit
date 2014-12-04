'use strict';

/* App Module */

var ditApp = angular.module('ditApp', [
    'ngRoute',
    'ditAnimations',

    'ditControllers',
    'ditFilters',
    'ditServices'
]);

ditApp.config(['$routeProvider','$locationProvider',
    function ($routeProvider, $locationProvider) {
        $routeProvider.
            when('/index', {
                templateUrl: 'views/main.html',
                controller: 'indexController'
            }).
            when('/phones/:phoneId', {
                templateUrl: 'partials/phone-detail.html',
                controller: 'PhoneDetailCtrl'
            }).
            when('/twitter/callback', {
                templateUrl: 'views/main.html',
                controller: 'twitterController'
            }).
            otherwise({
                redirectTo: '/index'
            });

//        $locationProvider.html5Mode(true);
    }]);
