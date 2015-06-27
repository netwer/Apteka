angular.module('myApp.pharmacyManagerRecipesNeedsAction', ['ngRoute', 'ui.bootstrap.progressbar'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesNeedsAction', {
            templateUrl: 'partials/pharmacy_manager_recipes_needs_action/pharmacy_manager_recipes_needs_action.html',
            controller: 'PharmacyManagerRecipesNeedsActionController'
        });
    }])

    .controller('PharmacyManagerRecipesNeedsActionController', ['$scope', '$modal', '$log', function ($scope, $modal, $log) {
        $scope.persons = [
            {
                id: 1,
                name: 'Константин Контсантинопольский',
                count: 4
            },
            {
                id: 2,
                name: 'Валентин Ковальский',
                count: 1
            },
            {
                id: 3,
                name: 'Георгий Валерьянович',
                count: 2
            },
            {
                id: 4,
                name: 'Иван Иванов',
                count: 2
            }
        ];

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

        $scope.drugs = [
            {
                recipe: {
                    name: 'Рецепт №1'
                },
                name: 'Спазмалгон',
                quantity: 2,
                status: $scope.statuses[0]
            },
            {
                recipe: {
                    name: 'Рецепт №1'
                },
                name: 'Глюкоброминатират гепоппатаниума (20 мл)',
                quantity: 1,
                status: $scope.statuses[0]
            },
            {
                recipe: {
                    name: 'Рецепт №1'
                },
                name: 'Фермагематоген детский (10 гр)',
                quantity: 10,
                status: $scope.statuses[1],
                person: $scope.persons[2]
            },
            {
                recipe: {
                    name: 'Рецепт №1'
                },
                name: 'Касторка',
                quantity: 1,
                status: $scope.statuses[2]
            }
        ];

        $scope.recipes = [
            {
                id: 1,
                date: '03 января 2014',
                patient: {
                    name: 'Мартин Мак-Флай'
                },
                drugs: [
                    {
                        recipe: {
                            name: 'Рецепт №1'
                        },
                        name: 'Спазмалгон',
                        quantity: 2,
                        status: $scope.statuses[0]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №1'
                        },
                        name: 'Глюкоброминатират гепоппатаниума (20 мл)',
                        quantity: 1,
                        status: $scope.statuses[0]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №1'
                        },
                        name: 'Фермагематоген детский (10 гр)',
                        quantity: 10,
                        status: $scope.statuses[1],
                        person: $scope.persons[2]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №1'
                        },
                        name: 'Касторка',
                        quantity: 1,
                        status: $scope.statuses[2]
                    }
                ]
            },
            {
                id: 2,
                date: '01 января 2014',
                patient: {
                    name: 'Ван Хельсинк'
                },
                drugs: [
                    {
                        recipe: {
                            name: 'Рецепт №2'
                        },
                        name: 'Сироп солодкового корня (100 мл)',
                        quantity: 1,
                        status: $scope.statuses[2]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №2'
                        },
                        name: 'Антигриппин уно трето в таблетках-призраках',
                        quantity: 10,
                        status: $scope.statuses[2]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №2'
                        },
                        name: 'Анаболические стеройды',
                        quantity: 1,
                        status: $scope.statuses[2]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №2'
                        },
                        name: 'Пипетка',
                        quantity: 1,
                        status: $scope.statuses[0]
                    }
                ]
            },
            {
                id: 3,
                date: '10 января 2015',
                patient: {
                    name: 'Робин'
                },
                drugs: [
                    {
                        recipe: {
                            name: 'Рецепт №3'
                        },
                        name: 'Сироп солодкового корня (100 мл)',
                        quantity: 1,
                        status: $scope.statuses[0]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №3'
                        },
                        name: 'Антигриппин уно трето в таблетках-призраках',
                        quantity: 10,
                        status: $scope.statuses[0]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №3'
                        },
                        name: 'Анаболические стеройды',
                        quantity: 1,
                        status: $scope.statuses[0]
                    },
                    {
                        recipe: {
                            name: 'Рецепт №3'
                        },
                        name: 'Пипетка',
                        quantity: 1,
                        status: $scope.statuses[0]
                    }
                ]
            }
        ];
        if ($scope.recipes.length > 0) {
            $scope.selectedDrug = $scope.recipes[0];
        }

        $scope.selectRecipe = function(recipe) {
            $scope.selectedDrug = recipe;
        };

        $scope.isAllDrugsDone = function(recipe) {
            isAllDrugsHasDoneStatus = true;
            recipe.drugs.forEach(function(drug) {
                if (drug.status.id != $scope.statuses[0].id) {
                    isAllDrugsHasDoneStatus = false;
                }
            });
            return isAllDrugsHasDoneStatus;
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