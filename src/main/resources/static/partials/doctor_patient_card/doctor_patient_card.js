angular.module('myApp.doctorPatientCard', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/doctorPatientCard/:patientId', {
            templateUrl: 'partials/doctor_patient_card/doctor_patient_card.html',
            controller: 'DoctorPatientCardController'
        });
    }])

    .controller('DoctorPatientCardController', [
        '$scope', '$routeParams', '$location', '$timeout', 'Drugs', 'Pharmacies', 'Appointments', 'DateService', 'UserService',
        function ($scope, $routeParams, $location, $timeout, Drugs, Pharmacies, Appointments, DateService, UserService) {
            $scope.dateService = DateService;

            var patientId = $routeParams.patientId;
            var doctorId = UserService.getUserInfo().userId;

            Appointments.query({doctorId: doctorId}).$promise.then(function (data) {
                data.forEach(function (appointment) {
                    if (appointment.patientId == patientId) {
                        $scope.currentUserAppointment = appointment;
                    }
                });
            }, function (error) {
                console.log(error);
            });

            $scope.diagnosisRecords = [];
            Appointments.query({patientId: patientId, doctorId: doctorId}).$promise.then(function (data) {
                $scope.diagnosisRecords = data;
            }, function (error) {
                console.log(error);
            });

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

            $scope.currentRecord = {
                drugsInRecipe: []
            };

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
                    if (!$scope.isDrugHasRealName(drug)) {
                        result = true;
                    }
                });
                return result;
            };

            $scope.disableCreateRecipeButton = function () {
                return $scope.isNotAllDrugsHasNames()
                    || $scope.isNotAllDrugsHasRealNames()
                    || !($scope.selectedDrugs.length > 0)
                    || !($scope.currentRecord.complaints.length > 0)
                    || !($scope.currentRecord.diagnosis.length > 0)
                    || $scope.currentRecord.pharmacyId == undefined;
            };

            $scope.createRecipe = function () {
                $scope.currentRecord.drugs = $scope.selectedDrugs.map(function (selectedDrug) {
                    var resultDrug = {};
                    for (var i = 0; i < $scope.drugs.length; i++) {
                        var drug = $scope.drugs[i];
                        if (drug.name === selectedDrug.name) {
                            resultDrug.drugId = drug.id;
                            resultDrug.drugCount = selectedDrug.quantity;
                            break;
                        }
                    }
                    return resultDrug;
                });

                Appointments.update({
                    appointmentId: $scope.currentUserAppointment.diagnosisId,
                    doctorId: doctorId
                }, $scope.currentRecord).$promise.then(function (data) {
                    console.log(data);
                    $location.path('/doctorAppointments');
                }, function (error) {
                    console.log(error);
                });
            }
        }]);