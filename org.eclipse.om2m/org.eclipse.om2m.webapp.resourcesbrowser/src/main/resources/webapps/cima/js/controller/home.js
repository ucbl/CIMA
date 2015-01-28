/* Controller page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory,$interval){
	
	$rootScope.loading = true;

	/* Calling the DeviceFactory for changing the devices list */
	$scope.loadDevices = function(){
		$scope.devices = DeviceFactory.find().then(function(devices){
			$scope.devices = devices;
			$rootScope.loading = false;
		}, function(msg){
			alert(msg);
		})
	};

	/* Stop refresh the device list */
	$scope.stopRefreshDevices = function(){
		clearInterval(interval);
	}; 

	/* Call once to first load the list */
	$scope.loadDevices();

	/* Set an intervall to refresh and call the function to load device list */
	var interval = setInterval( function(){ $scope.loadDevices(); }, DEVICE_REFRESH);

});