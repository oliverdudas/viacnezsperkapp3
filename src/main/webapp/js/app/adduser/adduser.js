angular.module('adduser', [])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home.adduser', {
            url: '/adduser',
            views: {
                'content@': {
                    controller: 'AddUserController',
                    templateUrl: 'js/app/adduser/adduser.tpl.html',
                    resolve: {
                        fafa: function() {
                            var jj = 5;
                        }
                    }
                }
            }
        })

    }])

    .controller('AddUserController', ['$scope', '$state', 'GApi', function AddUserCtrl($scope, $state, GApi) {

        $scope.userModel = {};
        $scope.key = '';

        $scope.put = function () {
            GApi.execute('viacnezsperkAPI', 'sperk.putUser', $scope.userModel).then(function (resp) {
                $scope.key = resp.key;
                $state.go('home.list');
            }, function () {
                console.log("error :(");
            });
        };

        $scope.home = function() {
            $state.go('home.list');
        }

    }
    ]);