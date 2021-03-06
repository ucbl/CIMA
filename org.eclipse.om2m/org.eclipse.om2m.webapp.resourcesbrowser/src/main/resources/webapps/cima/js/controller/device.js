'use strict';

var DeviceController = angular.module('DeviceController', ['CIMA.toast', 'ngStorage']);

DeviceController.controller('DeviceController', ['$scope', '$rootScope', 'DeviceFactory', 'ProtocolsFactory', '$routeParams', 'ngToast', 'ProfileService', '$localStorage', function($scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams, ngToast, ProfileService, $localStorage) {
    
    $rootScope.loading = true;
    //$scope.EditIsOpen = false;
    $scope.idrequired = false;
    $scope.ShowIsOpen = false;
    $scope.indexOfShowingCapability = -1;
    $scope.configParams = {};
    $scope.isResponseCapability = false;
    $scope.responsesCapability = [];
    $scope.isLoading = false;
    $scope.activeProfile = {};
    $scope.activeCapabilities = [];
    $scope.profilesMatching = [];
    $scope.selectedProfilesMatching = [];

    /*retrieve information about the device and add them to the view*/
    DeviceFactory.get($routeParams.id).then(function(device){
        ngToast.create("Device loaded.");


        $scope.id = device.id;
        $scope.name = device.name;
        //$scope.dateConnection = device.connection.dateConnection;
        $scope.dateConnection = device.dateConnection;
        //$scope.modeConnection = device.connection.protocol;
        $scope.modeConnection = device.modeConnection;
        //$scope.uri = device.connection.address;
        $scope.uri = device.uri;
        //$scope.configuration = device.configuration.automaticConfiguration ? 'automatic' : 'manual';
        $scope.configuration = device.configuration;
        $scope.keywords = device.keywords; 
        $scope.portforwarding = (typeof(device.portforwarding) !== "undefined") ? device.portforwarding : "Not available";
        $scope.capabilities = [];

        
        $scope.capabilities = device.capabilities;
        $scope.activeCapabilities = $scope.capabilities;
        $scope.loadProfileList(function(results) {
            $scope.capabilities = angular.copy(results);
            console.log($scope.capabilities);
        });

        if (device.configuration == 'automatic') {
            $scope.isDeviceNameEditable = false;
        } else {
            $scope.isDeviceNameEditable = true;
        }
        
        // if ($scope.capabilities){
        //     // Only capabilities whose configuration is "manual" are editable. Retrieved capability (json) doesn't contain "isEditable key. The following loop is served to add "isEditable" key depending on "configuration" key
        //     angular.forEach($scope.capabilities, function(value, key) {
        //         if(value.configuration == 'automatic'){
        //             value.isEditable = false;
        //         }else{
        //             value.isEditable = true;
        //         }
        //     });
        // } else {
        //     $scope.capabilities = [];
        // }
        
        
        $rootScope.loading = false; 

    }, function(msg){
        //error
        ngToast.create({
            content: "Unable to get device : "+msg,
            className: "danger"
        });
    });


    var addActiveCapabilities = function(profile) {
        for (var i in profile.capabilities) {
            $scope.activeCapabilities.push(profile.capabilities[i]);
        }
    };

    $scope.loadProfileList = function(callback) {
        ProfileService.list().then(function(results) {
            $scope.profiles = results;
            ProfileService.getProfilesMatchingOfDevice($routeParams.id).then(
                function(results) {
                    $scope.profilesMatching = results;
                    angular.forEach($scope.profiles, function(profile) {
                        for (var key in $scope.profilesMatching) {
                            if (profile.persistibleData['_id'] == $scope.profilesMatching[key].profileId) {
                                if (callback === undefined) {
                                    profile.isActive = true;
                                    $scope.selectedProfilesMatching.push({
                                        deviceId: $routeParams.id,
                                        profileId: $scope.profilesMatching[key].profileId
                                    });
                                }
                                
                                addActiveCapabilities(profile);
                                
                                break;
                            } else {
                                profile.isActive = false;
                            }
                        }

                    });

                    if (callback !== undefined) {
                        callback($scope.activeCapabilities);
                        $scope.activeCapabilities = [];
                    }
                },
                function(errors) {

                }
            );
            
            console.log($scope.selectedProfilesMatching);
        });

    };


    $scope.applyProfile = function() {
        var deletedProfiles = [];
        var found = false;

        for (var i in $scope.profilesMatching) {
            for (var j in $scope.selectedProfilesMatching) {
                if ($scope.profilesMatching[i].profileId == $scope.selectedProfilesMatching[j].profileId) {
                    found = false;
                    break;
                } else {
                    found = true;
                }
            }
            if (found)
                deletedProfiles.push($scope.profilesMatching[i].persistableData);
        }


        console.log(deletedProfiles);
        console.log($scope.selectedProfilesMatching);
        console.log($scope.activeCapabilities);

        ProfileService.deleteProfilesMatching(deletedProfiles).then(function(results) {
            if (results.error == 0) {
                ProfileService.addProfilesMatching($scope.selectedProfilesMatching).then(function(results) {
                    if (results.error == 0) {
                        $('#listProfilesModal').modal('hide');
                        $scope.capabilities = angular.copy($scope.activeCapabilities);
                        $scope.selectedProfilesMatching = [];
                        $scope.activeCapabilities = [];
                        $scope.ShowIsOpen = false;
                    }
                });
            }
        });
        
        // The codes below is for local-test purpose. Remove it if unused
        // $('#listProfilesModal').modal('hide');
        // $scope.capabilities = angular.copy($scope.activeCapabilities);
        // $scope.selectedProfilesMatching = [];
        // $scope.activeCapabilities = [];
        // $scope.ShowIsOpen = false;
        
        //$scope.EditIsOpen = false;
    };

    var unselectProfilesMatching = function(profile) {
        for (var key in $scope.selectedProfilesMatching) {
            if (profile.persistibleData['_id'] == $scope.selectedProfilesMatching[key].profileId) {
                $scope.selectedProfilesMatching.splice(key, 1);
                break;
            }
        }
    };

    var removeActiveCapabilities = function(profile) {
        for (var i in $scope.activeCapabilities) {
            for (var j in profile.capabilities) {
                if (profile.capabilities[j].id == $scope.activeCapabilities[i].id) {
                    $scope.activeCapabilities.splice(i, 1);
                    //break;
                }
            }
        }
    };

    $scope.addProfileIntoMatchingList = function(profile) {
        profile.isActive = !profile.isActive;

        var profileMatching = {
            deviceId: $routeParams.id,
            profileId : profile.persistibleData['_id']
        };

        if (profile.isActive) {
            $scope.selectedProfilesMatching.push(profileMatching);
            addActiveCapabilities(profile);
        }
        else {
            unselectProfilesMatching(profile);
            removeActiveCapabilities(profile);
        }

        console.log($scope.activeCapabilities);
        console.log($scope.selectedProfilesMatching);
    };

    $scope.loadCapabilitiesBy = function(profile) {
        $scope.activeProfile = profile;
        $scope.capabilitiesFromProfile = profile.capabilities;
       
    };

    

    /*retrieve the available protocols and add them to the view*/
    ProtocolsFactory.find().then(function(protocols){
        ngToast.create("Protocols loaded.");
        $scope.protocols = protocols;
        //List of protocol from an existing capability (obliged to abort cross config problem between new cap and cap from existing)
        $scope.protocolsFromExisting = JSON.parse(JSON.stringify(protocols));
        $scope.protocolsFromEdited = JSON.parse(JSON.stringify(protocols));

    }, function(msg){
        ngToast.create({
            content: "Unable to get protocols : "+msg,
            className: "danger"
        }); 
    });

    function isEmpty(obj) {
        return Object.keys(obj).length === 0;
    }

    // This method is being used for creating the object such as : 
    // {
    //      speed: 34,
    //      angle: 34
    // }
    // The value "34" is the user's inputs
    var filterParamsOn = function(params) {
        var configParams = {};
        if (params != null) {
            for (var i = 0; i < params.length; i++) {
                var param = params[i];
                if ($scope.configParams[param.idp] != null)
                    configParams[param.idp] = $scope.configParams[param.idp];
                else {
                    configParams = {};
                    break;
                }
            }
        }
        
        return configParams;
    };

    // This method is being used for concatenating the server's responses
    var getKeyValue = function(data) {
        var keyValueParts = data.split(',');
        var keyPart = keyValueParts[0];
        var valuePart = keyValueParts[1];
        return keyPart + ' : ' + valuePart;
    };

    $scope.removeResponses = function() {
        $scope.responsesCapability = [];
    };

    /*Testing capability function*/
    $scope.testCapability = function(newCapability){
        //$scope.isLoading = true;
        $scope.responsesCapability = [];
        var configParams = filterParamsOn(newCapability.params);

        var protocol = newCapability.protocol;
        var protocolName = protocol.protocolName.toLowerCase();
        var host = $scope.uri;
        var port = ''
            ,method = ''
            ,pathName = '';
        
        for (var i = 0; i < protocol.parameters.length; i++) {
            var parameter = protocol.parameters[i];
            
            switch (parameter.name) {
                case 'method':
                    method = parameter.value;
                    break;
                case 'port':
                    port = parameter.value;
                    break;
                case 'uri':
                    pathName = parameter.value;
                    break;
                default:
                    break;
            }
        }
        var url = protocolName + '://' + host + ':' + port + pathName;
        console.log(url);
        var paramInfos = {
            'method': method,
            'url': url,
            'configParams': configParams
        };
        console.log(configParams);
        DeviceFactory.testCapability(paramInfos).then(function(data){
            $scope.responsesCapability = [];
            $scope.isLoading = false;
            ngToast.create("Capability tested.");
            console.log(data);
            // Intepret the sensor's response here => Hint : delimiter ; differentiates between array and object, delimiter , differentiates between key and value. We have to test with the robot
            /**
            * Check if the data contains ;. If so, split it by ;, otherwise, we consider it as a key:value (format: key, value received)
            */
            if (data.indexOf(';') >= 0) {
                var keyValuePairs = data.split(';');
                for (var i = 0; i < keyValuePairs.length; i++) {
                    var aKeyValuePair = keyValuePairs[i];
                    if (aKeyValuePair.indexOf(',') >= 0)
                        $scope.responsesCapability.push(getKeyValue(aKeyValuePair));
                }
            } else if (data.indexOf(',') >= 0) {
                $scope.responsesCapability.push(getKeyValue(data));
            } else $scope.responsesCapability.push(data);
            //$scope.isResponseCapability = true;
        }, function(data){
            
        });

       
    };

  

    /*Function for setting the capability to modify to the scope and display the edition section*/
    $scope.openAndEditCapability = function(index, capability){
        $scope.ShowIsOpen = false;
        if ($scope.indexOfShowingCapability != index || !$scope.EditIsOpen) {
            $scope.EditIsOpen = true;
            loadCapabilitiesIntoForm(index, capability);
            
        } else $scope.EditIsOpen = false;
    };


    /*Function for show capability*/
    $scope.openAndShowCapability = function(index, capability){
        $scope.EditIsOpen = false;
        if (($scope.indexOfShowingCapability != index || !$scope.ShowIsOpen) && capability) {
            $scope.ShowIsOpen = true;
            loadCapabilitiesIntoForm(index, capability);
        } else $scope.ShowIsOpen = false;
        
    };

    var loadCapabilitiesIntoForm = function(index, capability) {
        $scope.indexOfShowingCapability = index;
        $scope.editedCapability = JSON.parse(JSON.stringify(capability));
        //Not to have same reference
        for (var i = $scope.protocolsFromEdited.length - 1; i >= 0; i--) {
            var dataset = $scope.protocolsFromEdited[i];
            if (dataset.protocolName == capability.protocol.protocolName) {
                $scope.protocolsFromEdited[i].parameters = capability.protocol.parameters;
                $scope.editedCapability.protocol = $scope.protocolsFromEdited[i];
                break;
            }
        }
    };

    $scope.showDetailsOfCapability = function(capability) {
        console.log(capability);
        $scope.detailsOfCapability = capability;
    };


 


}]);
