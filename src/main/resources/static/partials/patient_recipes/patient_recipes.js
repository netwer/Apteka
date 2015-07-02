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

            var countOfLoadingProcesses = 0;
            $scope.isDataLoading = function() {
                return countOfLoadingProcesses > 0;
            };

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
                        pharmacy.completedRecipes = [];
                        pharmacy.recipesInProgress = [];
                        dictOfPharmacies[pharmacy.address] = pharmacy;
                    }

                    if (recipe.recipeStatusId == 3) {
                        pharmacy.completedRecipes.push(angular.copy(recipe));
                    }
                    else if (recipe.recipeStatusId == 1 || recipe.recipeStatusId == 4 || recipe.recipeStatusId == 5) {
                        pharmacy.recipesInProgress.push(angular.copy(recipe));
                    }
                });

                var listOfPharmacies = [];
                for (var pharmacyAddress in dictOfPharmacies) {
                    listOfPharmacies.push(dictOfPharmacies[pharmacyAddress]);
                }
                return listOfPharmacies;
            };

            $scope.pharmaciesWithCompletedRecipes = [];
            var pharmaciesWithCompletedRecipes = function() {
                if (!$scope.pharmacies) {
                    return [];
                }
                return $scope.pharmacies.filter(function(pharmacy) {
                    if (!pharmacy || !pharmacy.completedRecipes) {
                        return false;
                    }
                    return pharmacy.completedRecipes.length > 0;
                });
            };

            $scope.pharmaciesWithRecipesInProgress = [];
            var pharmaciesWithRecipesInProgress = function() {
                if (!$scope.pharmacies) {
                    return [];
                }
                return $scope.pharmacies.filter(function(pharmacy) {
                    if (!pharmacy || !pharmacy.recipesInProgress) {
                        return false;
                    }
                    return pharmacy.recipesInProgress.length > 0;
                });
            };

            $scope.recipes = [];
            countOfLoadingProcesses++;
            PatientRecipes.query({patientId: patientId}).$promise.then(function (data) {
                configureRecipesAvailabilityDate(data);
                $scope.pharmacies = recipesGroupedByPharmacy(data);
                $scope.pharmaciesWithCompletedRecipes = pharmaciesWithCompletedRecipes();
                $scope.pharmaciesWithRecipesInProgress = pharmaciesWithRecipesInProgress();
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
            });

            countOfLoadingProcesses++;
            PatientInfo.get({patientId: patientId}).$promise.then(function (data) {
                $scope.patient = data;
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
            });

        }]);