/* Controller page home.html */
app.controller('HomeCtrl', function($scope, $rootScope,$location ,DeviceFactory,$interval,$timeout, ngToast){
	
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
                ngToast.create((devices.length-count)+" new devices detected.");
			}


		}, function(msg){
			$rootScope.requestInfo = msg;
            ngToast.create({
                content: "Unable to get devices : "+msg,
                className: "danger"
            });
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
  /*return the filtered lists*/
  $scope.$watch(function () {
        return {
            devices: $scope.devices,
            useConfig: $scope.useConfig,
            useMode: $scope.useMode,
            useKeys: $scope.useKeys
        }
    }, function (value) {
        var selected;
        /*configGroup contains the list of unique configuration items {'manual','automatic'}*/
        $scope.configGroup = uniqueItems($scope.devices, 'configuration');
        /*filter by configuration mode*/
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
        /*modeGroup contains the list of unique connection mode items {'USB','HTTP', ...}*/
        $scope.modeGroup = uniqueItems(filterAfterConfig, 'modeConnection');
         /*filter by connection mode*/
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
        /*keyGroup contains the list of unique keywords {'ev3','movement', ...}*/
        $scope.keyGroup = uniqueItemsKeys(filterAfterModes, 'capabilities');
        /*flter by keywords*/
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
        /*return the filtered devices depending on the previous filters*/
        $scope.filteredDevices= unique(filterAfterKeys);      
    }, true);
     
}); 
    /*function returns true if an object is in the list and false otherwise*/
    function include(arr,obj) { 
    	if(arr.indexOf(obj) != -1){ 
           return true;
    	}
        return false;
    };
    /*return unique items from a data object*/
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
    /*return unique keywords*/
    var uniqueItemsKeys = function (data, key) {

        var result = new Array();
        var tempList =[]; 
        for (var j in data) {
                var caps = data[j][key];   
            for (var j = 0; j < caps.length; j++) {
               keywords = caps[j].keywords;  
                
                for(var s in keywords){  
                    value = keywords[s];
                    tempList.push(value);        
                    }
        
            }
        } 
    var unique=tempList.filter(function(itm,i,a){
        return i==tempList.indexOf(itm);
    });
        result = unique;
        return result;
    }; 
/*Function returns unique elements of a list*/
var unique = function(origArr) {
    var newArr = [],
        origLen = origArr.length,
        found, x, y;

    for (x = 0; x < origLen; x++) {
        found = undefined;
        for (y = 0; y < newArr.length; y++) {
            if (origArr[x] === newArr[y]) {
                found = true;
                break;
            }
        }
        if (!found) {
            newArr.push(origArr[x]);
        }
    }
    return newArr;
}