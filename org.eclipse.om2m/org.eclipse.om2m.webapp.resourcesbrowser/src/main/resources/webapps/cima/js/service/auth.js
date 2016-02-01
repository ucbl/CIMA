'use strict';

app.factory('AuthService', ['$q', '$http', function($q, $http) {
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
        // var result = {};
        // result.username = 'CIMA';
        // result.errors = 0;
        // return result;
    };

    this.logout = function() {

    };

    this.isLogin = function() {

    };

    return this;
}]);