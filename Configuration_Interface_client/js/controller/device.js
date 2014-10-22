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

	//Fonction pour valider le formulaire et cacher ou on
	$scope.check = function(){
		if($scope.newCapability.id !=null && $scope.newCapability.protocol.protocolName != null && $scope.newCapability.protocol.parameters!= null){
			$scope.showme = false;
		}
	}
	
	//Fonction pour ajouter une capacité à l'objet
	$scope.addCapacity = function(){
		var cap = {};
		cap.id = $scope.newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = $scope.newCapability.protocol.protocolName;
		cap.protocol.parameters = $scope.newCapability.protocol.parameters;

		$scope.capabilities.push(cap);

		DeviceFactory.addCapacity($scope.id,cap).then(function(){


		}, function(){
			alert('Votre capacity n\'a pas pu être sauvegardé');
		});
		
		$scope.newCapability = {};
	}

	//Fonction pour tester une capacité
	$scope.testCapacity = function(){
		var cap = {};
		cap.id = $scope.newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = $scope.newCapability.protocol.protocolName;
		cap.protocol.parameters = $scope.newCapability.protocol.parameters;
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


});