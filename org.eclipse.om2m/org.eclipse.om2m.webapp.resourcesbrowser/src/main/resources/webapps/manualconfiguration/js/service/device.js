/* Modèle */
app.factory('DeviceFactory', function($http, $q, $timeout){
	
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
				$http.get('/om2m/nscl/applications/configuration/manualconfiguration/device')
				.success(function(data, status){
					factory.devices = data;
					 $timeout(function(){
						deferred.resolve(factory.devices);
					}, 1000);
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
		testCapacity : function(idDevice, capacity){
			var deferred = $q.defer();
			alert('POST /manualconfiguration/device/'+ idDevice+'/test'+ capacity);
			//...
			deferred.resolve();
			return deferred.promise;
		},

		//ajout d' une capacité
		addCapacity : function(idDevice, capacity){
			var deferred = $q.defer();
			alert('PUT /manualconfiguration/device/'+idDevice+'/capability/'+capacity.id+"/"+ capacity);
			//...
			deferred.resolve();
			return deferred.promise;
		},


		//Ajoute/modifie un device (coté serveur)
		saveDevice : function(device){
			var deferred = $q.defer();
			alert('PUT /manualconfiguration/device/'+device.id+'/'+ device);
			//...
			deferred.resolve();
			return deferred.promise;
		}
	};
return factory;

})