var module = angular.module('myApp.services', ['ngResource'])
    .factory('Drugs', ['$resource', function ($resource) {
        return $resource('/api/doctor/drugs', {})
    }])
    .factory('Pharmacies', ['$resource', function ($resource) {
        return $resource('/api/doctor/pharmacies', {})
    }])
    .factory('Appointments', ['$resource', function ($resource) {
        return $resource('/api/doctor/:doctorId/appointments/:appointmentId',
            {
                doctorId: "@doctorId",
                appointmentId: "@appointmentId"
            },
            {
                'update': {method: 'PUT'}
            }
        )
    }])
    .factory('PharmacyRecipes', ['$resource', function ($resource) {
        return $resource('/api/pharmacist/:pharmacistId/recipes/:recipeId', {
                pharmacistId: '@pharmacistId',
                recipeId: '@recipeId',
                status: '@status'
            },
            {
                'update': {method: 'PUT'}
            })
    }])
    .factory('PharmacyStaff', ['$resource', function ($resource) {
        return $resource('/api/pharmacist/:pharmacistId/staff', {
            pharmacistId: '@pharmacistId'
        })
    }])
    .factory('PharmacyConcreteDrug', ['$resource', function ($resource) {
        return $resource('/api/pharmacist/concreteDrugs', {}, {
            'update': {method: 'PUT'},
            'create': {method: 'POST'}
        })
    }])
    .factory('DateService', function () {
        return {
            getDate: function (string) {
                return moment(string).format('DD.MM.YYYY');
            },
            getTime: function (string) {
                return moment(string).format('HH:mm');
            }
        }
    })
    .factory('UserService', ['$http', '$q', '$window', '$location', function ($http, $q, $window, $location) {
        var userInfo;

        function login(userName, password) {
            var deferred = $q.defer();

            $http.get("/auth/", {
                params: {
                    login: userName,
                    password: password
                }
            }).then(function (result) {
                userInfo = {
                    userId: result.data.userId,
                    userRole: result.data.userRole,
                    userFullName: result.data.userFullName
                };
                $window.sessionStorage["userInfo"] = JSON.stringify(userInfo);
                deferred.resolve(userInfo);
            }, function (error) {
                deferred.reject(error);
            });

            return deferred.promise;
        }

        function logout() {
            userInfo = null;
            $window.sessionStorage["userInfo"] = null;
        }

        function getUserInfo() {
            return userInfo;
        }

        function init() {
            if ($window.sessionStorage["userInfo"]) {
                userInfo = JSON.parse($window.sessionStorage["userInfo"]);
            }
        }

        init();

        return {
            login: login,
            logout: logout,
            getUserInfo: getUserInfo
        };
    }]);