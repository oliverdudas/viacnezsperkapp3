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
                        users: function (GApi, listHolder) {
                            var list = listHolder.getList();
                            if (list === null) {
                                return GApi.execute('viacnezsperkAPI', 'sperk.getUsers').then(function (resp) {
                                    listHolder.setList(resp.items);
                                    return listHolder.getList();
                                }, function () {
                                    console.log("error :(");
                                });
                            } else {
                                return list;
                            }
                        }
                    }
                }
            }
        });

    }])

    .factory('listHolder', [function () {
        var _list = null;
        return {

            setList: function (list) {
                _list = list;
            },

            getList: function () {
                return _list;
            },

            clearList: function () {
                _list = null;
            },

            unshiftItem: function(item) {
                if (_list === null) {
                    _list = [];
                }
                _list.unshift(item);
            },

            deleteItem: function(key) {
                if (_list !== null) {
                    var listIndex;
                    var item;
                    for (listIndex in _list) {
                        item = _list[listIndex];
                        if (key === item.identifier) {
                            return _list.splice(listIndex, 1);
                        }
                    }
                }
            }
        }
    }])

    .service('listService', [function () {
        //this.filterUsers = function(users, q) {
        //    var counter = 0;
        //    return users.filter(function (user, index) {
        //        if (counter > 9) return false;
        //        counter++;
        //        return user.username.indexOf(q) === 0;
        //    });
        //};
        this.filterUsers = function (users, q) {
            var key;
            var user;
            var result = [];
            var counter = 0;
            for (key in users) {
                if (counter > 9) break;

                user = users[key];
                if (user.username.indexOf(q) === 0) {
                    result.push(user);
                    counter++;
                }
            }
            return result;
        };
    }])

    .controller('ListController', ['$scope', '$state', 'GApi', 'listService', 'listHolder', function ListCtrl($scope, $state, GApi, listService, listHolder) {

        var getUsers = function () {
            return listHolder.getList();
        };

        $scope.users = getUsers().slice(0, 10);

        $scope.$watch('typeahead', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.users = listService.filterUsers(getUsers(), newVal);
            }
        });

        $scope.addUser = function () {
            $state.go('home.adduser');
        };

        $scope.edit = function (key) {
            $state.go('home.adduser', {key: key});
        };

        $scope.$on('$viewContentLoaded', function () {
            Cufon.replace('h1', {fontFamily: 'ArnoPro', hover: true});
        });

        $scope.test = function () {
            GApi.execute('viacnezsperkAPI', 'sperk.getDummyRequest').then(function (resp) {
                console.log('returned');
            }, function () {
                console.log("error :(");
            });
        };

        $scope.toPreview = function(key, $event) {
            $event.stopPropagation();
            $state.go('home.childoverview', {key: key})
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