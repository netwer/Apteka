angular.module('myApp.login', ['ngRoute', 'myApp.services', 'myApp.navigationController'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'partials/login/login.html',
            controller: 'LoginViewController'
        });
    }])

    .controller('LoginViewController', [
        '$scope', '$location', 'UserService', 'RoleBaseNavigationBar',
        function ($scope, $location, UserService, RoleBaseNavigationBar) {
        $scope.loginInProcess = false;
        $scope.login = function () {
            $scope.loginInProcess = true;
            UserService.login($scope.username, $scope.password).then(function(userInfo) {
                $scope.loginInProcess = false;
                var navigationBarLeftItems = RoleBaseNavigationBar.getNavigationBar().leftItems;
                if (navigationBarLeftItems.length > 0) {
                    $location.path(navigationBarLeftItems[0].path);
                }
            }, function(reason) {
                $scope.loginInProcess = false;
                alert('Failed: ' + reason);
            });
        };
    }]);