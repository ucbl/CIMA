'use strict';

// Change to this structure if we have time to refactor
var ProfileController = angular.module('ProfileController', []);

ProfileController.run(['$rootScope', 'ProtocolsFactory', 'ngToast', function($rootScope, ProtocolsFactory, ngToast) {
     /*retrieve the available protocols and add them to the view*/
    ProtocolsFactory.find().then(function(protocols){
        ngToast.create("Protocols loaded.");
        $rootScope.protocols = protocols;
        //List of protocol from an existing capability (obliged to abort cross config problem between new cap and cap from existing)
        $rootScope.protocolsFromExisting = JSON.parse(JSON.stringify(protocols));
        $rootScope.protocolsFromEdited = JSON.parse(JSON.stringify(protocols));

    }, function(msg){
        ngToast.create({
            content: "Unable to get protocols : "+msg,
            className: "danger"
        }); 
    });

}]);

ProfileController.controller('ProfileController', ['$scope', 'ProfileService', '$rootScope', '$log', 'ProtocolsFactory', 'ngToast', function($scope, ProfileService, $rootScope, $log, ProtocolsFactory, ngToast) {
    $scope.newCapability = {};
    /*Add the capability to the model and to the view if it's a success*/
    $scope.addCapability = function(newCapability)
    {
        console.log('I am in addCapability');
        if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null)
        {           
            console.log(newCapability);
            var cap = {};
            cap.id = newCapability.id;
            cap.protocol = {};
            cap.protocol.protocolName = newCapability.protocol.protocolName;
            cap.protocol.parameters = newCapability.protocol.parameters;
            cap.configuration = 'manual';
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
            ngToast.create("Capability created.");
            $scope.newCapability = {};
        }

    };

    


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
            cap.configuration = 'manual';
            cap.isEditable = true;
            
            DeviceFactory.addCapability($scope.id,cap).then(function(){
                $scope.capabilities.push(cap);
                ngToast.create("Capability added.");
            }, function(msg){
                //error
                ngToast.create({
                    content: "Unable to add capability : " + msg,
                    className: "danger"
                }); 
            });
            $scope.newCapability = {}; 
        }
    };

    ProfileService.list().then(
        function(results) {
            $scope.profiles = results;
            $log.log($scope.profiles);
            $rootScope.$storage.profiles = $scope.profiles;
        },
        function(errors) {

        }
    );



    var deletingProfile;
    $scope.openModal = function(profile) {
        deletingProfile = angular.copy(profile);
        jQuery('#deleteModal').modal('toggle');
    };

    $scope.delete = function() {
        if (deletingProfile) {
            console.log(deletingProfile.persistibleData);
            ProfileService.delete(deletingProfile.persistibleData).then(
                function(results) {
                    jQuery('.modal').modal('toggle');

                    if (results.message) {
                        $scope.profiles.splice($scope.profiles.indexOf(deletingProfile), 1);
                        ngToast.create(results.message);
                        ProfileService.list().then(function(results) {
                            $scope.profiles = results;
                            $rootScope.$storage.profiles = $scope.profiles;
                            // var profiles = results && results.profiles;
                            // var render = results && results.pagination && results.pagination.render;
     
                            // if (profiles) {
                            //     $scope.profiles = profiles;
                            // }
                            // if (render) {
                            //     $scope.pagination = ConfigService.getRender(render);
                            // } else {
                            //     $scope.pagination = false;
                            // }
                            
                            
                        });

                    } 

                    if (results.error) {
                        ngToast.create({
                            content: results.message,
                            className: 'danger'
                        });
                    }

                }
            );
        }
    };
}]);


