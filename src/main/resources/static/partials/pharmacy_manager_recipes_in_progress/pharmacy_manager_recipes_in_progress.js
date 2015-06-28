angular.module('myApp.pharmacyManagerRecipesInProgress', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesInProgress', {
            templateUrl: 'partials/pharmacy_manager_recipes_in_progress/pharmacy_manager_recipes_in_progress.html',
            controller: 'PharmacyManagerRecipesInProgressController'
        });
    }])

    .controller('PharmacyManagerRecipesInProgressController', [
        '$scope', '$modal', '$log', 'UserService', 'PharmacyRecipes',
        function ($scope, $modal, $log, UserService, PharmacyRecipes) {

            var managerId = UserService.getUserInfo().userId;

            var recipesWithConfiguredDrugStatuses = function (recipes) {
                var statuses = [
                    {
                        name: 'Готово',
                        type: 'label-success',
                        priority: 3
                    },
                    {
                        name: 'В процессе',
                        type: 'label-warning',
                        priority: 2
                    },
                    {
                        name: 'Не назначено',
                        type: 'label-danger',
                        priority: 1
                    }
                ];
                return recipes.map(function (recipe) {
                    recipe.drugs.forEach(function (drug) {
                        if (drug.apothecaryId != null) {
                            drug.status = statuses[1];
                        }
                        else if (drug.needsToProduce == false) {
                            drug.status = statuses[0];
                        }
                        else {
                            drug.status = statuses[3];
                        }
                    });
                });
            };


            $scope.recipes = [];
            PharmacyRecipes.query({pharmacistId: managerId, status: 4}).$promise.then(function (data) {
                $scope.recipes = recipesWithConfiguredDrugStatuses(data);
                if ($scope.recipes.length > 0 && $scope.selectedRecipe == undefined) {
                    $scope.selectedRecipe = $scope.recipes[0];
                }
                console.log(data);
            }, function (error) {
                console.log(error);
            });

            $scope.selectRecipe = function (recipe) {
                $scope.selectedRecipe = recipe;
            };
        }]);