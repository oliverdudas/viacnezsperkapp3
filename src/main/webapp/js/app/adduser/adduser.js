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
                                return GApi.execute('viacnezsperkAPI', 'sperk.fulluser', {identifier: $stateParams.key}).then(function (resp) {
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

    .service('addUserService', ['listHolder', 'FileUploader', 'GAuth', 'modalService', '$filter', function(listHolder, FileUploader, GAuth, modalService, $filter) {
        this.prepareChild = function(child, newKey) {
            var isNewChild = angular.isUndefined(child.identifier);
            var actualDate = new Date();
            if (isNewChild) {
                child.created = actualDate;
                child.identifier = newKey;
            } else {
                listHolder.deleteItem(child.identifier); // delete old child from listholder
            }

            listHolder.unshiftItem(child); // add updated child to listholder
            child.modified = actualDate;
        };

        this.createUploader = function(successCallbackFn) {
            var successThumbs = [];
            var errorItems = [];
            return new FileUploader({
                //url: 'http://localhost:8080/_ah/upload/ahB2aWFjbmV6c3BlcmthcHAzciILEhVfX0Jsb2JVcGxvYWRTZXNzaW9uX18YgICAgICAoAsM',
                url: '/upload',
                alias: 'myFile',
                formData: [{
                    accessToken: GAuth.getToken().$$state.value.access_token
                }],
                method: 'POST',
                onSuccessItem: function(item, response, status, headers) {
                    console.log('done item: ' + item);
                    console.log('gphotoId: ' + response.gphotoId);
                    console.log('thumbUrl: ' + response.thumbUrl);
                    console.log('imageUrl: ' + response.imageUrl);
                    console.log('timestamp: ' + response.timestamp);
                    successCallbackFn.apply(null, [response.gphotoId, response.thumbUrl, response.imageUrl, response.timestamp]);
                    successThumbs.push(response.thumbUrl);
                },
                onErrorItem: function(item, response, status, headers) {
                    errorItems.push(response);
                },
                onCompleteAll: function() {
                    var sum = successThumbs.length + errorItems.length;
                    modalService.openUploadCompleteDialog(successThumbs, $filter('translate')('successfully_uploaded_photos') + ' ' + successThumbs.length + '/' + sum);
                    successThumbs = [];
                    errorItems = [];
                }
            });
        };

        /**
         * Generates random number between 1000 inclusive and 9999 exclusive
         * @returns {number}
         */
        this.generateRandomPassword = function() {
            return Math.floor(Math.random() * (9999 - 1000) + 1000);
        };

    }])

    .controller('AddUserController', ['$scope', '$state', 'GApi', 'GAuth', 'child', 'listHolder', 'addUserService', 'modalService', function AddUserCtrl($scope, $state, GApi, GAuth, child, listHolder, addUserService, modalService) {
        $scope.uploader = addUserService.createUploader(function(gphotoId, thumbUrl, imageUrl, timestamp) {
            console.log('Gallery uploader done: \ngphotoId: ' + gphotoId + '\nthumbUrl: ' + thumbUrl + '\nimageUrl: ' + imageUrl + '\ntimestamp: ' + timestamp);
            if (angular.isUndefined($scope.child.galleryItems)) {
                $scope.child.galleryItems = [];
            }
            $scope.child.galleryItems.push({
                gphotoId: gphotoId,
                imageUrl: imageUrl,
                thumbUrl: thumbUrl,
                timestamp: timestamp,
                index: 1,
                crudAction: 'put'
            });
        });

        $scope.titleUploader = addUserService.createUploader(function(gphotoId, thumbUrl, imageUrl, timestamp) {
            console.log('Title uploader done: \ngphotoId: ' + gphotoId + '\nthumbUrl: ' + thumbUrl + '\nimageUrl: ' + imageUrl + '\ntimestamp: ' + timestamp);
            $scope.child.mainURL = imageUrl;
        });

        $scope.child = child;

        $scope.mainURLs = function() {
            return [$scope.child.mainURL];
        };

        $scope.generatePassword = function() {
            $scope.child.password = addUserService.generateRandomPassword();
        };

        $scope.put = function () {
            GApi.execute('viacnezsperkAPI', 'sperk.putUser', $scope.child).then(function (resp) {

                //------------------------------------------------------------------------------------------//
                // TODO: the putUser should return the child, that we can update listHolder with fresh data //
                //------------------------------------------------------------------------------------------//

                addUserService.prepareChild($scope.child, resp.identifier);

                $state.go('home.list');
            }, function (resp) {
                alert(resp.error.message);
            });
        };

        $scope.toList = function() {
            $state.go('home.list');
        };

        $scope.deleteItem = function(item) {
            modalService.openDeleteDialog(item.thumbUrl, function() {
                item.deleted = 'deleted';
                if (angular.isUndefined(item.crudAction)) {
                    item.crudAction = 'delete';
                } else {
                    item.crudAction = undefined;
                }
                console.log('Image will be deleted.');
            });
        };

    }
    ])
    .directive('ngThumb', ['$window', function($window) {
        var helper = {
            support: !!($window.FileReader && $window.CanvasRenderingContext2D),
            isFile: function(item) {
                return angular.isObject(item) && item instanceof $window.File;
            },
            isImage: function(file) {
                var type =  '|' + file.type.slice(file.type.lastIndexOf('/') + 1) + '|';
                return '|jpg|png|jpeg|bmp|gif|'.indexOf(type) !== -1;
            }
        };

        return {
            restrict: 'A',
            template: '<canvas/>',
            link: function(scope, element, attributes) {
                if (!helper.support) return;

                var params = scope.$eval(attributes.ngThumb);

                if (!helper.isFile(params.file)) return;
                if (!helper.isImage(params.file)) return;

                var canvas = element.find('canvas');
                var reader = new FileReader();

                reader.onload = onLoadFile;
                reader.readAsDataURL(params.file);

                function onLoadFile(event) {
                    var img = new Image();
                    img.onload = onLoadImage;
                    img.src = event.target.result;
                }

                function onLoadImage() {
                    var width = params.width || this.width / this.height * params.height;
                    var height = params.height || this.height / this.width * params.width;
                    canvas.attr({ width: width, height: height });
                    canvas[0].getContext('2d').drawImage(this, 0, 0, width, height);
                }
            }
        };
    }]);