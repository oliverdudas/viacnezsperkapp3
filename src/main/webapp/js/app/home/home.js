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
                        users: function ($q, GApi, listHolder, $state) {
                            var list = listHolder.getList();
                            if (list === null) {
                                return GApi.execute('viacnezsperkAPI', 'sperk.getUsers').then(function (resp) {
                                    listHolder.setList(resp.items);
                                    return listHolder.getList();
                                }, function (resp) {
                                    console.log(resp.error.message);
                                    $state.go('home.login');
                                    //return $q.reject(resp.error.message);
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

    .factory('pager', ['listHolder', 'listService', function(listHolder, listService) {
        var _items = undefined;
        var _filteredItems = undefined;
        var _firstPageNumber = 1;
        var _pageNumber = _firstPageNumber;
        var _itemsPerPage = 10;

        var _isFirstPage = function() {
            return _pageNumber === _firstPageNumber;
        };

        var _isLastPage = function() {
            var nextPageNumber = _pageNumber + 1;
            return nextPageNumber * _itemsPerPage - _itemsPerPage >= _filteredItems.length;
        };

        var _isValidPage = function(pageNumber) {
            return pageNumber > 0 && pageNumber >= _firstPageNumber && pageNumber <= _getLastPageNumber();
        };

        var _getEndIndex = function() {
            return _pageNumber * _itemsPerPage;
        };

        var _getStartIndex = function() {
            return _getEndIndex() - _itemsPerPage;
        };

        var _getLastPageNumber = function() {
            return Math.ceil(_filteredItems.length / _itemsPerPage);
        };

        var _apply = function(direction) {
            _applyWithFilter(direction, "");
        };

        var _applyWithFilter = function(direction, q) {
            _filteredItems = listService.filterUsers(listHolder.getList(), q);
            var newPageNumber = _pageNumber + direction;
            if (_isValidPage(newPageNumber)) {
                _pageNumber = newPageNumber;
            } else if (newPageNumber > _getLastPageNumber()) {
                _pageNumber = _getLastPageNumber();
            } else if (_pageNumber === 0 &&_getLastPageNumber() > 0) {
                _pageNumber = 1;
            }
            _items = _filteredItems.slice(_getStartIndex(), _getEndIndex());
        };

        return {

            apply: function(direction) {
                _apply(direction);
            },

            applyWithFilter: function(direction, q) {
                _applyWithFilter(direction, q);
            },

            getItems: function() {
                return _items;
            },

            getPageNumber: function() {
                return _pageNumber;
            },

            getLastPageNumber: function() {
                return _getLastPageNumber();
            },

            getItemsPerPage: function() {
                return _itemsPerPage;
            },

            isFirstPage: function() {
                return _isFirstPage();
            },

            isLastPage: function() {
                return _isLastPage();
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

    .service('listService', [function () {
        this.filterUsers = function (users, q) {
            var key;
            var user;
            var result = [];
            var counter = 1;
            var itemsPerPage = 30;
            if (angular.isUndefined(q) || q === "") {
                result = users;
            } else {
                for (key in users) {
                    if (counter > itemsPerPage) break;

                    user = users[key];
                    if (user.username.toLowerCase().indexOf(q.toLowerCase()) === 0) {
                        result.push(user);
                        counter++;
                    }
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

        pager.apply(0);

        $scope.$watch('typeahead', function (newVal, oldVal) {
            if (newVal !== oldVal) {
                pager.applyWithFilter(0, newVal);
            }
        });

        $scope.addUser = function () {
            $state.go('home.adduser');
        };

        $scope.edit = function (key) {
            $state.go('home.adduser', {key: key});
        };

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

        //$scope.$on('$viewContentLoaded', function () {
        //    Cufon.replace('h1', {fontFamily: 'ArnoPro', hover: true});
        //});        //$scope.$on('$routeChangeSuccess', function () {
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