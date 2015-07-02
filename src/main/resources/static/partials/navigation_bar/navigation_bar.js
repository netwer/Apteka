angular.module('myApp.navigationController', ['myApp.services'])
    .factory('RoleBaseNavigationBar', function() {
        var navigationBar = {
            showLogo: false,
            leftItems:[
            ],
            rightItems: [
            ]
        };

        var roles = {
            'PATIENT': {
                configureMenuItems: function() {
                    navigationBar.leftItems = [
                        {
                            path: '/patientRecipes',
                            title: 'Мои рецепты'
                        }
                    ];
                }
            },
            'APOTHECARY': {
                configureMenuItems: function() {
                    navigationBar.leftItems = [
                        {
                            path: '/pharmacyDrugsToManufacture',
                            title: 'Лекарства к изготовлению'
                        }
                    ];
                }
            },
            'DOCTOR': {
                configureMenuItems: function() {
                    navigationBar.leftItems = [
                        {
                            path: '/doctorAppointments',
                            title: 'Записи на прием'
                        }
                    ];
                }
            },
            'PHARMACIST': {
                configureMenuItems: function() {
                    navigationBar.leftItems = [
                        {
                            path: '/pharmacyManagerRecipesNeedsAction',
                            title: 'Рецепты'
                        },
                        {
                            path: '/pharmacyManagerRecipesInProgress',
                            title: 'В процессе изготовления'
                        },
                        {
                            path: '/pharmacyManagerRecipesDone',
                            title: 'Готовы'
                        }
                    ];
                }
            }
        };

        function getRoleBaseNavigationBar(role) {
            if (role in roles) {
                navigationBar.showLogo = true;
                navigationBar.rightItems = [
                    {
                        path: '/logout',
                        title: 'Выйти'
                    }
                ];
                roles[role].configureMenuItems();
            }
            else {
                navigationBar.showLogo = false;
                navigationBar.leftItems = [];
                navigationBar.rightItems = [];
            }
            return getNavigationBar();
        }

        function getNavigationBar() {
            return navigationBar;
        }

        return {
            getRoleBaseNavigationBar: getRoleBaseNavigationBar,
            getNavigationBar: getNavigationBar
        }
    })
    .controller('NavigationBarController', [
        '$scope', '$rootScope', '$log', 'UserService', 'RoleBaseNavigationBar',
        function ($scope, $rootScope, $log, UserService, RoleBaseNavigationBar) {
            var configureNavigationBar = function() {
                var userInfo = UserService.getUserInfo();
                var userRole;
                if (userInfo) {
                    userRole = userInfo.userRole;
                }
                $scope.navigationBar = RoleBaseNavigationBar.getRoleBaseNavigationBar(userRole);
            };
            configureNavigationBar();

            $rootScope.$on('UserInfoChanged', function (event, data) {
                configureNavigationBar();
            });
        }]);