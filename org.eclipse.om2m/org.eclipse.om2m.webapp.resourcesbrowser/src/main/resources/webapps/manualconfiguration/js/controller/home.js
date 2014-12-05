/* Controller page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory){
	$rootScope.loading = true;
	/*Calling the DeviceFactory for changing the devices list*/
	$scope.devices = DeviceFactory.find().then(function(devices){
		$scope.devices = devices;
		$rootScope.loading = false;
	}, function(msg){
		alert(msg);
	});
});