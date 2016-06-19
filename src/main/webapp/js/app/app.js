angular.module('viacnezsperk', [
    'ui.router',
    'ngSanitize',
    'ui.bootstrap',
    'ui.bootstrap.tpls',
    'angularFileUpload',
    //'ngAnimate',
    'pascalprecht.translate',
    'ngCookies',
    'angular-google-gapi',
    'jkuri.gallery',
    'modal',
    'model',
    'login',
    'home',
    'adduser',
    'childoverview'
])

    .run(['GAuth', 'GApi', 'GData', '$state', '$rootScope', '$window', 'modelHolder', '$translate', function (GAuth, GApi, GData, $state, $rootScope, $window, modelHolder, $translate) {
          //viacnezsperkapp1
          var CLIENT = '539843283811-7c9egdebuur3uc27qrpsacijml19264j.apps.googleusercontent.com';
        //  viacnezsperkapp3
        //var CLIENT = '638906425222-mi0677muktg56on3b679cj726ni46a3f.apps.googleusercontent.com';
        var BASE;
        if ($window.location.hostname == 'localhost') {
            BASE = '//localhost:8080/_ah/api';
        } else {
            //BASE = 'https://viacnezsperkapp3.appspot.com/_ah/api';
            BASE = 'https://viacnezsperkapp1.appspot.com/_ah/api';
            //BASE = 'https://deti.viacnezsperk.sk/_ah/api';
        }

        GApi.load('viacnezsperkAPI', 'v1', BASE);
        GAuth.setClient(CLIENT);
        GAuth.setScope('email https://picasaweb.google.com/data/');

        GAuth.checkAuth().then(
            function () {
                $state.go('home.list');
            },
            function () {
                $state.go('home.login');
            }
        );

        $rootScope.logout = function () {
            GAuth.logout().then(
                function () {
                    $state.go('home.login');
                });
        };

        $rootScope.doLogin = function () {
            GAuth.login().then(function () {
                $state.go('home.list');
            });
        };

        $rootScope.isLogin = function () {
            return GData.isLogin();
        };

        $rootScope.isLoginState = function () {
            return 'home.login' === $state.current.name;
        };

        $rootScope.isOverviewState = function () {
            return 'home.childoverview' === $state.current.name;
        };

        $rootScope.closeOverview = function () {
            $state.go('home.list');
        };

        $rootScope.getEmail = function () {
            return GData.getUser() != null ? GData.getUser().email : '';
        };

        $rootScope.useLanguage = function(langKey) {
            $translate.use(langKey);
        };

        //$rootScope.$on('$translateChangeEnd', function (key) {
        //    console.log('change: ' + key);
        //    Cufon.replace('h1', {fontFamily: 'ArnoPro', hover: true});
        //});

    }])

    .factory('myHttpInterceptor', function($q) {
        return {
            // optional method
            'request': function(config) {
                return config;
            },
            // optional method
            'requestError': function(rejection) {
                //if (canRecover(rejection)) {
                //    return responseOrNewPromise
                //}
                return $q.reject(rejection);
            },
            // optional method
            'response': function(response) {
                return response;
            },
            // optional method
            'responseError': function(rejection) {
                //if (canRecover(rejection)) {
                //    return responseOrNewPromise
                //}
                return $q.reject(rejection);
            }
        };
    })

    .config(['$urlRouterProvider', '$httpProvider', function ($urlRouterProvider, $httpProvider) {
        $httpProvider.interceptors.push('myHttpInterceptor');
        //$urlRouterProvider.otherwise("/login");
    }]);

