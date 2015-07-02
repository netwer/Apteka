'use strict';

Array.prototype.remove = function (from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};

// Declare app level module which depends on views, and components
angular.module('myApp',
    [
        'ngRoute',
        'ngAnimate',
        'ngPrint',
        'ui.bootstrap',
        'ui-notification',
        'myApp.navigationController',
        'myApp.pharmacyManagerRecipesNeedsAction',
        'myApp.pharmacyManagerRecipesInProgress',
        'myApp.pharmacyManagerRecipesDone',
        'myApp.pharmacyDrugsToManufacture',
        'myApp.doctorAppointments',
        'myApp.doctorPatientCard',
        'myApp.patientRecipes',
        'myApp.version',
        'myApp.login',
        'myApp.directives',
        'myApp.services'
    ])
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/login'});
    }])
    .config(['NotificationProvider', function(NotificationProvider) {
        NotificationProvider.setOptions({
            delay: 10000,
            startTop: 20,
            startRight: 10,
            verticalSpacing: 20,
            horizontalSpacing: 20,
            positionX: 'right',
            positionY: 'top'
        });
    }])
    .run(['$rootScope', '$location', 'UserService', function ($rootScope, $location, UserService) {
        $rootScope.$on('$routeChangeStart', function (event) {
            if ($location.$$path === '/logout') {
                UserService.logout();
                $location.path('/login');
            }
            if (UserService.getUserInfo() == undefined) {
                console.log('DENY');
                //event.preventDefault();
                $location.path('/login');
            }
        });
    }]);
