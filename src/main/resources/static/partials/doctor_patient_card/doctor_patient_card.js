angular.module('myApp.doctorPatientCard', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/doctorPatientCard/:patientId', {
            templateUrl: 'partials/doctor_patient_card/doctor_patient_card.html',
            controller: 'DoctorPatientCardController'
        });
    }])

    .controller('DoctorPatientCardController', [
        '$scope', '$routeParams', '$timeout', 'Drugs', 'Pharmacies',
        function ($scope, $routeParams, $timeout, Drugs, Pharmacies) {
            $scope.pharmacies = [];
            Pharmacies.query().$promise.then(function (data) {
                $scope.pharmacies = data;
            }, function (error) {
                console.log(error);
            });

            $scope.drugs = [];
            Drugs.query().$promise.then(function (data) {
                $scope.drugs = data;
            }, function (error) {
                console.log(error);
            });

            $scope.currentRecord = {};
            $scope.selectedDrugs = [];
            $scope.addDrug = function () {
                $scope.selectedDrugs.push({name: "", quantity: 1});

                $timeout(function () {
                    var result = document.getElementsByClassName("drug-input");
                    var wrappedResult = angular.element(result[result.length - 1]);
                    wrappedResult.focus();
                });
            };

            $scope.isNotAllDrugsHasNames = function () {
                var result = false;
                $scope.selectedDrugs.forEach(function (drug) {
                    if (drug.name.length == 0) {
                        result = true;
                    }
                });
                return result;
            };

            $scope.isDrugHasRealName = function (checkingDrug) {
                var result = false;
                $scope.drugs.forEach(function (drug) {
                    if (drug.name === checkingDrug.name) {
                        result = true;
                    }
                });
                return result;
            };

            $scope.isNotAllDrugsHasRealNames = function () {
                var result = false;
                $scope.selectedDrugs.forEach(function (drug) {
                    if (!isDrugHasRealName(drug)) {
                        result = true;
                    }
                });
                return result;
            };

            $scope.disableCreateRecipeButton = function () {
                return $scope.isNotAllDrugsHasNames()
                    || $scope.isNotAllDrugsHasRealNames
                    || !($scope.selectedDrugs.length > 0)
                    || !($scope.currentRecord.complaints.length > 0)
                    || !($scope.currentRecord.diagnosis.length > 0)
                    || $scope.currentPharmacy == undefined;
            };

            //$scope.drugs = [
            //    {
            //        recipe: {
            //            name: 'Рецепт №1'
            //        },
            //        name: 'Спазмалгон',
            //        quantity: 2
            //    },
            //    {
            //        recipe: {
            //            name: 'Рецепт №1'
            //        },
            //        name: 'Глюкоброминатират гепоппатаниума (20 мл)',
            //        quantity: 1
            //    },
            //    {
            //        recipe: {
            //            name: 'Рецепт №1'
            //        },
            //        name: 'Фермагематоген детский (10 гр)',
            //        quantity: 10
            //    },
            //    {
            //        recipe: {
            //            name: 'Рецепт №1'
            //        },
            //        name: 'Касторка',
            //        quantity: 1
            //    }
            //];


            $scope.patients = {
                '1': {
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
                                pharmacy: {
                                    id: 1
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
                                pharmacy: {
                                    id: 1
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
                }
            };

            var patientId = $routeParams.patientId;
            console.log(patientId);
            $scope.patient = $scope.patients[patientId];
        }]);