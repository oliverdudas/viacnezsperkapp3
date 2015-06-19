angular.module('login', [])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home.login', {
            url: '/login',
            views: {
                'content@': {
                    controller: 'LoginController',
                    templateUrl: 'js/app/login/login.tpl.html'
                }
            }
        })

    }])


    .controller('LoginController', ['$scope', '$state', 'GAuth', 'GApi', 'GData', 'modelHolder', function LoginCtrl($scope, $state, GAuth, GApi, GData, modelHolder) {

        $scope.doLogin = function () {
            GAuth.login().then(function (resp) {
                $scope.email = GData.getUser().email;
                $state.go('home.list');
            });
        };

        $scope.login = function() {
            GApi.execute('viacnezsperkAPI', 'sperk.login', {username: $scope.username, password: $scope.password}).then(function (resp) {
                modelHolder.setChild(resp.result);
                $state.go('home.childoverview');
            }, function () {
                console.log("error :(");
            });
        };
    }
    ]);