'use strict';

app.controller('ProfileController', ['$scope', 'ProfileService', '$rootScope', function($scope, ProfileService, $rootScope) {
    
    ProfileService.list().then(
        function(results) {
            $scope.profiles = results;
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
            ProfileService.delete(deletingProfile.persistibleData).then(function(results) {
                jQuery('.modal').modal('toggle');

                //$scope.errors = results && results.errors;
                $scope.message = results && results.message;

                if ($scope.message) {
                    $scope.profiles.splice($scope.profiles.indexOf(deletingProfile), 1);
                    
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

            });
        }
    };
}]);


// Remove localStorage later, profile will be saved on server-side.
app.controller('AddProfileController', ['$scope', '$rootScope', 'ProfileService', '$location', '$localStorage', function($scope, $rootScope, ProfileService, $location, $localStorage) {
    //$scope.capabilities = $rootScope.$storage.capabilitiesForProfile;
    $scope.capabilities = angular.copy($rootScope.$storage.capabilitiesForProfile);
    angular.forEach($scope.capabilities, function(value, key) {
       value.isActive = false;
    });
    $scope.profile = {};
    $scope.profile.capabilities = [];

    $scope.chooseCapability = function(capability) {
        capability.isActive = !capability.isActive;
        if (capability.isActive) {
            $scope.profile.capabilities.push(capability);
        } else {
            for (var key in $scope.profile.capabilities) {
                if (!$scope.profile.capabilities[key].isActive) {
                    $scope.profile.capabilities.splice(key, 1);
                }
            }
            
        }
        console.log($scope.profile.capabilities);
    };

    $scope.add = function() {
        console.log($scope.profile);
        if ($scope.profile.name && $scope.profile.description) {
            $scope.profile.capabilities = JSON.stringify($scope.profile.capabilities);
            //$location.path('/profile');
            ProfileService.add($scope.profile).then(
                function(results) {
                     if (!results.errors)
                        $location.path('/profile');
                },
                function(errors) {

                }
            );
        }
    };
}]);

// Remove localStorage later, profile will be saved on server-side.
app.controller('EditProfileController', ['$scope', '$rootScope', 'ProfileService', '$location', '$localStorage', '$routeParams', function($scope, $rootScope, ProfileService, $location, $localStorage, $routeParams) {
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
    

    var findProfileByid = function(id) {
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
    $scope.profile = findProfileByid(id);
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

    $scope.edit = function() {
        if ($scope.profile.name && $scope.profile.description) {
            console.log($scope.profile);
            $scope.profile.capabilities = JSON.stringify($scope.profile.capabilities);
            //$location.path('/profile');
            ProfileService.edit($scope.profile).then(
                function(results) {
                    if (!results.errors) {
                        $location.path('/profile');
                    }
                },
                function(errors) {

                }
            );
        }
    };
}]);