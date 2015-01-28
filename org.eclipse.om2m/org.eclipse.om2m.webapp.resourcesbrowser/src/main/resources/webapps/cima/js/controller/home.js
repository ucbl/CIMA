/* Controller page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory,$interval,$timeout, toaster){
	
	$rootScope.loading = true;
	$scope.devices = new Array();
	$scope.predicate = '-configuration';

	/* Calling the DeviceFactory for changing the devices list */
	$scope.loadDevices = function(){
		var count = $scope.devices.length;
		DeviceFactory.find().then(function(devices){
			
			if(devices.length != count){
				toaster.pop('success', "", (devices.length-count)+" new devices detected.");
				$scope.devices = devices;
				$rootScope.loading = false;
			}
			

		}, function(msg){
			$rootScope.requestInfo = msg;
			toaster.pop('error', "Unable to get devices", msg);
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