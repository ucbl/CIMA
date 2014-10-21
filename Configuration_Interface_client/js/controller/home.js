/* Controlleur home (devices) */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory){
	/*Promesses*/
	$rootScope.loading = true;
	$scope.devices = DeviceFactory.find().then(function(devices){
		$rootScope.loading = false;
		$scope.devices = devices;
	}, function(msg){
		alert(msg);
	});
    $scope.go = function ( path ) {
        $location.path( path );
    }
});