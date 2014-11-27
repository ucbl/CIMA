/* Protocols model */
app.factory('ProtocolsFactory', function($http, $q, $timeout){

	var factory = {
		protocols : false,
		/* Retourne tous les protocoles */
		/*Return all protocols*/
		find : function(options){
			/* Promesses */
			var deferred = $q.defer();
            /*Avoid loading */
 			if( factory.protocols !== false){
				deferred.resolve(factory.protocols);
			}else{

				$http.get(URL_PROTOCOLS)
				.success(function(data, status){
					factory.protocols = data;
					deferred.resolve(factory.protocols);
				}).error(function(data, status){
					deferred.reject('Unable to get protocols')
				})
			}
			
			return deferred.promise;
		},
        /*get a protocol from its id*/
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