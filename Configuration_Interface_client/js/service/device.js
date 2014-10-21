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
				$http.get('json/devices.json')
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
		//Ajoute/modifie un device (coté serveur)
		add : function(device){
			var deferred = $q.defer();
			//...
			deferred.resolve();
			return deferred.promise;
		}
	};
return factory;

})