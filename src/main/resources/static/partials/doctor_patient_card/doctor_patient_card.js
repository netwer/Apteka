angular.module('myApp.doctorPatientCard', ['ngRoute', 'myApp.services', 'ui-notification'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/doctorPatientCard/:patientId', {
            templateUrl: 'partials/doctor_patient_card/doctor_patient_card.html',
            controller: 'DoctorPatientCardController'
        });
    }])

    .controller('DoctorPatientCardController', [
        '$scope', '$routeParams', '$location', '$timeout', 'Drugs',
        'Pharmacies', 'Appointments', 'DateService', 'UserService', 'Notification',
        function ($scope, $routeParams, $location, $timeout, Drugs, Pharmacies, Appointments, DateService, UserService, Notification) {
            $scope.dateService = DateService;

            var countOfLoadingProcesses = 0;
            $scope.isDataLoading = function () {
                return countOfLoadingProcesses > 0;
            };

            var patientId = $routeParams.patientId;
            var doctorId = UserService.getUserInfo().userId;

            countOfLoadingProcesses++;
            Appointments.query({doctorId: doctorId}).$promise.then(function (data) {
                data.forEach(function (appointment) {
                    if (appointment.patientId == patientId) {
                        $scope.currentUserAppointment = appointment;
                    }
                });
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
            });

            $scope.diagnosisRecords = [];
            countOfLoadingProcesses++;
            Appointments.query({patientId: patientId, doctorId: doctorId}).$promise.then(function (data) {
                $scope.diagnosisRecords = data;
                countOfLoadingProcesses--;
            }, function (error) {
                countOfLoadingProcesses--;
                console.log(error);
            });

            $scope.pharmacies = [];
            countOfLoadingProcesses++;
            Pharmacies.query().$promise.then(function (data) {
                $scope.pharmacies = data;
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
            });

            $scope.drugs = [];
            countOfLoadingProcesses++;
            Drugs.query().$promise.then(function (data) {
                $scope.drugs = data;
                countOfLoadingProcesses--;
            }, function (error) {
                console.log(error);
                countOfLoadingProcesses--;
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

            $scope.creatingRecipeInProgress = false;
            $scope.createRecipe = function () {
                $scope.creatingRecipeInProgress = true;
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
                        Notification.success({message: 'Рецепт отправлен в аптеку'});
                        $location.path('/doctorAppointments');
                    }, function (error) {
                        console.log(error);
                        Notification.error({message: error.statusText, title: 'Ошибка ' + error.status});
                        $scope.creatingRecipeInProgress = false;
                    });
            }
        }]);