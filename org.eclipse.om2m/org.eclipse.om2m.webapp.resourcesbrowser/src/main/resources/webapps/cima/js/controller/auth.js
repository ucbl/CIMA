'use strict';
app.controller('AuthController', ['$scope', '$rootScope', '$localStorage', '$location', 'AuthService', function($scope, $rootScope, $localStorage, $location, AuthService) {
    $scope.login = function(user) {
        AuthService.login(user).then(
            function(results) {
                $scope.errors = results && results.errors;
                if (!results.errors) {
                    $rootScope.$storage = $localStorage;
                    $rootScope.$storage.userSession = results && results.username;
		    $location.path('/');
                } else {
                    $scope.errors = results.errors;
                }
            },
            function(errors) {

            }
        );
        // var results 
        // if (!results.errors) {
        //     $rootScope.$storage = $localStorage;
        //     $rootScope.$storage.userSession = results && results.username;
        // } else {
        //     $scope.errors = results.errors;
        // }
    };
    
}]);
