angular.module('modal', [])

    .service('modalService', ['$modal', function ($modal) {

        this.openDeleteDialog = function (thumbUrl, callbackFn) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'js/app/modal/modal.tpl.html',
                controller: 'ModalController',
                size: 'sm',
                resolve: {
                    content: function () {
                        return {
                            thumbUrl: thumbUrl
                        };
                    }
                }
            });

            modalInstance.result.then(function (selectedItem) {
                callbackFn.apply();
            }, function () {
                console.log('Item deletion canceled.');
            });
        };

        this.openUserDeleteDialog = function (child, callbackFn) {
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'js/app/modal/modaluserdelete.tpl.html',
                controller: 'ModalController',
                size: 'sm',
                resolve: {
                    content: function () {
                        return {
                            username: child.username
                        };
                    }
                }
            });

            modalInstance.result.then(function (selectedItem) {
                callbackFn.apply();
            }, function () {
                console.log('User deletion canceled.');
            });
        };

        this.openUploadCompleteDialog = function(thumbUrlArray, text) {
            var size = thumbUrlArray.length > 2 ? 'lg' : 'sm';
            var modalInstance = $modal.open({
                animation: true,
                templateUrl: 'js/app/modal/modalupload.tpl.html',
                controller: 'ModalUploadController',
                size: size,
                resolve: {
                    content: function () {
                        return {
                            text: text,
                            thumbUrlArray: thumbUrlArray
                        };
                    }
                }
            });

        }

    }])

    .controller('ModalController', ['$scope', '$modalInstance', 'content', function ($scope, $modalInstance, content) {

        $scope.content = content;

        $scope.ok = function () {
            $modalInstance.close();
        };

        $scope.cancel = function () {
            $modalInstance.dismiss();
        };
    }])

    .controller('ModalUploadController', ['$scope', '$modalInstance', 'content', function ($scope, $modalInstance, content) {

        $scope.content = content;

        $scope.ok = function () {
            $modalInstance.close();
        };

    }]);