angular.module('myApp.doctorAppointments', ['ngRoute', 'ui.bootstrap.progressbar'])

    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/doctorAppointments', {
            templateUrl: 'partials/doctor_appointments/doctor_appointments.html',
            controller: 'DoctorAppointmentsController'
        });
    }])

    .controller('DoctorAppointmentsController', ['$scope', '$modal', '$log', function ($scope, $modal, $log) {
        $scope.appointments = [
            {
                time: '09:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            },
            {
                time: '10:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            },
            {
                time: '11:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            },
            {
                time: '12:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            },
            {
                time: '13:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            },
            {
                time: '14:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            },
            {
                time: '15:15',
                patient: {
                    id: 1,
                    name: 'Иван Иванов',
                    medicalPolicyNumber: 'ABS123-124'
                }
            }
        ];
    }]);