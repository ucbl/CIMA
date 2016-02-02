'use strict';

app.controller('ProfileController', ['$scope', 'ProfileService', function($scope, ProfileService) {
    ProfileService.list().then(
        function(results) {
            $scope.profiles = results;
        },
        function(errors) {

        }
    );
}]);


// Remove localStorage later, profile will be saved on server-side.
app.controller('AddProfileController', ['$scope', '$rootScope', 'ProfileService', '$location', '$localStorage', function($scope, $rootScope, ProfileService, $location, $localStorage) {
    $scope.capabilities = $rootScope.$storage.capabilitiesForProfile;

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
    $scope.capabilities = $rootScope.$storage.capabilitiesForProfile;
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
        console.log($scope.profile.capabilities);
    };
    // $scope.profile = {
    //     "name": "Profil 2",
    //     "_id": "2",
    //     "description": "description 2",
    //     "capabilities": [
    //       {
    //         "id": "C1",
    //         "result": null,
    //           "protocol": {
    //             "protocolName": "HTTP",
    //             "parameters": [
    //               {
    //                 "name": "port",
    //                 "value": "8080"
    //               },
    //               {
    //                 "name": "body",
    //                 "value": "sensor-S4-gyro"
    //               },
    //               {
    //                 "name": "method",
    //                 "value": "GET"
    //               },
    //               {
    //                 "name": "uri",
    //                 "value": "\/S4\/EV3GyroSensor"
    //               }
    //             ]
    //           },
    //           "cloudPort": "0",
    //           "configuration": "manual"
    //       },
    //       {
    //         "id": "C2",
    //         "result": null,
    //           "protocol": {
    //             "protocolName": "HTTP",
    //             "parameters": [
    //               {
    //                 "name": "port",
    //                 "value": "8080"
    //               },
    //               {
    //                 "name": "body",
    //                 "value": "sensor-S4-gyro"
    //               },
    //               {
    //                 "name": "method",
    //                 "value": "GET"
    //               },
    //               {
    //                 "name": "uri",
    //                 "value": "\/S4\/EV3GyroSensor"
    //               }
    //             ]
    //           },
    //           "cloudPort": "0",
    //           "configuration": "manual"
    //       },
    //       // {
    //       //   "id": "bvnvbnnvbnvbn",
    //       //   "result": null,
    //       //     "protocol": {
    //       //       "protocolName": "HTTP",
    //       //       "parameters": [
    //       //         {
    //       //           "name": "port",
    //       //           "value": "8080"
    //       //         },
    //       //         {
    //       //           "name": "body",
    //       //           "value": "sensor-S4-gyro"
    //       //         },
    //       //         {
    //       //           "name": "method",
    //       //           "value": "GET"
    //       //         },
    //       //         {
    //       //           "name": "uri",
    //       //           "value": "\/S4\/EV3GyroSensor"
    //       //         }
    //       //       ]
    //       //     },
    //       //     "cloudPort": "0",
    //       //     "configuration": "manual"
    //       // }
    //     ]
    // };
    // preChooseCapabilities();
    ProfileService.get(id).then(
        function(results) {
            $scope.profile = results;
            preChooseCapabilities();
        },
        function(errors) {

        }
    );

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

    $scope.edit = function() {
        if ($scope.profile.name && $scope.profile.description) {
            console.log($scope.profile);
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