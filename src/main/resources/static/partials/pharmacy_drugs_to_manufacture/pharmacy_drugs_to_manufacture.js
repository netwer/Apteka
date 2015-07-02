angular.module('myApp.pharmacyDrugsToManufacture', ['ngRoute', 'myApp.services', 'ui-notification'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyDrugsToManufacture', {
            templateUrl: 'partials/pharmacy_drugs_to_manufacture/pharmacy_drugs_to_manufacture.html',
            controller: 'PharmacyDrugsToManufactureController'
        });
    }])

    .controller('PharmacyDrugsToManufactureController', [
        '$scope', '$log', 'UserService', 'ApothecaryDrug', 'Notification',
        function ($scope, $log, UserService, ApothecaryDrug, Notification) {

            var countOfLoadingProcesses = 0;
            $scope.isDataLoading = function() {
                return countOfLoadingProcesses > 0;
            };

            var apothecaryId = UserService.getUserInfo().userId;

            var listOfAllNeededIngredients = function (drugs) {
                var dictOfIngredients = {};

                drugs.forEach(function (currentDrug) {
                    currentDrug.ingredients.forEach(function (currentIngredient) {
                        var ingredient = dictOfIngredients[currentIngredient.ingredientId];
                        if (ingredient == undefined) {
                            dictOfIngredients[currentIngredient.ingredientId] = angular.copy(currentIngredient);
                        }
                        else {
                            ingredient.count += currentIngredient.count;
                        }
                    });
                });

                var listOfIngredients = [];
                for (var ingredientId in dictOfIngredients) {
                    listOfIngredients.push(dictOfIngredients[ingredientId]);
                }
                return listOfIngredients;
            };

            var listOfIngredientsForDrug = function(drug) {
                if (!drug) {
                    return [];
                }

                var dictOfIngredients = {};
                drug.ingredients.forEach(function (currentIngredient) {
                    var ingredient = dictOfIngredients[currentIngredient.ingredientId];
                    if (ingredient == undefined) {
                        dictOfIngredients[currentIngredient.ingredientId] = angular.copy(currentIngredient);
                    }
                    else {
                        ingredient.count += currentIngredient.count;
                    }
                });

                var listOfIngredients = [];
                for (var ingredientId in dictOfIngredients) {
                    listOfIngredients.push(dictOfIngredients[ingredientId]);
                }
                return listOfIngredients;
            };

            $scope.drugs = [];
            $scope.allIngredients = [];
            countOfLoadingProcesses++;
            ApothecaryDrug.query({apothecaryId: apothecaryId}).$promise.then(function (data) {
                data.forEach(function(drug){
                    drug.groupedIngredients = listOfIngredientsForDrug(drug);
                });
                $scope.allIngredients = listOfAllNeededIngredients(data);
                $scope.drugs = data;
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
            });

            var removeCurrentDrugAndSelectNext = function() {
                var indexOfCurrentDrug = $scope.drugs.indexOf($scope.selectedDrug);
                //drug.status = $scope.statuses[0];
                if (indexOfCurrentDrug < $scope.drugs.length - 1) {
                    $scope.selectedDrug = $scope.drugs[indexOfCurrentDrug + 1];
                }
                else if (indexOfCurrentDrug > 0) {
                    $scope.selectedDrug = $scope.drugs[indexOfCurrentDrug - 1];
                }
                else {
                    $scope.selectedDrug = undefined;
                }
                $scope.drugs.remove(indexOfCurrentDrug);
            };

            $scope.completingManufactureInProcess = false;
            $scope.completeManufacture = function (drug) {
                $scope.completingManufactureInProcess = true;

                ApothecaryDrug.delete({apothecaryId: apothecaryId, drugId:drug.drug.recipesHasDrugsId}).$promise.then(function (data) {
                    removeCurrentDrugAndSelectNext();
                    Notification.success({message: 'Статус готовности изменен'});
                    $scope.completingManufactureInProcess = false;
                }, function (error) {
                    console.log(error);
                    Notification.error({message: error.statusText, title: 'Ошибка ' + error.status});
                    $scope.completingManufactureInProcess = false;
                });
            };

            $scope.selectDrug = function (recipe) {
                $scope.selectedDrug = recipe;
            };
        }]);