/* Controlleur device */
app.controller('DeviceCtrl', function ($scope, $rootScope, DeviceFactory, $routeParams) {
	/*Promesses*/
	$rootScope.loading = true;
	$scope.newComment ={};

	DeviceFactory.get($routeParams.id).then(function(device){
		$rootScope.loading = false;
		$scope.id = device.id;
		$scope.name = device.name;
		$scope.date = device.last_connection_date;
		$scope.capabilities = device.capabilities;

	}, function(msg){
		alert(msg);
	})
	$scope.addCapability = function(){
		$scope.capabilities.push($scope.newCapability);
		Device.add($scope.newCapability).then(function(){

		}, function(){
			alert('Votre capability n\'a pas pu être sauvegardé');
		});
		$scope.newCapability = {};
	}


});