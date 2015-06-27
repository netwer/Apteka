angular.module('myApp.pharmacyManagerRecipesDone', ['ngRoute'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/pharmacyManagerRecipesDone', {
            templateUrl: 'partials/pharmacy_manager_recipes_done/pharmacy_manager_recipes_done.html',
            controller: 'PharmacyManagerRecipesDoneController'
        });
    }])

    .controller('PharmacyManagerRecipesDoneController', ['$scope', '$modal', '$log', function ($scope, $modal, $log) {
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
    }]);