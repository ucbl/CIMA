/* Modèle */
app.factory('DeviceFactory', function($http, $q, $timeout){
//SRC PRINCIAPLE http://134.214.213.63:8080/om2m/nscl/applications/configuration/manualconfiguration

	var factory = {
		devices : false,
		/* Retourne tous les devices */
		find : function(options){
			/* Promesses */
			var deferred = $q.defer();
			//Eviter de recharger
			if( factory.devices !== false){
				deferred.resolve(factory.devices);
			}else{
				//om2m/nscl/applications/configuration/manualconfiguration/device
				$http.get('/om2m/nscl/applications/configuration/manualconfiguration/device')
				.success(function(data, status){
					factory.devices = data;
					deferred.resolve(factory.devices);
				}).error(function(data, status){
					deferred.reject('Unable to get devices')
				})
			}
			
			return deferred.promise;
		},
		//Retourne un device avec son id
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

		//test une capacité
		//URL http://134.214.213.63:8080/om2m/nscl/applications/manualconfiguration/device/'+ idDevice+'/test'+ capacity	
		testCapacity : function(idDevice, capacity){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/manualconfiguration/device/'+ idDevice+'/test',
        		method: "POST",
        		data: capacity,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
					deferred.resolve();
        	}).error(function (data, status, headers, config) {
					deferred.reject('Unable to test capacity, status : '+status+', header : '+headers);
        	});
	  	
			return deferred.promise;
		},

		//ajout d' une capacité
		addCapacity : function(idDevice, capacity){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/manualconfiguration/device/'+idDevice+'/capability/'+capacity.id+"/",
        		method: "PUT",
        		data: capacity,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to add capacity, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},


		//Ajoute/modifie un device (coté serveur)
		saveDevice : function(device){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/manualconfiguration/device/'+device.id+'/',
        		method: "PUT",
        		data: device,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to add capacity, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		//modifie une capacité
		modifyCapability : function(idDevice, capacity){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/manualconfiguration/device/'+idDevice+'/capability/'+capacity.id+"/",
        		method: "PUT",
        		data: capacity,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to add capacity, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		removeCapacity : function(idDevice, idCapacity){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/manualconfiguration/device/'+idDevice+'/capability/'+idCapacity+"/",
        		method: "DELETE",
        		data: "",
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to add capacity, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		}

	};
return factory;

})