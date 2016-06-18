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

    .factory('pager', ['listHolder', function(listHoler) {
        var _pageNumber = 1;
        var _itemsPerPage = 10;

        var _isFirstPage = function() {
            return _pageNumber === 1;
        };

        var _isLastPage = function() {
            var size = listHoler.getListSize();
            var maxLast = _pageNumber * _itemsPerPage;
            return size <= (size <= maxLast && size > (maxLast - _itemsPerPage ));
        };

        var _isValidPage = function(pageNumber) {
            return pageNumber > 0 && (pageNumber * _itemsPerPage - _itemsPerPage < listHoler.getListSize());
        };

        var _getEndIndex = function() {
            return _pageNumber * _itemsPerPage;
        };

        var _getStartIndex = function() {
            return _getEndIndex() - _itemsPerPage;
        };

        var _getItems = function (direction) {
            var newPageNumber = _pageNumber + direction;
            if (_isValidPage(newPageNumber)) {
                _pageNumber = newPageNumber;
            }
            return listHoler.getList().slice(_getStartIndex(), _getEndIndex());
        };

        return {

            getPageNumber: function() {
                return _pageNumber;
            },

            getItemsPerPage: function() {
                return _itemsPerPage;
            },

            apply: function(direction, callbackFn) {
                var items = _getItems(direction);
                callbackFn.call(undefined, items);
            }

        };
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

            getListSize: function() {
                return this.getList().length;
            },

            clearList: function () {
                _list = null;
            },

            unshiftItem: function (item) {
                if (_list === null) {
                    _list = [];
                }
                _list.unshift(item);
            },

            deleteItem: function (key) {
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

    .service('listService', ['pager', function (pager) {
        this.filterUsers = function (users, q) {
            var key;
            var user;
            var result = [];
            var counter = 1;
            var itemsPerPage = pager.getItemsPerPage();
            for (key in users) {
                if (counter > itemsPerPage) break;

                user = users[key];
                if (user.username.toLowerCase().indexOf(q.toLowerCase()) === 0) {
                    result.push(user);
                    counter++;
                }
            }
            return result;
        };
    }])

    .filter('highlightsearch', function () {
        return function (username, searchstring) {
            if (angular.isDefined(searchstring)) {
                var searchLength = searchstring.length;
                var searchPart = username.substring(0, searchLength);
                var otherPart = username.substring(searchLength, username.length);
                return "<strong>" + searchPart + "</strong>" + otherPart;
            } else {
                return username;
            }
        };
    })

    .controller('ListController', ['$scope', '$state', 'GApi', 'listService', 'listHolder', 'pager', function ListCtrl($scope, $state, GApi, listService, listHolder, pager) {

        $scope.pager = pager;

        $scope.applyPager = function(direction) {
            pager.apply(direction, function(items) {
                $scope.users = items;
            });
        };

        $scope.applyPager(0);

        $scope.$watch('typeahead', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                $scope.users = listService.filterUsers(listHolder.getList(), newVal);
            }
        });

        $scope.addUser = function () {
            $state.go('home.adduser');
        };

        $scope.edit = function (key) {
            $state.go('home.adduser', {key: key});
        };

        //$scope.$on('$viewContentLoaded', function () {
        //    Cufon.replace('h1', {fontFamily: 'ArnoPro', hover: true});
        //});

        $scope.test = function () {
            GApi.execute('viacnezsperkAPI', 'sperk.getDummyRequest').then(function (resp) {
                console.log('returned');
            }, function () {
                console.log("error :(");
            });
        };

        $scope.toPreview = function (key, $event) {
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