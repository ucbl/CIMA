'use strict';
/*Device Model
* Defining all methods to request the server*/
app.factory('DeviceFactory', ['$http', '$q', 'JsonldRest', function($http, $q, JsonldRest){
    //JsonldRest.setBaseUrl('http://localhost:4040/angular-project/api/');
    //var connectedObjects = JsonldRest.collection('/cima');
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
            // var data = [];
            // connectedObjects.one('devices').get().then(function(res) {
            //     for (var i = 0; i < res['@graph'].length; i++) {
            //         data.push(res['@graph'][i]);
            //     }
            //     deferred.resolve(data);
                
            // });
            return deferred.promise;
        },

        getAllCapabilitiesFromDevice : function(device) {
            var deferred = $q.defer();
            var capabilities = [];
            device.capabilities.forEach(function(capabilityLink, index) {
                capabilityLink.get().then(function(capability) {
                    capabilities.push(capability);
                    deferred.resolve(capabilities);
                });
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
            };

            switch (paramInfos['method']) {
                case "POST":
                    objHttp.data = paramInfos['configParams'];
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
            
            // $http.post("http://192.168.2.4:8080/A/rotate",{
            //     data: paramInfos['configParams']
            // }).then(function(response) {
            //     deferred.resolve(response.data);
            // }, function(error) {
            //     deferred.reject('Unable to test capability, error : ' + error);
            // });
            
            //getData(33, 60);
            return deferred.promise;
        },

        /*add capability*/
        addCapability : function(idDevice, capability){
            var deferred = $q.defer();
            //var jsonld = new jsonld();  
                      
            $http({
                url: URL_DEVICE + '/' + idDevice + '/capability/'+capability.id+"/",
                method: "PUT",
                data: capability,
                headers: {'Content-Type': 'application/json'}
            }).success(function (data, status, headers, config) {
                deferred.resolve();
            }).error(function (data, status, headers, config) {
                //jsonld.objectify(data,status,headers,config);
                jsonld.compact(capability, capability, function(err, compacted) {
                    console.log(JSON.stringify(compacted, null, 2));  
                });
                console.log(JSON.stringify(data));
                deferred.reject('Unable to add capability, status : '+status+', header : '+headers);
            });
            
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
            
            // $http.put(URL_DEVICE + '/' + idDevice + '/capability/' + capability.id + '/', {
            //     data: capability
            // }).then(function(response) {
            //     deferred.resolve();
            // }, function(error) {
            //     deferred.reject('Unable to add capability, error : ' + error);
            // });
            return deferred.promise;
        },

        /*save a device (server side)*/
        saveDevice : function(device){
            var deferred = $q.defer();
            $http({
                url: URL_DEVICE+'/'+device.id+'/',
                method: "PUT",
                data: device,
                headers: {'Content-Type': 'application/json'}
            }).success(function (data, status, headers, config) {
                deferred.resolve();
            }).error(function (data, status, headers, config) {
                //deferred.reject('Unable to save device, status : '+status+', header : '+headers);
                deferred.resolve();
            });
           
            // $http.put(URL_DEVICE+'/'+device.id+'/', {
            //     data: device
            // }).then(function(response) {
            //     deferred.resolve();
            // }, function(error) {
            //     deferred.reject('Unable to save device, error : ' + error);
            // });
            return deferred.promise;
        },

        /*modifying capability*/
        modifyCapability : function(idDevice, capability){
            var deferred = $q.defer();
            $http({
                url: URL_DEVICE+'/'+idDevice+'/capability/'+capability.id+"/",
                method: "PUT",
                data: capability,
                headers: {'Content-Type': 'application/json'}
            }).success(function (data, status, headers, config) {
                deferred.resolve();
                alert('success');
            }).error(function (data, status, headers, config) {
                deferred.reject('Unable to modify capability, status : '+status+', header : '+headers);
            });
            
            
            // $http.put(URL_DEVICE + '/' + idDevice + '/capability/' + capability.id + '/', {
            //     data: capability
            // }).then(function(response) {
            //     deferred.resolve();
            // }, function(error) {
            //     deferred.reject('Unable to modify capability, error : ' + error);
            // });
            return deferred.promise;
        },

        /*deleting a capability*/
        removeCapability : function(idDevice, idCapability){
            var deferred = $q.defer();
            $http({
                url: URL_DEVICE+'/'+idDevice+'/capability/'+idCapability+"/",
                method: "DELETE",
                data: "",
                headers: {'Content-Type': 'application/json'}
            }).success(function (data, status, headers, config) {
                deferred.resolve();
            }).error(function (data, status, headers, config) {
                deferred.reject('Unable to delete capability, status : '+status+', header : '+headers);
            });
            
            // $http.delete(URL_DEVICE + '/' + idDevice + '/capability/' + idCapability + '/').
            // success(function(data, status, headers, config) {
            //     deferred.resolve();
            // }).
            // error(function(data, status, headers, config) {
                
            //     deferred.reject('Unable to delete capability, status : '+status+', header : '+headers);
            // });
            
            // $http.delete(URL_DEVICE + '/' + idDevice + '/capability/' + idCapability + '/').
            // then(function(response) {
            //     deferred.resolve();
            // }, function(error) {
            //     deferred.reject('Unable to delete capability, error : ' + error);
            // });
            return deferred.promise;
        }

    };
    return factory;

}]);
