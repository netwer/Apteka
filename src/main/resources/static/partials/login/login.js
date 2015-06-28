angular.module('myApp.login', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'partials/login/login.html',
            controller: 'LoginViewController'
        });
    }])

    .controller('LoginViewController', ['$scope', '$location', 'UserService', function ($scope, $location, UserService) {
        $scope.loginInProcess = false;
        $scope.login = function () {
            $scope.loginInProcess = true;
            UserService.login($scope.username, $scope.password).then(function(userInfo) {
                $scope.loginInProcess = false;
                $location.path('/doctorAppointments');
            }, function(reason) {
                $scope.loginInProcess = false;
                alert('Failed: ' + reason);
            });
        };
    }]);