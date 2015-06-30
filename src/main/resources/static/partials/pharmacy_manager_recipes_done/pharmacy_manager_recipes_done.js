angular.module('myApp.pharmacyManagerRecipesDone', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesDone', {
            templateUrl: 'partials/pharmacy_manager_recipes_done/pharmacy_manager_recipes_done.html',
            controller: 'PharmacyManagerRecipesDoneController'
        });
    }])

    .controller('PharmacyManagerRecipesDoneController', [
        '$scope', '$modal', '$log', 'UserService', 'PharmacyRecipes',
        function ($scope, $modal, $log, UserService, PharmacyRecipes) {
            var managerId = UserService.getUserInfo().userId;

            $scope.recipes = [];
            PharmacyRecipes.query({pharmacistId: managerId, status: 3}).$promise.then(function (data) {
                $scope.recipes = data;
                console.log(data);
            }, function (error) {
                console.log(error);
            });

            $scope.sendRecipe = function (recipe) {
                PharmacyRecipes.update({
                    pharmacistId: managerId,
                    recipeId: recipe.recipeId
                }, {
                    status: 7
                }).$promise.then(function (data) {
                        $scope.recipes.remove($scope.recipes.indexOf(recipe));
                        console.log(data);
                    }, function (error) {
                        console.log(error);
                    });
            }
        }]);