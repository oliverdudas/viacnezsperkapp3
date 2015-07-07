angular.module('childoverview', [])

    .config(['$stateProvider', function ($stateProvider) {
        $stateProvider.state('home.childoverview', {
            url: '/childoverview?key',
            views: {
                'content@': {
                    controller: 'ChildoverviewController',
                    templateUrl: 'js/app/childoverview/childoverview.tpl.html'
                }
            },
            resolve: {
                child: function(GApi, $stateParams, modelHolder) {
                    if (angular.isDefined($stateParams.key)) {
                        return GApi.execute('viacnezsperkAPI', 'sperk.fulluser', {identifier: $stateParams.key}).then(function (resp) {
                            return resp;
                        }, function () {
                            console.log("error :(");
                        });
                    } else {
                        return modelHolder.getChild();
                    }
                }
            }
        })

    }])

    .service('overviewService', [function() {

        this.createGallerylist = function(galleryItems) {
            var list = [];

            for (var key in galleryItems) {
                var item = galleryItems[key];
                list.push({
                    thumb: item.thumbUrl,
                    img: item.imageUrl,
                    description: ''
                });
            }

            return list;
        };

    }])

    .controller('ChildoverviewController', ['$scope', '$state', 'modelHolder', '$sce', 'child', 'overviewService', function ChildoverviewCtrl($scope, $state, modelHolder, $sce, child, overviewService) {
        $scope.user = child;
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

        $scope.galleryList = overviewService.createGallerylist($scope.user.galleryItems);

        $scope.getHtmlContent = function() {
            return $sce.trustAsHtml($scope.user.content.value); //html content is th binded content.
        };

    }]);