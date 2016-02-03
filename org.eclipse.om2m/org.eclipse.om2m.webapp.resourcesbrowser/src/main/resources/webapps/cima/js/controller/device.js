'use strict';
/* Controller page device.html */
//angular.module('CIMA.DeviceController', []).controller('DeviceController', ['$http', '$scope', '$rootScope', 'DeviceFactory', 'ProtocolsFactory', '$routeParams', 'ngToast', function($http, $scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams, ngToast) {
app.controller('DeviceController', ['$http', '$scope', '$rootScope', 'DeviceFactory', 'ProtocolsFactory', '$routeParams', 'ngToast', 'ProfileService', '$localStorage', function($http, $scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams, ngToast, ProfileService, $localStorage) {
    $rootScope.$storage = $localStorage;
    if (!$rootScope.$storage.capabilitiesForProfile)
        $rootScope.$storage.capabilitiesForProfile = [];
    $rootScope.loading = true;
    $scope.EditIsOpen = false;
    $scope.idrequired = false;
    $scope.selected = undefined;
    $scope.ShowIsOpen = false;
    $scope.indexOfShowingCapacity = -1;
    $scope.configParams = {};
    $scope.isResponseSensor = false;
    $scope.responseSensors = [];
    $scope.isLoading = false;
    $scope.activeProfile = {};
    $scope.loadProfileList = function() {
        if (!$scope.profiles) {
            ProfileService.list().then(function(results) {
                $scope.profiles = results;
            });
        }
    };

    $scope.applyProfile = function() {
        $('#listProfilesModal').modal('hide');
        angular.copy($scope.activeProfile.capabilities, $scope.capabilities);
    };

    $scope.loadCapabilitiesBy = function(profile) {
        angular.forEach($scope.profiles, function(value, key) {
            value.isActive = false;
        });
        profile.isActive = true;
        $scope.activeProfile = profile;
        $scope.capabilitiesFromProfile = profile.capabilities;
        if ($scope.capabilitiesFromProfile){
            // Only capabilities whose configuration is "manual" are editable. Retrieved capability (json) doesn't contain "isEditable key. The following loop is served to add "isEditable" key depending on "configuration" key
            angular.forEach($scope.capabilitiesFromProfile, function(value, key) {
                if(value.configuration == 'automatic'){
                    value.isEditable = false;
                }else{
                    value.isEditable = true;
                }
            });
        } 
    };


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
        if(device.configuration == 'automatic'){
            $scope.isDeviceNameEditable = false;
        }else{
            $scope.isDeviceNameEditable = true;
        }

        // DeviceFactory.getAllCapabilitiesFromDevice(device).then(function(res) {
        //     $scope.capabilities = res;
        //     //Nécessaire pour connaitre possibilitée ou non d'éditer
        //     if ($scope.capabilities){
        //         // Only capabilities whose configuration is "manual" are editable. Retrieved capability (json) doesn't contain "isEditable key. The following loop is served to add "isEditable" key depending on "configuration" key
        //         angular.forEach($scope.capabilities, function(value, key) {
        //             if(value.configurationCapability == 'automatic'){
        //                 value.isEditable = false;
        //             }else{
        //                 value.isEditable = true;
        //             }
        //         });
        //     } else {
        //         $scope.capabilities = [];
        //     }
        // });
        
        if ($scope.capabilities){
            // Only capabilities whose configuration is "manual" are editable. Retrieved capability (json) doesn't contain "isEditable key. The following loop is served to add "isEditable" key depending on "configuration" key
            angular.forEach($scope.capabilities, function(value, key) {
                if(value.configuration == 'automatic'){
                    value.isEditable = false;
                }else{
                    value.isEditable = true;
                }
            });
        } else {
            $scope.capabilities = [];
        }
        
        
        $rootScope.loading = false; 

    }, function(msg){
        //error
        ngToast.create({
            content: "Unable to get device : "+msg,
            className: "danger"
        });
    });

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
    })
    
    /*Add the capability to the model and to the view if it's a success*/
    $scope.addCapability = function(newCapability)
    {
        console.log('I am in addCapability');
        if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null)
        {           
            console.log(newCapability.protocol.parameters);
            var cap = {};
            cap.id = newCapability.id;
            cap.protocol = {};
            cap.protocol.protocolName = newCapability.protocol.protocolName;
            cap.protocol.parameters = newCapability.protocol.parameters;
            cap.configuration='manual';
            cap.cloudPort = 0;
            // cap.isEditable=true;
            // cap.hydra = "http://www.w3.org/ns/hydra/core#";
            // cap.rdf = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";
            // cap.rdfs = "http://www.w3.org/2000/01/rdf-schema#";
            // cap.xsd = "http://www.w3.org/2001/XMLSchema#";
            // cap.owl = "http://www.w3.org/2002/07/owl#";
            // cap.vs = "http://www.w3.org/2003/06/sw-vocab-status/ns#";
            // cap.defines = { "@reverse": "rdfs:isDefinedBy" };
            // cap.comment = "rdfs:comment";
            // cap.label = "rdfs:label";
            // cap.domain = { "@id": "rdfs:domain", "@type": "@id" };
            // cap.range = { "@id": "rdfs:range", "@type": "@id" };
            // cap.subClassOf = { "@id": "rdfs:subClassOf", "@type": "@id", "@container": "@set" };
            // cap.subPropertyOf = { "@id": "rdfs:subPropertyOf", "@type": "@id", "@container": "@set" };
            // cap.seeAlso = { "@id": "rdfs:seeAlso", "@type": "@id" };
            // cap.status = "vs:term_status";
            // console.log("cap = "+JSON.stringify(cap));
            // var doc = {
            //   "http://schema.org/name": "Manu Sporny",
            //   "http://schema.org/url": {"@id": "http://manu.sporny.org/"},
            //   "http://schema.org/image": {"@id": "http://manu.sporny.org/images/manu.png"}
            // };
            // var context = {
            //     "hydra": "http://www.w3.org/ns/hydra/core#",
            //     "rdf": "http://www.w3.org/1999/02/22-rdf-syntax-ns#",
            //     "rdfs": "http://www.w3.org/2000/01/rdf-schema#",
            //     "xsd": "http://www.w3.org/2001/XMLSchema#",
            //     "owl": "http://www.w3.org/2002/07/owl#",
            //     "vs": "http://www.w3.org/2003/06/sw-vocab-status/ns#",
            //     "defines": { "@reverse": "rdfs:isDefinedBy" },
            //     "comment": "rdfs:comment",
            //     "label": "rdfs:label",
            //     "domain": { "@id": "rdfs:domain", "@type": "@id" },
            //     "range": { "@id": "rdfs:range", "@type": "@id" },
            //     "subClassOf": { "@id": "rdfs:subClassOf", "@type": "@id", "@container": "@set" },
            //     "subPropertyOf": { "@id": "rdfs:subPropertyOf", "@type": "@id", "@container": "@set" },
            //     "seeAlso": { "@id": "rdfs:seeAlso", "@type": "@id" },
            //     "status": "vs:term_status"
            // };
            $rootScope.$storage.capabilitiesForProfile.push(cap);
            // // compact a document according to a particular context
            // // see: http://json-ld.org/spec/latest/json-ld/#compacted-document-form
            // jsonld.compact(doc, context, function(err, compacted) {
            //     console.log(compacted);
            //     console.log(JSON.stringify(compacted, null, 2));
            // });
            
            // DeviceFactory.addCapability($scope.id,cap).then(function(){
            //     $scope.capabilities.push(cap);
            //     ngToast.create("Capability added.");
            // }, function(msg){

                //error
            //    ngToast.create({
            //        content: "Unable to add capability : "+msg,
            //        className: "danger"
            //    }); 
            //});
            $scope.newCapability = {}; 
        }
    }

    /* Add a capability from an existing capability. Need specific control */
    $scope.addCapabilityFromExisting = function(newCapability){
        var add = true;
        //Test if id change
        if($scope.existingCapability.id === newCapability.id){
            add = false;
            $scope.idrequired = true;
        }
        newCapability = newCapability || {};
        newCapability.protocol = newCapability.protocol || {};
        //If id change -> add
        if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null && add){          
            
            $scope.idrequired = false;
            var cap = {};
            cap.id = newCapability.id;
            cap.protocol = {};
            cap.protocol.protocolName = newCapability.protocol.protocolName;
            cap.protocol.parameters = newCapability.protocol.parameters;
            cap.configuration='manual';
            cap.isEditable=true;
            
            DeviceFactory.addCapability($scope.id,cap).then(function(){
                $scope.capabilities.push(cap);
                ngToast.create("Capability added.");
            }, function(msg){
                //error
                ngToast.create({
                    content: "Unable to add capability : "+msg,
                    className: "danger"
                }); 
            });
            $scope.newCapability = {}; 
        }
    }
        
    /*remove a capability from the model and the view*/
    $scope.removeCapability = function (row) {
        
        var r = confirm("Are you sure to delete ?");
        if (r == true) {
            DeviceFactory.removeCapability($scope.id, row.id).then(function(){
            var index = $scope.capabilities.indexOf(row);
            if (index !== -1) {
                $scope.capabilities.splice(index, 1);
            }
            ngToast.create("Capability removed.");
            $scope.EditIsOpen= false;
            }, function(msg){
                //error
                ngToast.create({
                    content: "Unable to remove capability : "+msg,
                    className: "danger"
                });             
            });
        }
    }
    function isEmpty(obj) {
        return Object.keys(obj).length === 0;
    }
    /*Testing capability function*/
    $scope.testCapability = function(newCapability){
        $scope.isLoading = true;
  
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

        var getKeyValue = function(data) {
            var keyValueParts = data.split(',');
            var keyPart = keyValueParts[0];
            var valuePart = keyValueParts[1];
            return keyPart + ' : ' + valuePart;
        };
        
        var newCapability = newCapability || {};
        if (!isEmpty(newCapability)) {
            var configParams = filterParamsOn(newCapability.params);
            if ((newCapability.id.indexOf("sensor") >= 0) || (newCapability.id.indexOf("stop") >= 0) || (!isEmpty(configParams) && newCapability.id.indexOf("motor") >= 0)) {
                var protocol = newCapability.protocol;
                var protocolName = protocol.protocolName.toLowerCase();
                //var protocolName = $scope.modeConnection;
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
                var paramInfos = {
                    'method': method,
                    'url': url,
                    'configParams': configParams,
                    'result': newCapability.result
                };
                console.log(configParams);
                DeviceFactory.testCapability(paramInfos).then(function(data){
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
                                $scope.responseSensors.push(getKeyValue(aKeyValuePair));
                        }
                    } else $scope.responseSensors.push(getKeyValue(data));
                    $scope.isResponseSensor = true;
                }, function(data){
                    $scope.isLoading = false;
                });
            } else {
                alert('Entrez tous les champs vides s\'il vous plait !');
            } 
        }
    }

    $scope.closeResponseSensorModal = function() {
        $scope.isRespo = false;
    };

    /*Function for saving a device */
    $scope.saveDevice = function(){
        var device = {};

        device.id = $scope.id;
        device.name = $scope.name;
        device.connection.dateConnection = $scope.dateConnection;
        device.connection.modeConnection = $scope.modeConnection;
        device.uri = $scope.uri;
        device.capabilities = {};
        
        device.capabilities = $scope.capabilities;

        DeviceFactory.saveDevice(device).then(function(){
            ngToast.create("Device saved.");

        }, function(msg){
            //error
            ngToast.create({
                content: "Unable to save device : "+msg,
                className: "danger"
            });
        });
    }
    /*Function for modifing a device */
    $scope.modifyCapability = function(capability){
        var cap = {};
        cap.id = capability.id;
        cap.protocol = {};
        cap.protocol.protocolName = capability.protocol.protocolName;
        cap.protocol.parameters = capability.protocol.parameters;
        DeviceFactory.modifyCapability($scope.id, cap).then(function(){
            ngToast.create("Capability modified.");

        }, function(msg){
            //error
            ngToast.create({
                content: "Unable to modify capability : "+msg,
                className: "danger"
            });     
        });
    }

    var loadCapabilitiesIntoForm = function(index, capability) {
        console.log(capability);
        $scope.indexOfShowingCapacity = index;
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

    /*Function for setting the capability to modify to the scope and display the edition section*/
    $scope.openAndEditCapability = function(index, capability){
        $scope.ShowIsOpen = false;
        if ($scope.indexOfShowingCapacity != index || !$scope.EditIsOpen) {
            $scope.EditIsOpen = true;
            loadCapabilitiesIntoForm(index, capability);
            
        } else $scope.EditIsOpen = false;
    }

    /*Function for show capability*/
    $scope.openAndShowCapability = function(index, capability){
        $scope.EditIsOpen = false;
        if ($scope.indexOfShowingCapacity != index || !$scope.ShowIsOpen) {
            $scope.ShowIsOpen = true;
            loadCapabilitiesIntoForm(index, capability);
        } else $scope.ShowIsOpen = false;
        
    };

    /*Function for closing the edition div*/
    $scope.CloseEditCapability = function(){
        if($scope.EditIsOpen){
            $scope.EditIsOpen = false;
        }
    }

    /*Auto Indent capabilities searching bloc*/
    $scope.getCapability = function(val) {
        console.log(val);
        return $http.get(URL_CAPABILITIES, {
            params: {
                filter: val
            }
        }).
        then(function(response){
            console.log(response.data);
            return response.data;
        });
        
    };

    /*Function for closing the edition div*/
    $scope.CloseShowCapability = function(){
        if($scope.ShowIsOpen){
            $scope.ShowIsOpen = false;
        }
    };

    /*Fucntion for intercepting the existing capability selection*/
    $scope.onSelectCapability = function ($item, $model, $label) {
        $scope.existingCapability = JSON.parse(JSON.stringify($item));
        $scope.NewFromExistingCapability = $item;
        for (var i = $scope.protocolsFromExisting.length - 1; i >= 0; i--) {
            var dataset = $scope.protocolsFromExisting[i];
            if (dataset.protocolName == $item.protocol.protocolName) {
                $scope.protocolsFromExisting[i].parameters = $item.protocol.parameters;
                $scope.NewFromExisting.protocol = $scope.protocolsFromExisting[i];
                break;
            }
        }
    };


}]);