// Remove localStorage later, profile will be saved on server-side.
ProfileController.controller('AddProfileController', ['$scope', '$rootScope', 'ProfileService', '$location', '$localStorage', 'ngToast', function($scope, $rootScope, ProfileService, $location, $localStorage, ngToast) {
    //$scope.capabilities = $rootScope.$storage.capabilitiesForProfile;
    $scope.capabilities = angular.copy($rootScope.$storage.capabilitiesForProfile);

    angular.forEach($scope.capabilities, function(value, key) {
       value.isActive = false;
    });
    $scope.profile = {};
    $scope.profile.capabilities = [];

    var loadCapabilitiesIntoForm = function(index, capability) {
        $scope.detailCapability = JSON.parse(JSON.stringify(capability));
        //Not to have same reference
        for (var i = $rootScope.protocolsFromEdited.length - 1; i >= 0; i--) {
            var dataset = $rootScope.protocolsFromEdited[i];
            if (dataset.protocolName == capability.protocol.protocolName) {
                $rootScope.protocolsFromEdited[i].parameters = capability.protocol.parameters;
                $scope.detailCapability.protocol = $rootScope.protocolsFromEdited[i];
                break;
            }
        }
    };

    $scope.openAndshowCapability = function(index, capability) {
        loadCapabilitiesIntoForm(index, capability);
    };

    $scope.chooseCapability = function(capability) {
        capability.isActive = !capability.isActive;
        if (capability.isActive) {
            $scope.profile.capabilities.push(capability);
        } else {
            for (var key in $scope.profile.capabilities) {
                if ($scope.profile.capabilities[key].id == capability.id) {
                    $scope.profile.capabilities.splice(key, 1);
                    break;
                }
            }
            
        }
        console.log($scope.profile.capabilities);
    };

    $scope.add = function() {
        var profile = angular.copy($scope.profile);
        angular.forEach(profile.capabilities, function(value, key) {
            delete value.$$hashKey;
            delete value.isActive;
        });
        console.log(profile);
        if (profile.name && profile.description) {
            if (profile.capabilities) {
                profile.capabilities = JSON.stringify(profile.capabilities);
            }
            console.log(profile.capabilities);
            //$location.path('/profile');
            ProfileService.add(profile).then(
                function(results) {
                    if (results) {
                        console.log("success");
                        ngToast.create(results.message);
                        $location.path('/profile');    
                    }
                },
                function(errors) {

                }
            );
        }
    };
}]);

ProfileController.controller('EditProfileController', ['$scope', '$rootScope', 'ProfileService', '$location', '$localStorage', '$routeParams', 'ngToast', function($scope, $rootScope, ProfileService, $location, $localStorage, $routeParams, ngToast) {
    // $scope.capabilities = $rootScope.$storage.capabilitiesForProfile;
    // $scope.profiles = $rootScope.$storage.profiles;
    $scope.capabilities = angular.copy($rootScope.$storage.capabilitiesForProfile);
    $scope.profiles = angular.copy($rootScope.$storage.profiles);
    angular.forEach($scope.capabilities, function(value, key) {
       value.isActive = false;
    });

    var id = $routeParams.id ? $routeParams.id : 1;
    var preChooseCapabilities = function() {
        for (var i in $scope.profile.capabilities) {
            for (var j in $scope.capabilities) {
                if ($scope.profile.capabilities[i].id == $scope.capabilities[j].id) {
                    $scope.capabilities[j].isActive = true;
                    break;
                }
            }
        }
    };

    var loadCapabilitiesIntoForm = function(index, capability) {
        $scope.editedCapability = JSON.parse(JSON.stringify(capability));
        //Not to have same reference
        for (var i = $rootScope.protocolsFromEdited.length - 1; i >= 0; i--) {
            var dataset = $rootScope.protocolsFromEdited[i];
            if (dataset.protocolName == capability.protocol.protocolName) {
                $rootScope.protocolsFromEdited[i].parameters = capability.protocol.parameters;
                $scope.editedCapability.protocol = $rootScope.protocolsFromEdited[i];
                break;
            }
        }
    };

    var findProfileById = function(id) {
        var profile = {};
        for (var key in $scope.profiles) {
            if ($scope.profiles[key].persistibleData['_id'] == id) {
                profile = $scope.profiles[key];
                break;
            }
        }

        return profile;
    };

    // Find profile before pre-choosing the capabilities
    $scope.profile = findProfileById(id);
    preChooseCapabilities();

    $scope.chooseCapability = function(capability) {
        capability.isActive = !capability.isActive;
        if (capability.isActive) {
            $scope.profile.capabilities.push(capability);
        } else {
            for (var key in $scope.profile.capabilities) {
                if ($scope.profile.capabilities[key].id == capability.id) {
                    $scope.profile.capabilities.splice(key, 1);
                    break;
                }
            }
            
        }
        console.log($scope.profile.capabilities);
    };

    /*Function for setting the capability to modify to the scope and display the edition section*/
    $scope.openAndEditCapability = function(index, capability){
        loadCapabilitiesIntoForm(index, capability);
    };

    $scope.editCapability = function(editedCapability) {
        for (var i in $scope.profile.capabilities) {
            if ($scope.profile.capabilities[i].id == editedCapability.id) {
                $scope.profile.capabilities[i] = editedCapability;
                break;
            }
        }
        console.log($scope.profile);
    };

    $scope.edit = function() {
        var profile = angular.copy($scope.profile);
        angular.forEach(profile.capabilities, function(value, key) {
            delete value.$$hashKey;
            delete value.isActive;
        });
        if (profile.name && profile.description) {
            if (profile.capabilities) {
                profile.capabilities = JSON.stringify($scope.profile.capabilities);
            }
            console.log(profile);
            //$location.path('/profile');
            ProfileService.edit(profile).then(
                function(results) {
                    if (results.message) {
                        //ngToast.create(results.message); // uncomment this when server send "non-cute" messages
                        $location.path('/profile');
                    }
                },
                function(errors) {

                }
            );
        }
    };
}]);