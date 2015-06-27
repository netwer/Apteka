'use strict';

Array.prototype.remove = function(from, to) {
    var rest = this.slice((to || from) + 1 || this.length);
    this.length = from < 0 ? this.length + from : from;
    return this.push.apply(this, rest);
};

// Declare app level module which depends on views, and components
angular.module('myApp', [
    'ngRoute',
    'ngAnimate',
    'ngPrint',
    'ui.bootstrap',
    'myApp.view1',
    'myApp.view2',
    'myApp.pharmacyManagerRecipesNeedsAction',
    'myApp.pharmacyManagerRecipesInProgress',
    'myApp.pharmacyManagerRecipesDone',
    'myApp.pharmacyDrugsToManufacture',
    'myApp.doctorAppointments',
    'myApp.doctorPatientCard',
    'myApp.patientRecipes',
    'myApp.version',
    'myApp.login',
    'myApp.services'
]).
    config(['$routeProvider', function ($routeProvider) {
        $routeProvider.otherwise({redirectTo: '/view1'});
    }]);
