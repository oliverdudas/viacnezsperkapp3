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

    .controller('AddUserController', ['$scope', '$state', 'GApi', 'GAuth', 'child', 'FileUploader', function AddUserCtrl($scope, $state, GApi, GAuth, child, FileUploader) {
        $scope.uploader = new FileUploader({
            //url: 'http://localhost:8080/_ah/upload/ahB2aWFjbmV6c3BlcmthcHAzciILEhVfX0Jsb2JVcGxvYWRTZXNzaW9uX18YgICAgICAoAsM',
            url: '/upload',
            alias: 'myFile',
            formData: [{
                accessToken: GAuth.getToken().$$state.value.access_token
            }],
            method: 'POST',
                onAfterAddingFile: function(item) {
                console.log(111111111111);
            },
            onSuccessItem: function(item, response, status, headers) {
                console.log('done item: ' + item);
                console.log('gphotoId: ' + response.gphotoId);
                console.log('thumbUrl: ' + response.thumbUrl);
                console.log('imageUrl: ' + response.imageUrl);
            }
        });
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