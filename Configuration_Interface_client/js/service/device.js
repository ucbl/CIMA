	
/* Modèle Device 
** Défini toutes les méthodes pour requêter le serveur */
app.factory('DeviceFactory',function($http, $q, $timeout, $log){

	var factory = {
		devices : false,
		/* Retourne tous les devices */ 
	req: function(){


			$.ajax({
			       url : 'json/devices.json',
			       type : 'GET',
			       dataType : 'json',
			       success : function(data, status){
			       		factory.devices = data;
			       },
			       error : function(result, status, error){
			       		console.log("error ajax req ");
			       },
			    });

	},
		find : function(options){
			/* Promesses */
			var deferred = $q.defer();
			//Eviter de recharger
			if( factory.devices !== false){
				deferred.resolve(factory.devices);
			}else{ 
				/*$http.get('/om2m/nscl/applications/configuration/manualconfiguration/device')
				.success(function(data, status){
					factory.devices = data;
					deferred.resolve(factory.devices);
				}).error(function(data, status){
					deferred.reject('Unable to get devices')
				})*/

				/*$http.get('json/devices.json')
				.success(function(data, status){
					factory.devices = data;
					 $timeout(function(){
						deferred.resolve(factory.devices);
					}, 1000);
				})
				.error(function(data, status){
					deferred.reject('Unable to get devices')
				})*/
 			setInterval(function(){
 				$.ajax({
			       url : 'json/devices.json',
			       type : 'GET',
			       dataType : 'json',
			       success : function(data, status){
			       		factory.devices = data;
			       		$timeout(function(){
						deferred.resolve(factory.devices);
					}, 1000);
			       },
			       error : function(result, status, error){
			       		console.log("error ajax req ");
			       		deferred.reject('Unable to get devices')

			       },
			    });
 			}, 1000); 
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

		//add capability
		addCapability : function(idDevice, capability){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+idDevice+'/capability/'+capability.id+"/",
        		method: "PUT",
        		data: capability,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to add capability, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},


		//save device (server side)
		saveDevice : function(device){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+device.id+'/',
        		method: "PUT",
        		data: device,
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to save device, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		//modify capability
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
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to modify capability, status : '+status+', header : '+headers);
        	});

			return deferred.promise;
		},

		//delete capability
		removeCapability : function(idDevice, idCapability){
			var deferred = $q.defer();
			$http({
        		url: '/om2m/nscl/applications/configuration/manualconfiguration/device/'+idDevice+'/capability/'+idCapability+"/",
        		method: "DELETE",
        		data: "",
        		headers: {'Content-Type': 'application/json'}
      		}).success(function (data, status, headers, config) {
				deferred.resolve();
				//On doit recharger les devices
	  			devices = false;
        	}).error(function (data, status, headers, config) {
				deferred.reject('Unable to delete capability, status : '+status+', header : '+headers);
        	});
			return deferred.promise;
		}

	};
return factory;

})
