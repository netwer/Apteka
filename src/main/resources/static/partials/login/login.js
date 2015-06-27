angular.module('myApp.login', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'partials/login/login.html',
            controller: 'LoginViewController'
        });
    }])

    .controller('LoginViewController', ['$scope', function ($scope) {

    }]);