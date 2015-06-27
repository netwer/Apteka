var module = angular.module('myApp.services', ['ngResource'])
    .factory('Drugs', ['$resource', function($resource) {
        return $resource('/api/doctor/drugs', {})
    }])
    .factory('Pharmacies', ['$resource', function($resource) {
        return $resource('/api/doctor/pharmacies', {})
    }]);