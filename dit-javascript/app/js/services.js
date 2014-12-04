'use strict';

/* Services */

var ditServices = angular.module('ditServices', ['ngResource']);

ditServices.factory('Phone', ['$resource',
  function($resource){
    return $resource('phones/:phoneId.json', {}, {
      query: {method:'GET', params:{phoneId:'phones'}, isArray:true}
    });
  }]);
