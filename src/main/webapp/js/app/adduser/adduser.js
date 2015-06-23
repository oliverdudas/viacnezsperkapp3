angular.module('adduser', [])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home.adduser', {
            url: '/adduser?key',
            views: {
                'content@': {
                    controller: 'AddUserController',
                    templateUrl: 'js/app/adduser/adduser.tpl.html',
                    resolve: {
                        child: function(GApi, $stateParams) {
                            if ($stateParams.key) {
                                return GApi.execute('viacnezsperkAPI', 'sperk.fulluser', {key: $stateParams.key}).then(function (resp) {
                                    return resp;
                                }, function () {
                                    console.log("error :(");
                                });
                            } else {
                                return {};
                            }
                        }
                    }
                }
            }
        })

    }])

    .controller('AddUserController', ['$scope', '$state', 'GApi', 'child', function AddUserCtrl($scope, $state, GApi, child) {

        $scope.child = child;
        $scope.key = '';

        $scope.put = function () {
            GApi.execute('viacnezsperkAPI', 'sperk.putUser', $scope.child).then(function (resp) {
                $scope.key = resp.key;
                $state.go('home.list');
            }, function () {
                console.log("error :(");
            });
        };

        $scope.toList = function() {
            $state.go('home.list');
        };

    }
    ]);