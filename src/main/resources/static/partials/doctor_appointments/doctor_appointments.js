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
            $scope.dateService = DateService;

            var doctorId = UserService.getUserInfo().userId;
            console.log(doctorId);

            $scope.appointments = [];
            Appointments.query({doctorId: doctorId}).$promise.then(function (data) {
                $scope.appointments = data;
            }, function (error) {
                console.log(error);
            });
        }]);