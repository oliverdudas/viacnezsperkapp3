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
                    controller: 'ListController',
                    templateUrl: 'js/app/home/list.tpl.html',
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

    .controller('ListController', ['$scope', '$state', 'GApi', 'users', function ListCtrl($scope, $state, GApi, users) {

        $scope.users = users;

        $scope.addUser = function () {
            $state.go('home.adduser');
        };

        $scope.edit = function (key) {
            $state.go('home.adduser', {key: key});
        };

        $scope.$on('$viewContentLoaded', function() {
            Cufon.replace('h1', { fontFamily:'ArnoPro', hover:true });
        });

        $scope.test = function() {
            GApi.execute('viacnezsperkAPI', 'sperk.getUsers').then(function (resp) {
                console.log('returned');
            }, function () {
                console.log("error :(");
            });
        };

        //$scope.$on('$routeChangeSuccess', function () {
        //    Cufon.replace('h1', { fontFamily:'ArnoPro', hover:true });
        //});
        //$scope.executeCufon = function() {
        //    Cufon.replace('h1', { fontFamily:'ArnoPro', hover:true });
        //};

    }
    ])

    .controller('HeaderController', ['$scope', '$state', 'GAuth', 'GApi', 'GData', function HeaderCtrl($scope, $state, GAuth, GApi, GData) {
    }
    ]);