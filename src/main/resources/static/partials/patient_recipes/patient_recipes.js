angular.module('myApp.patientRecipes', ['ngRoute', 'ui.bootstrap.progressbar'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/patientRecipes', {
            templateUrl: 'partials/patient_recipes/patient_recipes.html',
            controller: 'PatientRecipesController'
        });
    }])

    .controller('PatientRecipesController', ['$scope', '$modal', '$log', function ($scope, $modal, $log) {
        $scope.statuses = [
            {
                id: 1,
                name: 'Готово',
                type: 'label-success'
            },
            {
                id: 2,
                name: 'В процессе',
                type: 'label-warning'
            }
        ];

        $scope.patient = {
            id: 1,
            name: 'Иван Иванов',
            email: 'ivaiva@lol.ru',
            medicalPolicyNumber: 'ABS123-124',
            diagnosesRecords: [
                {
                    id: 1,
                    date: '23 марта 2013',
                    complaints: 'Болит голова и живот',
                    diagnosis: 'Мигрень, Спасмы в желужке',
                    recipe: {
                        id: 13,
                        status: $scope.statuses[0],
                        pharmacy: {
                            id: 1,
                            name: 'Аптека 1',
                            address: 'пер. Слепушкина, 7'
                        },
                        drugs: [
                            {
                                name: 'Спазмалгон',
                                quantity: 2
                            },
                            {
                                name: 'Глюкоброминатират гепоппатаниума (20 мл)',
                                quantity: 1
                            },
                            {
                                name: 'Фермагематоген детский (10 гр)',
                                quantity: 10
                            },
                            {
                                name: 'Касторка',
                                quantity: 1
                            }
                        ]
                    }
                },
                {
                    id: 1,
                    date: '23 марта 2013',
                    complaints: 'Болит голова и живот',
                    diagnosis: 'Мигрень, Спасмы в желужке',
                    recipe: {
                        id: 13,
                        status: $scope.statuses[1],
                        completionDate: '20 марта 2013',
                        pharmacy: {
                            id: 1,
                            name: 'Аптека 1',
                            address: 'пер. Слепушкина, 7'
                        },
                        drugs: [
                            {
                                name: 'Спазмалгон',
                                quantity: 2
                            },
                            {
                                name: 'Глюкоброминатират гепоппатаниума (20 мл)',
                                quantity: 1
                            },
                            {
                                name: 'Фермагематоген детский (10 гр)',
                                quantity: 10
                            },
                            {
                                name: 'Касторка',
                                quantity: 1
                            }
                        ]
                    }
                }
            ]
        };

        $scope.recipesGroupedByPharmacy = function(){
            var dictOfPharmacies = [];

            $scope.patient.diagnosesRecords.forEach(function(record){
                var pharmacy = dictOfPharmacies[record.recipe.pharmacy.id];
                if (pharmacy == undefined) {
                    pharmacy = angular.copy(record.recipe.pharmacy);
                    pharmacy.recipes = [record.recipe];
                    dictOfPharmacies[pharmacy.id] = pharmacy;
                }
                else {
                    pharmacy.recipes.push(record.recipe);
                }
            });

            var listOfPharmacies = [];
            for(var pharmacyId in dictOfPharmacies) {
                listOfPharmacies.push(dictOfPharmacies[pharmacyId]);
            }
            return listOfPharmacies;
        };
        $scope.pharmacies = $scope.recipesGroupedByPharmacy();

    }]);