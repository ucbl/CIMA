'use strict';


app.factory('ProfileService', ['$q', '$http', function($q, $http) {
    this.list = function() {
        var deferred = $q.defer();
        $http.get(URL_PROFILE).then(function(results) { //URL_PROFILE 'http://localhost:4040/angular-project/api/cima/robot/capabilitiesjson'
            var data = angular.copy(results.data);
            for (var key in data) {
                var transFormedData = data[key].capabilities.substr(1, data[key].capabilities.length-2); // remove " at the 0 and the end position
                console.log(transFormedData);
                data[key].capabilities = JSON.parse(transFormedData);
            }
            deferred.resolve(data);
        });
        // $http.get('http://localhost:4040/angular-project/api/cima/robot/capabilitiesjson').then(function(results) { //URL_PROFILE 
        //     deferred.resolve(results.data);
        // });
        return deferred.promise;
    };

    this.add = function(data) {
        var deferred = $q.defer();
        $http.post(URL_PROFILE, data).then(function(results) {
            deferred.resolve(results.data);
        });
        return deferred.promise;
    };

    this.edit = function(data) {
        var deferred = $q.defer();
        $http.put(URL_PROFILE, data).then(function(results) {
            deferred.resolve(results.data);
        });
        return deferred.promise;
    };

    this.delete = function(data) {
        var deferred = $q.defer();
        $http.delete(URL_PROFILE, data).then(function(results) {
            deferred.resolve(results.data);
        });
        return deferred.promise;
    };

    this.get = function(id) {
        var deferred = $q.defer();
        $http.get(URL_PROFILE, id).then(function(results) {
            deferred.resolve(results.data);
        });
        return deferred.promise;
    };
    return this;
}]);