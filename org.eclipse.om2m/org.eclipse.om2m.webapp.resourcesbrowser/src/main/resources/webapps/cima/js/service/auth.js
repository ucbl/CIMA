'use strict';

app.factory('AuthService', ['$q', '$http', function($q, $http) {
    this.login = function(data) {
        var deferred = $q.defer();
        $http.post(URL_DEVICE + '/login', data).then(
            function(result) {
                deferred.resolve(result.data);
            },
            function(errors) {

            }
        );
        return deferred.promise;
    };

    this.logout = function() {

    };

    this.isLogin = function() {

    };

    return this;
}]);