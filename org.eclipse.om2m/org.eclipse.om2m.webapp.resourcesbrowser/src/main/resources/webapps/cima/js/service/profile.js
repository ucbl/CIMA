'use strict';

app.factory('ProfileService', ['$q', '$http', function($q, $http) {
    this.getAllProfiles = function() {
        var deferred = $q.defer();
        $http.get('http://localhost:4040/angular-project/api/cima/robot/capabilitiesjson').then(function(results) {
            deferred.resolve(results.data);
        });
        return deferred.promise;
    };

    return this;
}]);