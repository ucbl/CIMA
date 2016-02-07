'use strict';
/* Application modules */
var CRYPTOKEY = 'CIMACIMACIMACIMACIMA';
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
    // when using jsonld, add the angularJsld as a DI into the module, add jsonldContextProvider as DI into app.config
    // jsonldContextProvider.add({
    //     //'vocab': 'http://localhost:4040/angular-project/api/cima/phonevocabJSONLD/',
    //     'name': 'vocab:robotEV3/name',
    //     'description': 'vocab:robotEV3/description',
    //     'id': 'vocab:robotEV3/id',
    //     'portforwarding': 'vocab:robotEV3/Portforwarding',
    //     'connection': 'vocab:Connection',
    //     'dateConnection': 'vocab:Connection/DateConnection',
    //     'protocol': 'vocab:Connection/Protocol',
    //     'address': 'vocab:Connection/Address',
    //     'configuration': 'vocab:Configuration',
    //     'automaticConfiguration': 'vocab:Configuration/AutomaticConfiguration',
    //     'profile': 'vocab:Configuration/Profile',
    //     'capabilities': 'vocab:robotEV3/capabilities',
    //     "idCapability": "vocab:id",
    //     "result": "vocab:result",
    //     "protocolCapability": "vocab:protocol",
    //     "protocolName": "vocab:protocolName",
    //     "parameters": "vocab:parameters",
    //     "nameParamCapability": "vocab:name",
    //     "value": "vocab:value",
    //     "cloudPort": "vocab:cloudPort",
    //     "params": "vocab:params",
    //     "desc": "vocab:desc",
    //     "idp": "vocab:idp",
    //     "type": "vocab:type",
    //     "configurationCapability": "vocab:configuration"
    // });
}]);
/* bootstrap UI module */
//angular.module('CIMA.ui', ['ui.bootstrap']);
//angular.module('CIMA.tags', ['ngTagsInput']);
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
        if ($rootScope.$storage.userSession) {
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
        if ($rootScope.$storage.userSession) {
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