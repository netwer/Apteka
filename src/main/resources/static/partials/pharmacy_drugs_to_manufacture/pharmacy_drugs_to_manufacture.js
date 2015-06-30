angular.module('myApp.pharmacyDrugsToManufacture', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyDrugsToManufacture', {
            templateUrl: 'partials/pharmacy_drugs_to_manufacture/pharmacy_drugs_to_manufacture.html',
            controller: 'PharmacyDrugsToManufactureController'
        });
    }])

    .controller('PharmacyDrugsToManufactureController', [
        '$scope', '$log', 'UserService', 'ApothecaryDrug',
        function ($scope, $log, UserService, ApothecaryDrug) {

            var apothecaryId = UserService.getUserInfo().userId;

            $scope.drugs = [];
            ApothecaryDrug.query({apothecaryId: apothecaryId}).$promise.then(function (data) {
                $scope.drugs = data;
            }, function (error) {
                console.log(error);
            });

            $scope.completeManufacture = function (drug) {
                var indexOfCurrentDrug = $scope.drugs.indexOf(drug);
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

                ApothecaryDrug.delete({apothecaryId: apothecaryId, drugId:drug.drug.recipesHasDrugsId}).$promise.then(function (data) {
                    $scope.drugs.remove(indexOfCurrentDrug);
                }, function (error) {
                    console.log(error);
                });
            };

            $scope.selectDrug = function (recipe) {
                $scope.selectedDrug = recipe;
            };

            $scope.listOfAllNeededIngredients = function () {
                //var dictOfIngredients = {};
                //
                //$scope.drugs.forEach(function (currentDrug) {
                //    currentDrug.ingredients.forEach(function (currentIngredient) {
                //        var ingredient = dictOfIngredients[currentIngredient.ingredientId];
                //        if (ingredient == undefined) {
                //            dictOfIngredients[currentIngredient.ingredientId] = angular.copy(currentIngredient);
                //        }
                //        else {
                //            ingredient.count += currentIngredient.count;
                //        }
                //    });
                //});

                var listOfIngredients = [];
                //for (var ingredientId in dictOfIngredients) {
                //    listOfIngredients.push(dictOfIngredients[ingredientId]);
                //}
                return listOfIngredients;
            };
        }]);