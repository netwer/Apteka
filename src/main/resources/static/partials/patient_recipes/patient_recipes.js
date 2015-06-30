angular.module('myApp.patientRecipes', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/patientRecipes', {
            templateUrl: 'partials/patient_recipes/patient_recipes.html',
            controller: 'PatientRecipesController'
        });
    }])

    .controller('PatientRecipesController', [
        '$scope', '$log', 'UserService', 'PatientRecipes',
        function ($scope, $log, UserService, PatientRecipes) {

            var patientId = UserService.getUserInfo().userId;

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

            var configureRecipesAvailabilityDate = function (recipes) {
                recipes.forEach(function (recipe) {
                    var maxDate;
                    recipe.drugs.forEach(function (drug) {
                        var availabilityDate = drug.availabilityDate;
                        if (maxDate == undefined) {
                            maxDate = availabilityDate;
                        }

                        if (maxDate < availabilityDate) {
                            maxDate = availabilityDate;
                        }
                    });
                    if (maxDate != undefined) {
                        recipe.completionDate = maxDate;
                    }
                });
            };

            var recipesGroupedByPharmacy = function (recipes) {
                var dictOfPharmacies = [];

                recipes.forEach(function (recipe) {
                    var pharmacy = dictOfPharmacies[recipe.pharmaciesAddress];
                    if (pharmacy == undefined) {
                        pharmacy = {
                            name: recipe.pharmaciesName,
                            address: recipe.pharmaciesAddress
                        };
                        pharmacy.recipes = [recipe];
                        dictOfPharmacies[pharmacy.address] = pharmacy;
                    }
                    else {
                        pharmacy.recipes.push(recipe);
                    }
                });

                var listOfPharmacies = [];
                for (var pharmacyAddress in dictOfPharmacies) {
                    listOfPharmacies.push(dictOfPharmacies[pharmacyAddress]);
                }
                return listOfPharmacies;
            };

            $scope.recipes = [];
            PatientRecipes.query({patientId: patientId}).$promise.then(function (data) {
                configureDrugStates(data);
                configureRecipesAvailabilityDate(data);
                $scope.pharmacies = recipesGroupedByPharmacy(data);
                console.log(data);
            }, function (error) {
                console.log(error);
            });
        }]);