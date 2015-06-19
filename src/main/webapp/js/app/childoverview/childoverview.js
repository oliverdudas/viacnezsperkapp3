angular.module('childoverview', [])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home.childoverview', {
            url: '/childoverview',
            views: {
                'content@': {
                    controller: 'ChildoverviewController',
                    templateUrl: 'js/app/childoverview/childoverview.tpl.html'
                }
            }
        })

    }])

    .controller('ChildoverviewController', ['$scope', '$state', 'modelHolder', function ChildoverviewCtrl($scope, $state, modelHolder) {

        $scope.child = modelHolder.getChild();

    }]);