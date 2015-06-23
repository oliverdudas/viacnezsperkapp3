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

        $scope.user = modelHolder.getChild();
        $scope.validator = {
            validMainURL: function() {
                return angular.isDefined($scope.user.mainURL);
            },
            validFirstname: function() {
                return angular.isDefined($scope.user.firstname);
            },
            validBornYear: function() {
                return angular.isDefined($scope.user.bornYear);
            },
            validResidence: function() {
                return angular.isDefined($scope.user.residence);
            },
            validGalleryItems: function() {
                return angular.isDefined($scope.user.galleryItems);
            }

        };

    }]);