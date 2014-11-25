
/* Controlleur page device.html */
app.controller('DeviceCtrl', function ($http, $scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams) {

	/*Promesses*/
	$rootScope.loading = true;
	$scope.newComment ={};
	$rootScope.EditIsOpen = false;

	/* Recupere les infos sur le device selectionné et les ajoute à la vue */
	DeviceFactory.get($routeParams.id).then(function(device){
		$scope.id = device.id;
		$scope.name = device.name;
		$scope.lastConnectionDate = device.dateConnection;
		$scope.connectionMode = device.modeConnection;
		$scope.uri = device.uri;

		if(device.capabilities){
			$scope.capabilities = device.capabilities;
		}else{
			$scope.capabilities = [];
		}
		$rootScope.loading = false; 

	}, function(msg){
		alert(msg);
	})

	/* Recupere les protocoles disponibles et les ajoute à la vue */
	ProtocolsFactory.find().then(function(protocols){
		$scope.protocols = protocols;
	}, function(msg){
		alert(msg);
	})
	
	//Ajoute une capability au modèle et à la vue si c'est un succès
	$scope.addCapability = function(newCapability){
		if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null){			

			var cap = {};
			cap.id = newCapability.id;
			cap.protocol = {};
			cap.protocol.protocolName = newCapability.protocol.protocolName;
			cap.protocol.parameters = newCapability.protocol.parameters;

			DeviceFactory.addCapability($scope.id,cap).then(function(){
				$scope.capabilities.push(cap);
			}, function(){
				alert('Votre capability n\'a pas pu être ajoutée');
			});
			$scope.newCapability = {};
			$scope.showme = false;
		}
	}
        
	//Supprimer un capability du podèle et de la vue si succès
    $scope.removeCapability = function (row) {
        
        DeviceFactory.removeCapability($scope.id, row.id).then(function(){
        	var index = $scope.capabilities.indexOf(row);
        	if (index !== -1) {
            	$scope.capabilities.splice(index, 1);
        	}
        	$rootScope.EditIsOpen= false;
		}, function(){
			alert('Votre capability n\'a pas pu être supprimé');
		});

        
    }

	//Fonction pour tester une capacité
	$scope.testCapability = function(newCapability){
		var cap = {};
		cap.id = newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = newCapability.protocol.protocolName;
		cap.protocol.parameters = newCapability.protocol.parameters;
		
		DeviceFactory.testCapability($scope.id, cap).then(function(){

		}, function(){
			alert('Votre capability n\'a pas pu être testé');
		});
	}

	//Fonction pour sauvegarder le device
	$scope.saveDevice = function(){
		var device = {};

		device.id = $scope.id;
		device.name = $scope.name;
		device.lastConnectionDate = $scope.lastConnectionDate;
		device.connectionMode = $scope.connectionMode;
		device.uri = $scope.uri;
		device.capabilities = {};
		device.capabilities = $scope.capabilities;

		DeviceFactory.saveDevice(device).then(function(){

		}, function(){
			alert('Votre device n\'a pas pu être sauvegardé');
		});
	}
	//Fonction pour modifier le device

	$scope.modifyCapability = function(capability){
		var cap = {};
		cap.id = capability.id;
		cap.protocol = {};
		cap.protocol.protocolName = capability.protocol.protocolName;
		cap.protocol.parameters = capability.protocol.parameters;
		DeviceFactory.modifyCapability($scope.id, cap).then(function(){

		}, function(){
			alert('Votre capability n\'a pas pu être modifié');
		});
	}

	//Fonction pour ajouter la capacité a modifier dans le scope et afficher la section d'edition
	$scope.openAndEditCapability = function(capability){
		$rootScope.EditIsOpen = true;
		$scope.editedCapability = JSON.parse(JSON.stringify(capability));
	}

	//Fonction pour fermer la div d'edition
	$scope.CloseEditCapability = function(){
		if($rootScope.EditIsOpen){
			$rootScope.EditIsOpen = false;
		}
	}

	//Bloc pour recherche capabilities auto indentation
	$scope.selected = undefined;
  	$scope.getCapability = function(val) {
	  	return $http.get('json/capabilities.json', {
	    	params: {
	      		filter: val
	    	}
	  	}).then(function(response){
	      	return response.data;
	    });
  	};

  	//Fonction qui intercepte la selection d'une capacité existante
  	$scope.onSelectCapability = function ($item, $model, $label) {
	    //alert($item.protocol.protocolName);
	    $scope.NewFromExistingCapability = $item;
	    for (i = $scope.protocols.length - 1; i >= 0; i--) {
		dataset = $scope.protocols[i];
			if (dataset.protocolName == $item.protocol.protocolName) {
				alert("Protocole dans select trouvé !");
				$scope.protocols[i].parameters = $item.protocol.parameters;
			    $scope.NewFromExistingCapability.protocol = $scope.protocols[i];
			    break;
			}
		}
	};
});