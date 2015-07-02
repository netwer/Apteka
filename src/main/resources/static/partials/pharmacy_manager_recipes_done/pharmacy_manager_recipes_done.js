angular.module('myApp.pharmacyManagerRecipesDone', ['ngRoute', 'myApp.services', 'ui-notification'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesDone', {
            templateUrl: 'partials/pharmacy_manager_recipes_done/pharmacy_manager_recipes_done.html',
            controller: 'PharmacyManagerRecipesDoneController'
        });
    }])

    .controller('PharmacyManagerRecipesDoneController', [
        '$scope', '$modal', '$log', 'UserService', 'PharmacyRecipes', 'Notification',
        function ($scope, $modal, $log, UserService, PharmacyRecipes, Notification) {
            var managerId = UserService.getUserInfo().userId;

            var countOfLoadingProcesses = 0;
            $scope.isDataLoading = function() {
                return countOfLoadingProcesses > 0;
            };

            $scope.recipes = [];
            countOfLoadingProcesses++;
            PharmacyRecipes.query({pharmacistId: managerId, status: 3}).$promise.then(function (data) {
                $scope.recipes = data;
                console.log(data);
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                Notification.error({message: error.statusText, title: 'Error ' + error.status});
                countOfLoadingProcesses--;
            });

            $scope.sendingRecipeId = 0;
            $scope.sendRecipe = function (recipe) {
                $scope.sendingRecipeId = recipe.recipeId;
                PharmacyRecipes.update({
                    pharmacistId: managerId,
                    recipeId: recipe.recipeId
                }, {
                    status: 7
                }).$promise.then(function (data) {
                        $scope.recipes.remove($scope.recipes.indexOf(recipe));
                        Notification.success({message: 'Рецепт успешно выдан'});
                        $scope.sendingRecipeId = 0;
                    }, function (error) {
                        Notification.error({message: error.statusText, title: 'Ошибка ' + error.status});
                        console.log(error);
                        $scope.sendingRecipeId = 0;
                    });
            };
        }]);