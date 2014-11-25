/* Controlleur page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory){
	$rootScope.loading = true;
	/* Appel au DeviceFactory pour charger la liste des devices */
	$scope.devices = DeviceFactory.find().then(function(devices){
		$scope.devices = devices;
		$rootScope.loading = false;
	}, function(msg){
		alert(msg);
	});
});