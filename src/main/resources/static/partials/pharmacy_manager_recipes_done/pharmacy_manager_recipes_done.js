angular.module('myApp.pharmacyManagerRecipesDone', ['ngRoute'])

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
            PharmacyRecipes.query({pharmacistId: managerId, status: 3}).$promise.then(function (data) {
                $scope.recipes = data;
                console.log(data);
            }, function (error) {
                console.log(error);
            });

            $scope.sendRecipe = function(recipe) {
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