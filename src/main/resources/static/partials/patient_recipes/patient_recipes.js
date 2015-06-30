angular.module('myApp.patientRecipes', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/patientRecipes', {
            templateUrl: 'partials/patient_recipes/patient_recipes.html',
            controller: 'PatientRecipesController'
        });
    }])

    .controller('PatientRecipesController', [
        '$scope', '$log', 'UserService', 'PatientRecipes', 'PatientInfo',
        function ($scope, $log, UserService, PatientRecipes, PatientInfo) {

            var patientId = UserService.getUserInfo().userId;

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
                        pharmacy.recipes = [angular.copy(recipe)];
                        dictOfPharmacies[pharmacy.address] = pharmacy;
                    }
                    else {
                        pharmacy.recipes.push(angular.copy(recipe));
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
                configureRecipesAvailabilityDate(data);
                $scope.pharmacies = recipesGroupedByPharmacy(data);
                console.log(data);
            }, function (error) {
                console.log(error);
            });

            PatientInfo.get({patientId: patientId}).$promise.then(function (data) {
                $scope.patient = data;
                console.log(data);
            }, function (error) {
                console.log(error);
            });

        }]);