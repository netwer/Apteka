angular.module('myApp.pharmacyDrugsToManufacture', ['ngRoute', 'ui.bootstrap.progressbar'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyDrugsToManufacture', {
            templateUrl: 'partials/pharmacy_drugs_to_manufacture/pharmacy_drugs_to_manufacture.html',
            controller: 'PharmacyDrugsToManufactureController'
        });
    }])

    .controller('PharmacyDrugsToManufactureController', ['$scope', '$modal', '$log', function ($scope, $modal, $log) {
        $scope.statuses = [
            {
                id: 1,
                name: 'Готово',
                type: 'label-success',
                priority: 3
            },
            {
                id: 2,
                name: 'В процессе',
                type: 'label-warning',
                priority: 2
            },
            {
                id: 3,
                name: 'Не назначено',
                type: 'label-danger',
                priority: 1
            }
        ];

        $scope.person = {
            id: 1,
            name: 'Константин Контсантинопольский',
            drugs: [
                {
                    recipe: {
                        id: 1,
                        date: '03 января 2014',
                        patient: {
                            name: 'Мартин Мак-Флай'
                        }
                    },
                    id: 1,
                    name: 'Спазмалгон',
                    quantity: 2,
                    status: $scope.statuses[1],
                    ingredients: [
                        {
                            id: 1,
                            name: 'Митомизол натрия',
                            quantity: 500,
                            quantity_type: 'мг'
                        },
                        {
                            id: 2,
                            name: 'Питофенон хлорида',
                            quantity: 5,
                            quantity_type: 'мг'
                        },
                        {
                            id: 3,
                            name: 'Фенпивериний бромида',
                            quantity: 0.1,
                            quantity_type: 'мг'
                        }
                    ]
                },
                {
                    recipe: {
                        id: 2,
                        date: '01 января 2014',
                        patient: {
                            name: 'Ван Хельсинк'
                        }
                    },
                    id: 2,
                    name: 'Сироп солодкового корня (100 мл)',
                    quantity: 1,
                    status: $scope.statuses[1],
                    ingredients: [
                        {
                            id: 4,
                            name: 'Экстракт корня солодки густой',
                            quantity: 4,
                            quantity_type: 'г'
                        },
                        {
                            id: 5,
                            name: 'Сироп сахарный',
                            quantity: 86,
                            quantity_type: 'г'
                        },
                        {
                            id: 6,
                            name: 'Спирт этиловый 96% и вода',
                            quantity: 100,
                            quantity_type: 'мл'
                        }
                    ]
                }
            ]
        };

        $scope.completeManufacture = function(drug) {
            var indexOfCurrentDrug = $scope.person.drugs.indexOf(drug);
            //drug.status = $scope.statuses[0];
            if (indexOfCurrentDrug < $scope.person.drugs.length - 1) {
                $scope.selectedDrug = $scope.person.drugs[indexOfCurrentDrug + 1];
            }
            else if (indexOfCurrentDrug > 0) {
                $scope.selectedDrug = $scope.person.drugs[indexOfCurrentDrug - 1];
            }
            else {
                $scope.selectedDrug = undefined;
            }

            $scope.person.drugs.remove(indexOfCurrentDrug);
        };

        $scope.selectDrug = function(recipe) {
            $scope.selectedDrug = recipe;
        };

        //$scope.listOfDrugsToManufacture = function() {
        //    var listOfDrugs = [];
        //    $scope.person.drugs.forEach(function(currentDrug) {
        //        if (currentDrug.status.type != 'label-success') {
        //            listOfDrugs.push(currentDrug);
        //        }
        //    });
        //    return listOfDrugs;
        //};

        $scope.listOfAllNeededIngredients = function() {
            var dictOfIngredients = {};

            $scope.person.drugs.forEach(function(currentDrug) {
                currentDrug.ingredients.forEach(function (currentIngredient) {
                    var ingredient = dictOfIngredients[currentIngredient.id];
                    if (ingredient == undefined) {
                        dictOfIngredients[currentIngredient.id] = currentIngredient;
                    }
                    else {
                        ingredient.quantity += currentIngredient.quantity;
                    }
                });
            });

            var listOfIngredients = [];
            for(var ingredientId in dictOfIngredients) {
                listOfIngredients.push(dictOfIngredients[ingredientId]);
            }
            return listOfIngredients;
        };

        $scope.open = function (selectedDrug) {
            var modalInstance = $modal.open({
                animation: $scope.animationsEnabled,
                templateUrl: 'partials/modal_views/select_pharmacy_modal_view.html',
                controller: 'ModalInstanceCtrl',
                size: undefined,
                resolve: {
                    persons: function () {
                        return $scope.persons;
                    },
                    drug: function () {
                        return selectedDrug;
                    },
                    statuses: function () {
                        return $scope.statuses;
                    }
                }
            });

            modalInstance.result.then(function (selectedItem) {
            }, function () {
                console.log($scope.drugs);
                $log.info('Modal dismissed at');
            });
        };
    }])

    .controller('ModalInstanceCtrl', function ($scope, $modalInstance, persons, drug, statuses) {
        $scope.select = function (person) {
            if ($scope.selectedPerson == person) {
                $scope.selectedPerson = undefined;
            }
            else {
                $scope.selectedPerson = person;
            }
            //$scope.selectedPerson = $scope.drug.person;
        };

        $scope.persons = persons;
        $scope.drug = drug;
        $scope.selectedPerson = $scope.drug.person;
        $scope.oldPerson = angular.copy($scope.drug.person);

        $scope.ok = function () {
            $scope.drug.status = statuses[1];
            $scope.drug.person = $scope.selectedPerson;
            $modalInstance.close($scope.drug);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
    });