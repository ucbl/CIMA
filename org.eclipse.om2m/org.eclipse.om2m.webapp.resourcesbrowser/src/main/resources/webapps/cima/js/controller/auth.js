'use strict';

var AuthController = angular.module('AuthController', []);

AuthController.controller('AuthController', ['$scope', '$rootScope', '$localStorage', '$location', 'AuthService', 'GooglePlus', 'md5', function($scope, $rootScope, $localStorage, $location, AuthService, GooglePlus, md5) {
    $scope.loginGoogle = function() {
        // lib/angular-google-plus.js
        GooglePlus.login().then(function (authResult) {
            GooglePlus.getUser().then(function (user) {
                $rootScope.$storage = $localStorage;
                $rootScope.$storage.userSession = user.name;
                $location.path('/');
                console.log(user);
            });
        }, function (err) {
            console.log(err);
        });

        
    };

    $scope.login = function(user) {
        var cryptingUser = angular.copy(user);
        cryptingUser.userpassword = md5.createHash(user.userpassword);
        AuthService.login(cryptingUser).then(function(results) {
            console.log(results);
            if (results.error == 0) {
                console.log('logged in');
                $rootScope.$storage = $localStorage;
                $rootScope.$storage.userSession = user.username;
                $location.path('/');
            } else {
                $scope.errors = results.errors;
            }
        }, function(errors) {});
        
        // var results = AuthService.login(cryptingUser);
        // if (!results.errors) {
        //     $rootScope.$storage = $localStorage;
        //     $rootScope.$storage.userSession = results && results.username;
        //     $location.path('/');
        // } else {
        //     $scope.errors = results.errors;
        // }
    };

}]);