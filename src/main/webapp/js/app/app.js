angular.module('viacnezsperk', [
    'ui.router',
    'ngSanitize',
    'ui.bootstrap',
    'ui.bootstrap.tpls',
    'angularFileUpload',
    //'ngAnimate',
    'pascalprecht.translate',
    'angular-google-gapi',
    'modal',
    'model',
    'login',
    'home',
    'adduser',
    'childoverview'
])

    .run(['GAuth', 'GApi', 'GData', '$state', '$rootScope', '$window', 'modelHolder', function (GAuth, GApi, GData, $state, $rootScope, $window, modelHolder) {
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

        //GAuth.checkAuth().then(
        //    function () {
        //        $state.go('home.list');
        //    },
        //    function () {
        //        $state.go('home.login');
        //    }
        //);

        $rootScope.logout = function () {
            GAuth.logout().then(
                function () {
                    $state.go('home.login');
                });
        };

        $rootScope.doLogin = function () {
            GAuth.login().then(function () {
                $rootScope.email = GData.getUser().email;
                if ($rootScope.email.indexOf('viacnezsperk.sk') != -1) {
                    $state.go('home.list');
                } else {
                    $rootScope.logout();
                }
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

        $rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
            if (toState.name !== 'home.login' && !$rootScope.isLogin() && modelHolder.getChild() == null) {
                event.preventDefault();
                $state.go('home.login');
            }

            if (toState === 'home.login' && $rootScope.isLogin()) {
                event.preventDefault();
                $state.go('home.list');
            }
            // transitionTo() promise will be rejected with
            // a 'transition prevented' error
        });

    }
    ])

    .config(['$urlRouterProvider', function ($urlRouterProvider) {
        $urlRouterProvider.otherwise("/list");
    }]);

