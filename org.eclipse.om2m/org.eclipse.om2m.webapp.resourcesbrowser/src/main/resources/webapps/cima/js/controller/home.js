/* Controller page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory,$interval,$timeout){
	
	$rootScope.loading = true;
	$scope.devices = new Array();
	$scope.predicate = '-configuration';
	$scope.useConfig = {};
    $scope.useMode = {};
    $scope.useKeys = {}; 

	/* Calling the DeviceFactory for changing the devices list */
	$scope.loadDevices = function(){
		var count = $scope.devices.length;
		DeviceFactory.find().then(function(devices){
			
			if(devices.length != count){
				$scope.devices = devices;
				$rootScope.loading = false;
			}
			

		}, function(msg){
			$rootScope.requestInfo = msg;
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

  $scope.$watch(function () {
        return {
            devices: $scope.devices,
            useConfig: $scope.useConfig,
            useMode: $scope.useMode,
            useKeys: $scope.useKeys
        }
    }, function (value) {
        var selected;
        
        $scope.configGroup = uniqueItems($scope.devices, 'configuration');
        var filterAfterConfig = [];        
        selected = false;
        for (var j in $scope.devices) {
            var p = $scope.devices[j];
            for (var i in $scope.useConfig) {
                if ($scope.useConfig[i]) {
                    selected = true;
                    if (i == p.configuration) {
                        filterAfterConfig.push(p);
                        break;
                    }
                }
            }        
        }
        if (!selected) {
            filterAfterConfig = $scope.devices;
        }

        $scope.modeGroup = uniqueItems(filterAfterConfig, 'modeConnection');
        var filterAfterModes = [];        
        selected = false;
        for (var j in filterAfterConfig) {
            var p = filterAfterConfig[j];
            for (var i in $scope.useMode) {
                if ($scope.useMode[i]) {
                    selected = true;
                    if (i == p.modeConnection) {
                        filterAfterModes.push(p);
                        break;
                    }
                }
            }       
        }
        if (!selected) {
            filterAfterModes = filterAfterConfig;
        } 

        $scope.keyGroup = uniqueItems(filterAfterModes, 'capabilities');
        var filterAfterKeys = []; 
        var temp = [];       
        selected = false;
        for (var j in filterAfterModes) {
            var p = filterAfterModes[j];
            for (var i in $scope.useKeys) {
                if ($scope.useKeys[i]) {
                    selected = true;  
                   	var caps = p.capabilities;
                   	for (var l = 0; l < caps.length; l++) { 
                    for(var s in caps[l]['keywords']){
                    		value = caps[l]['keywords'];
                    		//alert(value);
                    		//value = caps[l]['keywords']
                    if (i == value[s]) {  
                        filterAfterKeys.push(p);
                        break;
                    } 
                }
                }
                }
            }    
        }
        if (!selected) {
            filterAfterKeys = filterAfterModes;
        }        

        $scope.filteredDevices= filterAfterKeys;        
    }, true);
    
    
    $scope.$watch('filtered', function (newValue) {
        if (angular.isArray(newValue)) {
            console.log(newValue.length);
        }
    }, true);    
});
/*function List_search(what, where){
 var exists = false; 

	for( var i = 0; i < where.length; i++){ 
		//if (where[elt]==what){
	if (where.indexOf(what, i) != -1){

			exists =  true;
	}
		//}
	} 
	return exists;
};*/
function include(arr,obj) {
	var time = 0;

	if(arr.indexOf(obj) != -1){
       time ++;
	}
    return time;
};
var uniqueItems = function (data, key) {
    var result = new Array();
    for (var i = 0; i < data.length; i++) {
        var value = data[i][key];
 
        if (result.indexOf(value) == -1) {
            result.push(value);
        }
    
    }
    return result;
};  
app.filter('unique', function() {
    return function(input, key) {
        var unique = {};
        var uniqueList = [];
        var tempList =[];
        for(var i = 0; i < input.length; i++){
            //alert(input[i]); 
          // if(typeof unique[input[i]] == "undefined"){
          	//if(input[i] =='wiki'){
               // alert(unique[input[i]]); 
              ///  unique[input[i]] = ""; 
            
            tempList.push(input[i]); 
           // alert(input[i]); 
            
         
        //}
        //alert(tempList); 
       // for (var i = 0; i < input.length; i++){
        	 //  if (!List_search(input[i],tempList)){
          	if(include(tempList, input[i])==1){

            	//alert(input[i]); 
            	//unique[input[i][key]] = "";
                uniqueList.push(input[i]);
            }
        }
        return uniqueList;
    };
});
/*app.filter('unique', function() {
    return function(input, key) {
        var unique = {};
        var uniqueList = [];
        for(var i = 0; i < input.length; i++){
            if(typeof unique[input[i][key]] == "undefined"){
                unique[input[i][key]] = "";
                uniqueList.push(input[i]);
            }
        }
        return uniqueList;
    };
});*/
