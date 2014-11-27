
/*Device Model
* Defining all methods to request the server*/

app.factory('DeviceFactory',function($http, $q, $timeout, $log){

	var factory = { 
		/*Return all devices*/
		find : function(options){
			/* Promesses */
			var deferred = $q.defer();

				/*$http.get('/om2m/nscl/applications/configuration/manualconfiguration/device')
				.success(function(data, status){
					factory.devices = data;
					deferred.resolve(factory.devices);
				}).error(function(data, status){
					deferred.reject('Unable to get devices')
				})*/
            /*Asynchronous request for getting all devices*/
 			setInterval(function(){
 				$.ajax({
 				   //$http.get('/om2m/nscl/applications/configuration/manualconfiguration/device')
			       url : 'json/devices.json',
			       type : 'GET',
			       dataType : 'json',
			       success : function(data, status){
			       		factory.devices = data;
						deferred.resolve(factory.devices);
			       },
			       error : function(result, status, error){
			       		console.log("error ajax req ");
			       		deferred.reject('Unable to get devices')

			       },
			    });
 			}, 1000); 
       

			return deferred.promise;
		
	}, 
        /*get a device from its id*/
 		get : function(id){
			/* Promesses */
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
		//URL http://134.214.213.63:8080/om2m/nscl/applications/manualconfiguration/device/'+ idDevice+'/test'+ capability	
		testCapability : function(idDevice, capability){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+ idDevice+'/test',
        		method: "POST",
        		data: capability,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
					deferred.resolve();
        	}).error(function (data, status, headers, config) {
					deferred.reject('Unable to test capability, status : '+status+', header : '+headers);
        	});
	  	
			return deferred.promise;
		},

		/*add capability*/
		addCapability : function(idDevice, capability){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+idDevice+'/capability/'+capability.id+"/",
        		method: "PUT",
        		data: capability,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to add capability, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		/*save a device (server side)*/
		saveDevice : function(device){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+device.id+'/',
        		method: "PUT",
        		data: device,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to save device, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		/*modifying capability*/
		modifyCapability : function(idDevice, capability){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+idDevice+'/capability/'+capability.id+"/",
        		method: "PUT",
        		data: capability,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				alert('success');
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to modify capability, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		/*deleting a capability*/
		removeCapability : function(idDevice, idCapability){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+idDevice+'/capability/'+idCapability+"/",
        		method: "DELETE",
        		data: "",
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to delete capability, status : '+status+', header : '+headers);
        	});
			return deferred.promise;
		}

	};
return factory;

})
