/* Controlleur device */
app.controller('DeviceCtrl', function ($scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams) {
	/*Promesses*/
	$rootScope.loading = true;
	$scope.newComment ={};

	DeviceFactory.get($routeParams.id).then(function(device){
		$rootScope.loading = false;
		$scope.id = device.id;
		$scope.name = device.name;
		$scope.date = device.lastConnectionDate;
		$scope.capabilities = device.capabilities;

	}, function(msg){
		alert(msg);
	})

	ProtocolsFactory.find().then(function(protocols){
		$scope.protocols = protocols;
	}, function(msg){
		alert(msg);
	})
	
	$scope.addCapability = function(){
		var cap = {};
		cap.id = $scope.newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = $scope.newCapability.protocol.protocolName;
		cap.protocol.parameters = $scope.newCapability.protocol.parameters;

		$scope.capabilities.push(cap);
		//$scope.capabilities.push($scope.newCapability);
		//DeviceFactory.add($scope.newCapability).then(function(){
		DeviceFactory.add(cap).then(function(){


		}, function(){
			alert('Votre capability n\'a pas pu être sauvegardé');
		});
		$scope.newCapability = {};
	}


});