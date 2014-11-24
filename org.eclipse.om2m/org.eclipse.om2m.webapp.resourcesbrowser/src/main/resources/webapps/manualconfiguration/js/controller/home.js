/* Controlleur page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory){
	/*Promesses*/
	$rootScope.loading = true;
	/* Appel au DeviceFactory pour charger la liste des devices */
	$scope.devices = DeviceFactory.find().then(function(devices){
		$rootScope.loading = false;
		$scope.devices = devices;
	}, function(msg){
		alert(msg);
	});
});