'use strict';

// Change to this structure if we have time to refactor
var ProfileController = angular.module('ProfileController', ['DeviceController', 'CIMA.toast']);

ProfileController.run(['$rootScope', 'ProtocolsFactory', function($rootScope, ProtocolsFactory, ngToast) {
     /*retrieve the available protocols and add them to the view*/
    ProtocolsFactory.find().then(function(protocols){    
        $rootScope.protocols = protocols;
        //List of protocol from an existing capability (obliged to abort cross config problem between new cap and cap from existing)
        $rootScope.protocolsFromExisting = JSON.parse(JSON.stringify(protocols));
        $rootScope.protocolsFromEdited = JSON.parse(JSON.stringify(protocols));

    }, function(msg){

    });


}]);

ProfileController.controller('ProfileController', ['$scope', 'ProfileService', '$rootScope', '$log', 'ProtocolsFactory', 'ngToast', function($scope, ProfileService, $rootScope, $log, ProtocolsFactory, ngToast) {
    $scope.directiveScope = {
        counter: 0
    };
    $scope.newCapability = {};
    $scope.newCapability.params = [];
    $scope.newCapability.result = '';
    $rootScope.$storage.capabilitiesForProfile = [];
    $rootScope.$storage.addedCapabilities = $rootScope.$storage.addedCapabilities ? $rootScope.$storage.addedCapabilities : [];
    
    $scope.isResultCheckboxDisabled = true;
    $scope.toggleResultCheckbox = function() {
        console.log($scope.newCapability);
        $scope.isResultCheckboxDisabled = !$scope.isResultCheckboxDisabled;
        $scope.newCapability.result = ($scope.newCapability.result && !$scope.isResultCheckboxDisabled) ? $scope.newCapibility.result : '';
        
    };

    /*Add the capability to the model and to the view if it's a success*/
    $scope.addCapability = function(newCapability)
    {

        $scope.newCapability = newCapability;
        if (newCapability.id != null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null)
        {   
            console.log('I am in addCapability');        
            console.log(newCapability);
            var cap = {};
            cap.id = newCapability.id;

            // /!\ : For object-assignment, we should use angular.copy
            cap.protocol = angular.copy(newCapability.protocol);
            cap.configuration = 'manual';
            cap.cloudPort = 0;
            cap.params = [];
            if (newCapability.params instanceof Object) {
                for (var key in newCapability.params) {
                    cap.params.push(newCapability.params[key]);
                }
            } else if (newCapability.params instanceof Array) {
                cap.params = newCapability.params ? newCapability.params : [];
            }
            
            cap.result = newCapability.result ? newCapability.result : null;
            console.log(cap);
            
            $rootScope.$storage.addedCapabilities.push(cap);
            $rootScope.$storage.capabilitiesForProfile.push(cap);
           
            ngToast.create("Capability created.");
            $scope.newCapability = {};
            $scope.newCapability.protocol = {};
            $scope.newCapability.params = [];
        }
        
    };

    ProfileService.list().then(
        function(results) {
            $scope.profiles = results;
            //$log.log($scope.profiles);
            $rootScope.$storage.profiles = $scope.profiles;
            angular.forEach($scope.profiles, function(profile) {
                angular.forEach(profile.capabilities, function(capability) {
                    $rootScope.$storage.capabilitiesForProfile.push(capability);
                });
            });

            angular.forEach($rootScope.$storage.addedCapabilities, function(addedCapability) {
                $rootScope.$storage.capabilitiesForProfile.push(addedCapability);
            });
            console.log("Capabilities in profiles : " + $rootScope.$storage.capabilitiesForProfile.length);
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
    $scope.directiveScopes = {
        currentIndex: 0
    };
    $scope.capabilities = angular.copy($rootScope.$storage.capabilitiesForProfile);
    $scope.profiles = angular.copy($rootScope.$storage.profiles);

    $scope.isResultCheckboxDisabled = true;
    $scope.toggleResultCheckbox = function() {
        $scope.isResultCheckboxDisabled = !$scope.isResultCheckboxDisabled;
        $scope.editedCapability.result = ($scope.editedCapability.result && !$scope.isResultCheckboxDisabled) ? $scope.editedCapability.result : '';
    };

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

    var initializeDirectiveScopes = function() {
        for (var i = 0; i < $scope.capabilities.length; i++) {
            $scope.directiveScopes[i] = {};
            if ($scope.capabilities[i].params) {
                $scope.directiveScopes[i].counter = $scope.capabilities[i].params.length;
            } else $scope.directiveScopes[i].counter = 0;
            
            
        }
        $scope.directiveScopes.currentIndex = 0;
        console.log($scope.directiveScopes);
    };
    initializeDirectiveScopes();

    var loadCapabilitiesIntoForm = function(index, capability) {
        
        $scope.editedCapability = capability;
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

    /*Function for setting the capability to modify to the scope and display the edition section*/
    $scope.openAndEditCapability = function(index, capability){
        // Clone in the first use (openAndEditCapability), not in the second use of capability (loadCapabilitiesIntoForm)
        var cloneCapability = angular.copy(capability);
        loadCapabilitiesIntoForm(index, cloneCapability); // this is to fill up the $scope.editedCapability
        $scope.isResultCheckboxDisabled = ($scope.editedCapability.result) ? false : true; 
        $scope.directiveScopes.currentIndex = index;
        $scope.directiveScopes.loadParams($scope.editedCapability.params); // methode in directive
        
    };

    $scope.editCapability = function(editedCapability) {
        
        if (editedCapability.protocol.protocolName != null && editedCapability.protocol.parameters!= null) {           
            console.log(editedCapability);

            // BEGIN /!\ : This code below is to fix directive bug
            var cap = {};
            cap.params = [];
            if (editedCapability.params instanceof Object) {
                for (var key in editedCapability.params) {
                    cap.params.push(editedCapability.params[key]);
                }
            } else if (editedCapability.params instanceof Array) {
                cap.params = editedCapability.params ? editedCapability.params : [];
            }
            editedCapability.params = cap.params;
             // END /!\ 

            for (var i in $scope.profile.capabilities) {
                if ($scope.profile.capabilities[i].id == editedCapability.id) {
                    $scope.profile.capabilities[i] = angular.copy(editedCapability);
                    break;
                }
            }

            for (var i in $scope.capabilities) {
                if ($scope.capabilities[i].id == editedCapability.id) {
                    $scope.capabilities[i] = angular.copy(editedCapability);
                    break;
                }
            }
            console.log($scope.profile);

        }



        
    };

    $scope.edit = function() {
        var profile = angular.copy($scope.profile);
        angular.forEach(profile.capabilities, function(value, key) {
            delete value.$$hashKey;
            delete value.isActive;
        });
        if (profile.name && profile.description) {
            if (profile.capabilities) {
                profile.capabilities = JSON.stringify(profile.capabilities);
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