/* Controlleur device */
app.controller('DeviceCtrl', function ($scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams) {
	/*Promesses*/
	$rootScope.loading = true;
	$scope.newComment ={};

	DeviceFactory.get($routeParams.id).then(function(device){
		$rootScope.loading = false;
		$scope.id = device.id;
		$scope.name = device.name;
		$scope.lastConnectionDate = device.lastConnectionDate;
		$scope.connectionMode = device.connectionMode;
		$scope.uri = device.uri;

		$scope.capabilities = device.capabilities;

	}, function(msg){
		alert(msg);
	})

	ProtocolsFactory.find().then(function(protocols){
		$scope.protocols = protocols;
	}, function(msg){
		alert(msg);
	})

	//Fonction pour valider le formulaire d'ajout de capacité et cacher ou on
	$scope.check = function(capacity){
		if(capacity.id !=null && capacity.protocol.protocolName != null && capacity.protocol.parameters!= null){
			$scope.showme = false;
		}
	}
	
	//Fonction pour ajouter une capacité à l'objet
	$scope.addCapacity = function(newCapability){
		var cap = {};
		cap.id = newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = newCapability.protocol.protocolName;
		cap.protocol.parameters = newCapability.protocol.parameters;

		$scope.capabilities.push(cap);

		DeviceFactory.addCapacity($scope.id,cap).then(function(){

		}, function(){
			alert('Votre capacity n\'a pas pu être sauvegardé');
		});
		$scope.newCapability = {};
	}
        
	//Supprimer un capacity
    $scope.removeCapability = function removeCapability(row) {
        
        DeviceFactory.removeCapacity($scope.id, row.id).then(function(){


		}, function(){
			alert('Votre capacity n\'a pas pu être testé');
		});

        var index = $scope.capabilities.indexOf(row);
        if (index !== -1) {
            $scope.capabilities.splice(index, 1);
        }
        $scope.editme= false;
    }

	//Fonction pour tester une capacité
	$scope.testCapacity = function(newCapability){
		var cap = {};
		cap.id = newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = newCapability.protocol.protocolName;
		cap.protocol.parameters = newCapability.protocol.parameters;
		DeviceFactory.testCapacity($scope.id, cap).then(function(){


		}, function(){
			alert('Votre capacity n\'a pas pu être testé');
		});
	}

	//Fonction pour sauvgarder le device
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

	//Fonction pour ajouter la capacité a modifier dans le scope et afficher la section d'edition
	$scope.openAndEditCapacity = function(capacity){
		$scope.editme= true;
		$scope.isOpen=true;
		$scope.capability = capacity;
	}

	//Fonction pour fermer la div d'edition
	$scope.CloseEditCapacity = function(){
		$scope.editme= false;
	}

	$scope.modifyCapability = function(capability){
		var cap = {};
		cap.id = capability.id;
		cap.protocol = {};
		cap.protocol.protocolName = capability.protocol.protocolName;
		cap.protocol.parameters = capability.protocol.parameters;
		DeviceFactory.modifyCapability($scope.id, cap).then(function(){

		}, function(){
			alert('Votre capacity n\'a pas pu être testé');
		});
	}

});