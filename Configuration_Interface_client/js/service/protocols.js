/* Modèle pour protocoles */
app.factory('ProtocolsFactory', function($http, $q, $timeout){
	
	var factory = {
		protocols : false,
		/* Retourne tous les protocoles */
		find : function(options){
			/* Promesses */
			var deferred = $q.defer();
			//Eviter de recharger
			if( factory.protocols !== false){
				deferred.resolve(factory.protocols);
			}else{
				$http.get('json/protocols.json')
				.success(function(data, status){
					factory.protocols = data;
					 $timeout(function(){
						deferred.resolve(factory.protocols);
					}, 1000);
				}).error(function(data, status){
					deferred.reject('Unable to get protocols')
				})
			}
			
			return deferred.promise;
		},
		//Retourne un protocol avec son id
		get : function(id){
			/* Promesses */
			var deferred = $q.defer();
			var protocol = {};
			var protocol = factory.find().then(function(protocols){
				angular.forEach(protocols, function(value,key){
					if(value.id == id){
						protocol = value;
					}
				});
				deferred.resolve(protocol);
			}, function(msg){
				deferred.reject(msg);
			});
			
			return deferred.promise;
		},
	};
return factory;
})