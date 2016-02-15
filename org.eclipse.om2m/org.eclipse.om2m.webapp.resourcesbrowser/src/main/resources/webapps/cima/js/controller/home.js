'use strict';

var HomeController = angular.module('HomeController', ['DeviceController', 'CIMA.toast']);
HomeController.controller('HomeController', ['$scope', '$rootScope', 'DeviceFactory', '$interval', 'ngToast', '$timeout', function($scope, $rootScope, DeviceFactory, $interval,  ngToast, $timeout){

    $rootScope.loading = false;
    $scope.devices = [];
    $scope.devicesTemp = [];
    $scope.predicate = '-configuration';
    $scope.useConfig = {};
    $scope.useMode = {};
    $scope.useKeys = {};  
    $scope.isSelectAll = true;
    $scope.filters = {};
    $scope.toggle = true;
    var interval = '';

    $scope.countLoadDevices = 0;
    var count = $scope.devices.length;

    var noSubFilter = function(subFilterObj) {
        for (var key in subFilterObj) {
            if (subFilterObj[key]) return false;
        }
        return true;
    }

    // $scope.useConfig = {}; $scope.useMode = {}; $scope.useKeys = {};  
    var initializeOptions = function() {
        /*configGroup contains the list of unique configuration items ['manual','automatic']*/
        $scope.configGroup = uniqueItems($scope.devices, 'configuration');
        /*modeGroup contains the list of unique connection mode items ['USB','HTTP', ...]*/
        $scope.modeGroup = uniqueItems($scope.devices, 'modeConnection');
        /*keyGroup contains the list of unique keywords ['ev3','movement', ...]*/
        $scope.keyGroup = uniqueItemsKeys($scope.devices, 'capabilities');
    };

    $scope.loadDevices = function() {
        var count = $scope.devices.length;
        $scope.countLoadDevices++;
        DeviceFactory.find().then(function(devices){
            if(devices.length != count){
                $scope.devices = devices;
                $scope.devicesTemp = devices;
                initializeOptions();
                $scope.isSelectAll = true;
                for (var key1 in $scope.filters) {
                    for (var key2 in $scope.filters[key1]) {
                        $scope.filters[key1][key2] = $scope.isSelectAll;
                    }
                }
                ngToast.create((devices.length-count)+" new devices detected.");
                console.log("In if length - count: " + devices.length + "-" + count);
            } else {
                console.log("In else length - count: " + devices.length + "-" + count);
            }


        }, function(msg){
            $rootScope.requestInfo = msg;
            ngToast.create({
                content: "Unable to get devices : " + msg,
                className: "danger"
            });
        });
    };
    $scope.loadDevices();

    $scope.notify = function() {
        var isSelectAll = true;
        var isOneSelected = false;
        for (var key1 in $scope.filters) {
            for (var key2 in $scope.filters[key1]) {
                if(!$scope.filters[key1][key2]) {
                    isSelectAll = false;
                } else {
                    isOneSelected = true;
                }
            }
        }
        $scope.isSelectAll = isSelectAll;
        if (isOneSelected)
            $scope.devices = $scope.devicesTemp;
        else $scope.devices = [];

    };

    // Switch the filters : AND or OR if necessary
    $scope.filterByDevice = function(device) {
        // Filter with the OR condition
        /*var matchesOR = true;
        var reached = false;
        
        for (var prop in $scope.filters) {
            if (noSubFilter($scope.filters[prop])) continue;
            prop = (prop == 'keywords' ? 'capabilities' : prop);
            if (device[prop] instanceof Array) { // This means prop = 'capabilites' which is an array
                for (var j = 0; j < device[prop].length; j++) {
                    for (var i = 0; i < device[prop][j]['keywords'].length; i++) {
                        
                        if ($scope.filters['keywords'].hasOwnProperty(device[prop][j]['keywords'][i])) {
                            
                            if (!$scope.filters['keywords'][device[prop][j]['keywords'][i]]) {
                                matchesOR = false;
                            } else {
                                console.log('reculer here');
                                matchesOR = true;
                                reached = true;
                                break;
                            }
                        }
                    }
                }
                
                if (reached) break;
            } else {
                if (!$scope.filters[prop][device[prop]]) {
                    matchesOR = false;
                } else {
                    matchesOR = true;
                    break;
                }
            }
        }
        
        return matchesOR;*/
       
        // Filter with the AND condition
        var matchesAND = true;
        var reached = false;
        // Example of filters of configuration and connection mode : 
        // $scope.filters['configuration']['automatic'] = true , $scope.filters['modeConnection']['http'] = true
        // So each iteration : prop = configuration or prop = modeConnection
        for (var prop in $scope.filters) {

            // For actual server of CIMA, the client hasn't received "keywords" yet
            if (noSubFilter($scope.filters[prop])) continue;
            prop = (prop == 'keywords' ? 'capabilities' : prop);
            if (device[prop] instanceof Array) { // This means prop = 'capabilites' which is an array
                for (var j = 0; j < device[prop].length; j++) {
                    for (var i = 0; i < device[prop][j]['keywords'].length; i++) {
                        
                        if ($scope.filters['keywords'].hasOwnProperty(device[prop][j]['keywords'][i])) {
                            
                            if (!$scope.filters['keywords'][device[prop][j]['keywords'][i]]) {
                                matchesAND = false;
                                reached = true;
                                break;
                            } else {
                                matchesAND = true;
                            }
                        }
                    }
                }
                
                if (reached) break;
            } else {
                var value = device[prop];
               
                if (!$scope.filters[prop][value]) {
                    matchesAND = false;
                    break;
                }
            }
        }

        return matchesAND;
     };

    $scope.selectAll = function() {
        for (var key1 in $scope.filters) {
            for (var key2 in $scope.filters[key1]) {
                var value = $scope.filters[key1][key2];
                $scope.filters[key1][key2] = $scope.isSelectAll;
            }
        }
        
        if (!$scope.isSelectAll) {
            $scope.devicesTemp = $scope.devices;
            $scope.devices = [];
        } else $scope.devices = $scope.devicesTemp;
    };

    interval = $interval($scope.loadDevices, DEVICE_REFRESH);
   
    
    $scope.stopRefreshDevices = function(){
        if (angular.isDefined(interval)) {
            console.log('This is stopRefreshDevices');
            $interval.cancel(interval);
            interval = undefined;
        }
    };

    $scope.$on('$destroy', function() {
        $scope.stopRefreshDevices();
    });

}]); 
/*function returns true if an object is in the list and false otherwise*/
function include(arr,obj) { 
    if(arr.indexOf(obj) != -1){ 
       return true;
    }
    return false;
}

/*return unique items from a data object*/
var uniqueItems = function(data, key) {
    var result = [];
    for (var i = 0; i < data.length; i++) {
        var value = data[i][key];
 
        if (result.indexOf(value) == -1) {
            result.push(value);
        }
    
    }
    return result;
};  
/*return unique keywords*/
var uniqueItemsKeys = function(data, key){

    var result = [];
    var tempList =[]; 
    for (var j in data) {
            var caps = data[j][key];   
        for (var j = 0; j < caps.length; j++) {
            var keywords = caps[j].keywords;  
            
            for (var s in keywords) {   
                var value = keywords[s];
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
};