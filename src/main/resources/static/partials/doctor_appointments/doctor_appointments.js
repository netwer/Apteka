angular.module('myApp.doctorAppointments', ['ngRoute', 'myApp.services'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/doctorAppointments', {
            templateUrl: 'partials/doctor_appointments/doctor_appointments.html',
            controller: 'DoctorAppointmentsController'
        });
    }])

    .controller('DoctorAppointmentsController', [
        '$scope', '$modal', '$log', 'Appointments', 'DateService', 'UserService',
        function ($scope, $modal, $log, Appointments, DateService, UserService) {
            var countOfLoadingProcesses = 0;
            $scope.isDataLoading = function() {
                return countOfLoadingProcesses > 0;
            };

            $scope.dateService = DateService;

            var doctorId = UserService.getUserInfo().userId;

            $scope.appointments = [];

            countOfLoadingProcesses++;
            Appointments.query({doctorId: doctorId}).$promise.then(function (data) {
                $scope.appointments = data;
                countOfLoadingProcesses--;
            }, function (error) {
                countOfLoadingProcesses--;
                console.log(error);
            });
        }]);