'use strict';
/*Device Model
* Defining all methods to request the server*/
DeviceController.factory('DeviceFactory', ['$http', '$q', function($http, $q){

    var factory = { 
        /*Return all devices*/
        find : function(options){
            /* Promises */
            var deferred = $q.defer();
            $http.get(URL_DEVICE).then(function(response) {
                factory.devices = response.data;
                deferred.resolve(factory.devices);
            }, function(error) {
                deferred.reject('error : '+error);
            });
            
            return deferred.promise;
        },

        /*get a device from its id*/
        get : function(id){
            /* Promises */
            // /!\ : Change later because this will retrieve all devices and get the good one by its id
            // why don't we create a REST like : .../device/{id} ?
            var deferred = $q.defer();
            var device = {};
            var device = factory.find().then(function(devices){
                angular.forEach(devices, function(value,key){
                    if(value.id == id){
                        device = value;
                       
                    }
                });
                //console.log(device);
                deferred.resolve(device);
            }, function(msg){
                deferred.reject(msg);

            });
            
            return deferred.promise;
        },

        /*test a device*/
        testCapability : function(paramInfos){
            $http.defaults.headers.common.Authorization = undefined;
            console.log($http.defaults.headers.common.Authorization);
            
            
            var deferred = $q.defer();
            var objHttp = {
                url: paramInfos['url'],
                method: paramInfos['method'],
                data: paramInfos['configParams']
            };

            switch (paramInfos['method']) {
                case "POST":
                    //objHttp.data = paramInfos['configParams'];
                    objHttp.headers = {};
                    objHttp.headers['Content-Type'] = 'application/x-www-form-urlencoded';
                    break;
                // Implement later if necessary
                case "GET":
                    
                    break;
                case "PUT":
                    
                    break;
                default:
                    break;
            }

            console.log('*** Sending AJAX request ***');
            console.log(objHttp);
            $http(objHttp).success(function (data, status, headers, config) {
                deferred.resolve(data);
            }).error(function (data, status, headers, config) {
                deferred.reject('Unable to test capability, status : '+status+', header : '+headers);
            });
            $http.defaults.headers.common.Authorization = 'Basic YWRtaW46YWRtaW4=';
            console.log($http.defaults.headers.common.Authorization);
            
         
            return deferred.promise;
        }
    };
    return factory;

}]);
