angular.module('home', [])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home', {
            abstract: true,
            views: {
                'header': {
                    templateUrl: 'js/app/template/header.tpl.html',
                    controller: 'HeaderController'
                },
                'content': {
                    templateUrl: ''
                },
                footer: {
                    templateUrl: 'js/app/template/footer.tpl.html'
                }
            }
        });

        $stateProvider.state('home.list', {
            url: '/list',
            views: {
                'content@': {
                    controller: 'HomeController',
                    templateUrl: 'js/app/app.tpl.html',
                    resolve: {
                        users: function(GApi) {
                            return GApi.execute('viacnezsperkAPI', 'sperk.getUsers').then(function (resp) {
                                return resp.items;
                            }, function () {
                                console.log("error :(");
                            });
                        }
                    }
                }
            }
        });

    }])

    .controller('HomeController', ['$scope', '$state', 'GApi', 'users', function HomeCtrl($scope, $state, GApi, users) {

        $scope.users = users;

        $scope.addUser = function () {
            $state.go('home.adduser');
        };

    }
    ])

    .controller('HeaderController', ['$scope', '$state', 'GAuth', 'GApi', 'GData', function HeaderCtrl($scope, $state, GAuth, GApi, GData) {
    }
    ]);