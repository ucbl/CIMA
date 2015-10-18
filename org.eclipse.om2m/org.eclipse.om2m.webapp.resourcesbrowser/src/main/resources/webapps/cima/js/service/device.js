'use strict';
/*Device Model
* Defining all methods to request the server*/
app.factory('DeviceFactory', ['$http', '$q', function($http, $q){

    var factory = { 
        /*Return all devices*/
        find : function(options){
                /* Promesses */
                var deferred = $q.defer();
                /* First call to initialise */
                // $.ajax({
                //     url : URL_DEVICE,
                //     type : 'GET',
                //     dataType : 'json',
                //     success : function(data, status){   
                //         factory.devices = data;
                //         deferred.resolve(factory.devices);
                //     },
                //     error : function(result, status, error){
                //         deferred.reject('status : '+status+', error : '+error+'.');
                //     },
                //     beforeSend: function(xhr, settings) { 
                //         xhr.setRequestHeader('Authorization','Basic YWRtaW46YWRtaW4=');
                //     }
                // });
               
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
            var deferred = $q.defer();
            var device = {};
            var device = factory.find().then(function(devices){
                angular.forEach(devices, function(value,key){
                    if(value.id == id){
                        device = value;
                       
                    }
                });
                deferred.resolve(device);
            }, function(msg){
                deferred.reject(msg);

            });
            
            return deferred.promise;
        },

        /*test a device*/
        testCapability : function(idDevice, capability){
            var deferred = $q.defer();
            // $http({
            //     //url : URL_DEVICE + '/' + idDevice+'/test',    
            //     url: URL_DEVICE + '/' + idDevice+'/test',
            //     method: "POST",
            //     data: capability,
            //     headers: {'Content-Type': 'application/json'}
            // }).success(function (data, status, headers, config) {
            //         deferred.resolve();
            // }).error(function (data, status, headers, config) {
            //         deferred.reject('Unable to test capability, status : '+status+', header : '+headers);
            // });
            
            $http.post(URL_DEVICE + '/' + idDevice + '/test', {
                data: capability
            }).then(function(response) {
                deferred.resolve();
            }, function(error) {
                deferred.reject('Unable to test capability, error : ' + error);
            });
            return deferred.promise;
        },

        /*add capability*/
        addCapability : function(idDevice, capability){
            var deferred = $q.defer();
            //var jsonld = new jsonld();            
            // $http({
            //     url: URL_DEVICE + '/' + idDevice + '/capability/'+capability.id+"/",
            //     method: "PUT",
            //     data: capability,
            //     headers: {'Content-Type': 'application/json'}
            // }).success(function (data, status, headers, config) {
            //     deferred.resolve();
            // }).error(function (data, status, headers, config) {
            //     //jsonld.objectify(data,status,headers,config);
            //     /*jsonld.compact(capability, capability, function(err, compacted) {
            //         console.log(JSON.stringify(compacted, null, 2));  
            //     });*/
            //     console.log(JSON.stringify(data));
            //     deferred.reject('Unable to add capability, status : '+status+', header : '+headers);
            // });
            
            // $http.put(URL_DEVICE + '/' + idDevice + '/capability/' + capability.id + '/', {
            //     data: capability
            // }).
            // success(function(data, status, headers, config) {
            //     deferred.resolve();
            // }).
            // error(function(data, status, headers, config) {
            //     console.log(JSON.stringify(data));
            //     deferred.reject('Unable to add capability, status : '+status+', header : '+headers);
            // });
            
            $http.put(URL_DEVICE + '/' + idDevice + '/capability/' + capability.id + '/', {
                data: capability
            }).then(function(response) {
                deferred.resolve();
            }, function(error) {
                deferred.reject('Unable to add capability, error : ' + error);
            });
            return deferred.promise;
        },

        /*save a device (server side)*/
        saveDevice : function(device){
            var deferred = $q.defer();
            // $http({
            //     url: URL_DEVICE+'/'+device.id+'/',
            //     method: "PUT",
            //     data: device,
            //     headers: {'Content-Type': 'application/json'}
            // }).success(function (data, status, headers, config) {
            //     deferred.resolve();
            // }).error(function (data, status, headers, config) {
            //     //deferred.reject('Unable to save device, status : '+status+', header : '+headers);
            //     deferred.resolve();
            // });
           
            $http.put(URL_DEVICE+'/'+device.id+'/', {
                data: device
            }).then(function(response) {
                deferred.resolve();
            }, function(error) {
                deferred.reject('Unable to save device, error : ' + error);
            });
            return deferred.promise;
        },

        /*modifying capability*/
        modifyCapability : function(idDevice, capability){
            var deferred = $q.defer();
            // $http({
            //     url: URL_DEVICE+'/'+idDevice+'/capability/'+capability.id+"/",
            //     method: "PUT",
            //     data: capability,
            //     headers: {'Content-Type': 'application/json'}
            // }).success(function (data, status, headers, config) {
            //     deferred.resolve();
            //     alert('success');
            // }).error(function (data, status, headers, config) {
            //     deferred.reject('Unable to modify capability, status : '+status+', header : '+headers);
            // });
            
            
            $http.put(URL_DEVICE + '/' + idDevice + '/capability/' + capability.id + '/', {
                data: capability
            }).then(function(response) {
                deferred.resolve();
            }, function(error) {
                deferred.reject('Unable to modify capability, error : ' + error);
            });
            return deferred.promise;
        },

        /*deleting a capability*/
        removeCapability : function(idDevice, idCapability){
            var deferred = $q.defer();
            // $http({
            //     url: URL_DEVICE+'/'+idDevice+'/capability/'+idCapability+"/",
            //     method: "DELETE",
            //     data: "",
            //     headers: {'Content-Type': 'application/json'}
            // }).success(function (data, status, headers, config) {
            //     deferred.resolve();
            // }).error(function (data, status, headers, config) {
            //     deferred.reject('Unable to delete capability, status : '+status+', header : '+headers);
            // });
            // $http.delete(URL_DEVICE + '/' + idDevice + '/capability/' + idCapability + '/').
            // success(function(data, status, headers, config) {
            //     deferred.resolve();
            // }).
            // error(function(data, status, headers, config) {
                
            //     deferred.reject('Unable to delete capability, status : '+status+', header : '+headers);
            // });
            
            $http.delete(URL_DEVICE + '/' + idDevice + '/capability/' + idCapability + '/').
            then(function(response) {
                deferred.resolve();
            }, function(error) {
                deferred.reject('Unable to delete capability, error : ' + error);
            });
            return deferred.promise;
        }

    };
    return factory;

}]);
