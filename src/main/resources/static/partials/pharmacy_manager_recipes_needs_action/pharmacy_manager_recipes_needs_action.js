angular.module('myApp.pharmacyManagerRecipesNeedsAction', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesNeedsAction', {
            templateUrl: 'partials/pharmacy_manager_recipes_needs_action/pharmacy_manager_recipes_needs_action.html',
            controller: 'PharmacyManagerRecipesNeedsActionController'
        });
    }])

    .controller('PharmacyManagerRecipesNeedsActionController', [
        '$scope', '$modal', '$log', 'PharmacyRecipes', 'PharmacyStaff', 'UserService', 'PharmacyConcreteDrug',
        function ($scope, $modal, $log, PharmacyRecipes, PharmacyStaff, UserService, PharmacyConcreteDrug) {

            var managerId = UserService.getUserInfo().userId;

            var configureDrugStates = function (recipes, allDone) {
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

                        if (allDone) {
                            drug.status = statuses[0];
                        }
                    });
                });
            };


            $scope.recipes = [];
            PharmacyRecipes.query({pharmacistId: managerId, status: 1}).$promise.then(function (data) {
                configureDrugStates(data, false);
                data.forEach(function(recipe) {
                    $scope.recipes.push(recipe);
                });
                if ($scope.recipes.length > 0 && $scope.selectedRecipe == undefined) {
                    $scope.selectedRecipe = $scope.recipes[0];
                }
            }, function (error) {
                console.log(error);
            });
            PharmacyRecipes.query({pharmacistId: managerId, status: 5}).$promise.then(function (data) {
                configureDrugStates(data, true);
                data.forEach(function(recipe) {
                    $scope.recipes.push(recipe);
                });
                if ($scope.recipes.length > 0 && $scope.selectedRecipe == undefined) {
                    $scope.selectedRecipe = $scope.recipes[0];
                }
            }, function (error) {
                console.log(error);
            });

            $scope.staff = [];
            PharmacyStaff.query({pharmacistId: managerId}).$promise.then(function (data) {
                $scope.staff = data;
                console.log(data);
            }, function (error) {
                console.log(error);
            });

            $scope.selectRecipe = function (recipe) {
                $scope.selectedRecipe = recipe;
            };

            $scope.isAllDrugsDone = function (recipe) {
                if (!recipe) {
                    return false;
                }

                var isAllDrugsHasDoneStatus = true;
                recipe.drugs.forEach(function (drug) {
                    if (drug.status.type != 'label-success') {
                        isAllDrugsHasDoneStatus = false;
                    }
                });
                return isAllDrugsHasDoneStatus;
            };

            $scope.isAllDrugsInProgress = function (recipe) {
                if (!recipe) {
                    return false;
                }

                var isAllDrugsHasInProgressStatus = true;
                recipe.drugs.forEach(function (drug) {
                    if (drug.status.type == 'label-danger') {
                        isAllDrugsHasInProgressStatus = false;
                    }
                });
                return isAllDrugsHasInProgressStatus;
            };

            $scope.open = function (selectedDrug) {
                console.log(selectedDrug);
                var modalInstance = $modal.open({
                    animation: $scope.animationsEnabled,
                    templateUrl: 'partials/modal_views/select_pharmacy_modal_view.html',
                    controller: 'ModalInstanceController',
                    size: undefined,
                    resolve: {
                        persons: function () {
                            return $scope.staff;
                        },
                        drug: function () {
                            return selectedDrug;
                        }
                    }
                });

                modalInstance.result.then(function (selectedItem) {
                }, function () {
                    console.log($scope.drugs);
                    $log.info('Modal dismissed at');
                });
            };

            $scope.assignDrugs = function () {
                var resultAssignments = $scope.selectedRecipe.drugs.map(function (drug) {
                    return {
                        recipeId: $scope.selectedRecipe.recipeId,
                        drugId: drug.drugId,
                        apothecaryId: drug.apothecaryId
                    }
                });

                PharmacyConcreteDrug.create({}, resultAssignments).$promise.then(function (data) {
                    console.log(data);
                }, function (error) {
                    console.log(error);
                });

            };

            $scope.completeRecipe = function () {
                PharmacyRecipes.update({
                    pharmacistId: managerId,
                    recipeId: $scope.selectedRecipe.recipeId,
                    status: 3
                }).$promise.then(function (data) {
                        console.log(data);
                    }, function (error) {
                        console.log(error);
                    });
            };
        }])

    .controller('ModalInstanceController', function ($scope, $modalInstance, persons, drug) {
        $scope.select = function (person) {
            if ($scope.selectedApothecaryId == person.apothecaryId) {
                $scope.selectedApothecaryId = undefined;
            }
            else {
                $scope.selectedApothecaryId = person.apothecaryId;
            }
            //$scope.selectedPerson = $scope.drug.person;
        };

        $scope.persons = persons;
        $scope.drug = drug;
        $scope.selectedApothecaryId = $scope.drug.apothecaryId;

        $scope.ok = function () {
            $scope.drug.status = {
                name: 'В процессе',
                type: 'label-warning',
                priority: 2
            };
            $scope.drug.apothecaryId = $scope.selectedApothecaryId;
            $modalInstance.close($scope.drug);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
    });