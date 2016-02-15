'use strict';
/* Application modules */

var app = angular.module('CIMA', ['ngRoute', 'googleplus', 'ui.bootstrap', 'ngTagsInput', 'CIMA.toast', 'ngStorage', 'angular-md5', 'HomeController', 'DeviceController', 'AuthController', 'ProfileController']);
/*routing URLs*/
app.config(['$routeProvider', '$httpProvider', 'GooglePlusProvider', function($routeProvider, $httpProvider, GooglePlusProvider) {
    
    GooglePlusProvider.init({
        clientId: '259163117056-j6sjjs4pm54ujvffo6s247an6ssqg5g1.apps.googleusercontent.com',
        apiKey: 'AIzaSyCYZbWdunI_87_XOa9v0GO6W6fwZ7mSIPY'
    });
    $routeProvider.when('/', {
        templateUrl: 'partials/home.html',
        controller: 'HomeController'
    }).when('/device/:id', {
        templateUrl: 'partials/device.html',
        controller: 'DeviceController'
    }).when('/login', {
        templateUrl: 'partials/login.html',
        controller: 'AuthController'
    }).when('/profile', {
        templateUrl: 'partials/profile/list.html',
        controller: 'ProfileController'
    }).when('/profile/add', {
        templateUrl: 'partials/profile/add.html',
        controller: 'AddProfileController'
    }).when('/profile/:id', {
        templateUrl: 'partials/profile/edit.html',
        controller: 'EditProfileController'
    }).otherwise({
        redirectTo: '/'
    });
    
}]);
/* bootstrap UI module */

angular.module('CIMA.toast', ['ngToast']).config(['ngToastProvider', function(ngToast) {
    ngToast.configure({
        verticalPosition: 'bottom',
        horizontalPosition: 'right',
        dismissButton: true,
        maxNumber: 3,
    });
}]);
/*Auth header for http requests*/
app.run(['$http', '$rootScope', '$localStorage', '$location', 'GooglePlus', function($http, $rootScope, $localStorage, $location, GooglePlus) {
    // /!\: This command below PREVENT the interface from sending POST data (speed, angle...) to connected object
    $http.defaults.headers.common.Authorization = 'Basic YWRtaW46YWRtaW4=';
    $rootScope.isLogin = false; // Is it in use ? Check it later
    $rootScope.$storage = $localStorage;
    $rootScope.logout = function() {
        if ($rootScope.$storage.userSession != null) {
            console.log(GooglePlus.getToken());
            if (GooglePlus.getToken()) {
                console.log('logging out');
                GooglePlus.logout();
                console.log(GooglePlus.getToken());
            }
            $localStorage.$reset();
            $location.path('/login');
        }
    };
    $rootScope.$on('$routeChangeStart', function(event) {
        if ($rootScope.$storage.userSession != null) {
            if ($location.path() == '/login') {
                alert('You have already logged in');
                $location.path('/');
            }
        } else {
            if ($location.path() != '/login') {
                //alert('You need to login to access this area');
                $location.path('/login');
            }
        }
    });
}]);