angular.module('myApp.pharmacyManagerRecipesInProgress', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesInProgress', {
            templateUrl: 'partials/pharmacy_manager_recipes_in_progress/pharmacy_manager_recipes_in_progress.html',
            controller: 'PharmacyManagerRecipesInProgressController'
        });
    }])

    .controller('PharmacyManagerRecipesInProgressController', [
        '$scope', '$log', 'UserService', 'PharmacyRecipes',
        function ($scope, $log, UserService, PharmacyRecipes) {

            var managerId = UserService.getUserInfo().userId;

            var countOfLoadingProcesses = 0;
            $scope.isDataLoading = function() {
                return countOfLoadingProcesses > 0;
            };

            var configureDrugStates = function (recipes) {
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
                recipes.forEach(function (recipe) {
                    recipe.drugs.forEach(function (drug) {
                        if (drug.apothecaryId != null && drug.apothecaryId != 0) {
                            drug.status = statuses[1];
                        }
                        else if (drug.needsToProduce == false) {
                            drug.status = statuses[0];
                        }
                        else {
                            drug.status = statuses[2];
                        }
                    });
                });
            };

            $scope.recipes = [];
            countOfLoadingProcesses++;
            PharmacyRecipes.query({pharmacistId: managerId, status: 4}).$promise.then(function (data) {
                configureDrugStates(data);
                $scope.recipes = data;
                if ($scope.recipes.length > 0 && $scope.selectedRecipe == undefined) {
                    $scope.selectedRecipe = $scope.recipes[0];
                }
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
            });

            $scope.selectRecipe = function (recipe) {
                $scope.selectedRecipe = recipe;
            };
        }]);