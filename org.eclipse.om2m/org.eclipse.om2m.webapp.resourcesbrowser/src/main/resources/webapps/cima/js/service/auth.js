'use strict';

AuthController.factory('AuthService', ['$q', '$http', function($q, $http) {
    this.login = function(data) {
        var deferred = $q.defer();
        $http.post(URL_LOGIN, data).then(
            function(result) {
                deferred.resolve(result.data);
            },
            function(errors) {

            }
        );
        return deferred.promise;
        // This code below is just for local test. Remove when unnecessary
        
        // var result = {};
        // result.username = 'CIMA';
        // result.userpassword = 'CIMA';
        // result.error = 0;
        // return result;
    };

    return this;
}]);