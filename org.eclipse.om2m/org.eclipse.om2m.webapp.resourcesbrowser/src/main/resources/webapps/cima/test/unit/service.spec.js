'use strict';

describe('DeviceFactory', function() {
    var devices = [
        {
            "id" : "0123456789",
            "name" : "EV3",
            "configuration" : "automatic",
            "uri" : "http://192.168.0.2",
            "dateConnection" : "10/10/14",
            "modeConnection" : "USB",
            "portforwarding": 91483,
            "capabilities": [
                {
                    "id" : "ev3Back",
                    "configuration" : "automatic",
                    "protocol": {
                        "protocolName" : "USB",
                        "parameters" : [
                            {
                                "name" : "method",
                                "value" : "POST" 
                            },
                            {
                                "name" : "port",
                                "value" : "8080" 
                            },
                           {
                                "name" : "URI",
                                "value" : "/capability" 
                            },
                            {
                                "name" : "body",
                                "value" : "YmFjaw==" 
                            }
                        ]
                    },
                    "keywords" : ["ev3","back","robot","mouvement","right"]
                },
                {
                    "id" : "ev3Front",
                    "configuration" : "manual",
                    "protocol": {
                        "protocolName" : "USB",
                        "parameters" : [
                            {
                                "name" : "method",
                                "value" : "POST" 
                            },
                            {
                                "name" : "port",
                                "value" : "8080" 
                            },
                           {
                                "name" : "URI",
                                "value" : "/capability" 
                            },
                            {
                                "name" : "body",
                                "value" : "YmFjaw==" 
                            }
                        ]
                    },
                    "keywords" : ["ev3","reculer","back","robot","mouvement","left"]
                }
            ]
        },
        {
            "id" : "9876543210",
            "portforwarding": 91483,
            "name" : "unObjet",
            "configuration" : "manual",
            "uri" : "http://192.168.0.1",
            "dateConnection" : "09/10/14",
            "modeConnection" : "HTTP",
            "capabilities": [
                {
                    "id" : "CapObjet1",
                    "configuration" : "automatic",
                    "protocol": {
                        "protocolName" : "HTTP",
                        "parameters" : [
                            {
                                "name" : "method",
                                "value" : "POST" 
                            },
                            {
                                "name" : "port",
                                "value" : "8080" 
                            },
                           {
                                "name" : "URI",
                                "value" : "/capability" 
                            },
                            {
                                "name" : "body",
                                "value" : "YmFjaw==" 
                            }
                        ]
                    },
                    "keywords" : ["ev3","back","robot","mouvement", "wiki"]

                }
            ]
        },
        {
            "id" : "0918273645",
            "name" : "Android2202",
            "portforwarding": 91483,
            "configuration" : "automatic",
            "uri" : "http://192.168.0.3",
            "dateConnection" : "08/10/14",
            "modeConnection" : "HTTP",
            "capabilities": [
                {
                    "id" : "ev3Back",
                    "configuration" : "manual",
                    "protocol": {
                        "protocolName" : "HTTP",
                        "parameters" : [
                            {
                                "name" : "method",
                                "value" : "POST" 
                            },
                            {
                                "name" : "port",
                                "value" : "8080" 
                            },
                           {
                                "name" : "URI",
                                "value" : "/capability" 
                            },
                            {
                                "name" : "body",
                                "value" : "YmFjaw==" 
                            }
                        ]
                    },
                    "keywords" : ["ev3","reculer","back","robot","mouvement"]
                }
            ]
        }
    ];

    beforeEach(module('DeviceController'));

    var $q,
        deferred,
        $scope,
        DeviceFactory;
    beforeEach(inject(function(_DeviceFactory_, _$q_, _$rootScope_) {
        $q = _$q_;
        $scope = _$rootScope_.$new();
        DeviceFactory = _DeviceFactory_;
        deferred = $q.defer();
        
    }));

    it(' should find 3 devices', function() {
        var result  = {};
        spyOn(DeviceFactory, "find").and.callFake(function() {
            deferred.resolve(devices);
            return deferred.promise;
        });

        DeviceFactory.find().then(function(data) {
            result = data; 
        });

        $scope.$apply();
        expect(result).toBe(devices);
        expect(result.length).toEqual(3);
    });

    it(' should find the first device', function() {
        var result  = {};
        spyOn(DeviceFactory, "get").and.callFake(function() {
            deferred.resolve(devices[0]);
            return deferred.promise;
        });
        DeviceFactory.get().then(function(data) {
            result = data;
        });
        $scope.$apply();
        expect(result).toBe(devices[0]);
    });
});

describe('ProfileService', function() {
    var profiles = [
      {
        "@context": "http://localhost:4040/angular-project/api/cima/robot/capability/context",
        "@id": "http://localhost:4040/angular-project/api/cima/robot/getMotorARotate",
        "persistibleData": {
            "_id": "aaa",
            "_etag": "bbb"
        },
        "name": "Profil 1",
        "description": "description 1",
        "capabilities":"[{\"id\":\"sensor\",\"protocol\":{\"protocolName\":\"HTTP\",\"parameters\":[{\"name\":\"method\",\"value\":\"POST\"},{\"name\":\"uri\",\"value\":\"\/A\/rotate\"},{\"name\":\"port\",\"value\":\"8080\"},{\"name\":\"body\",\"value\":\"motor-PPP-rotate\"}]},\"configuration\":\"manual\",\"cloudPort\":0},{\"id\":\"motor\",\"protocol\":{\"protocolName\":\"HTTP\",\"parameters\":[{\"name\":\"URL\",\"value\":\"jhklkjh\"},{\"name\":\"URI\",\"value\":\"khgjklhgj\"}]},\"configuration\":\"automatic\",\"cloudPort\":0}]"
      },
      {
        "name": "Profil 2",
        "persistibleData": {
            "_id": "ccc",
            "_etag": "ddd"
        },
        "description": "description 2",
        "capabilities": "[{\"id\":\"sensor-S4-gyro\",\"protocol\":{\"protocolName\":\"HTTP\",\"parameters\":[{\"name\":\"URL\",\"value\":\"jhklkjh\"},{\"name\":\"URI\",\"value\":\"khgjklhgj\"}]},\"configuration\":\"manual\",\"cloudPort\":0},{\"id\":\"sensor-S5-gyro\",\"protocol\":{\"protocolName\":\"HTTP\",\"parameters\":[{\"name\":\"URL\",\"value\":\"jhklkjh\"},{\"name\":\"URI\",\"value\":\"khgjklhgj\"}]},\"configuration\":\"automatic\",\"cloudPort\":0}]"
      }
    ];

    beforeEach(module('ProfileController'));

    var $q,
        deferred,
        $scope,
        ProfileService,
        $httpBackend;
    beforeEach(inject(function(_ProfileService_, _$q_, _$rootScope_, _$httpBackend_) {
        $q = _$q_;
        $scope = _$rootScope_.$new();
        ProfileService = _ProfileService_;
        $httpBackend = _$httpBackend_;
        deferred = $q.defer();
        $httpBackend.when('GET', URL_PROTOCOLS).respond([]);
    }));

    it(' should find all profiles', function() {
        var result  = {};
        spyOn(ProfileService, "list").and.callFake(function() {
            deferred.resolve(profiles);
            return deferred.promise;
        });
        
        ProfileService.list().then(function(data) {
            result = data; 
        });
        $httpBackend.flush();
        $scope.$apply();
        expect(result).toBe(profiles);
    });

    it(' should add a profile', function() {
        var profile = {
            "@context": "http://localhost:4040/angular-project/api/cima/robot/capability/context",
            "@id": "http://localhost:4040/angular-project/api/cima/robot/getMotorARotate",
            "persistibleData": {
                "_id": "aaa",
                "_etag": "bbb"
            },
            "name": "Profil 1",
            "description": "description 1",
            "capabilities":"[{\"id\":\"sensor\",\"protocol\":{\"protocolName\":\"HTTP\",\"parameters\":[{\"name\":\"method\",\"value\":\"POST\"},{\"name\":\"uri\",\"value\":\"\/A\/rotate\"},{\"name\":\"port\",\"value\":\"8080\"},{\"name\":\"body\",\"value\":\"motor-PPP-rotate\"}]},\"configuration\":\"manual\",\"cloudPort\":0},{\"id\":\"motor\",\"protocol\":{\"protocolName\":\"HTTP\",\"parameters\":[{\"name\":\"URL\",\"value\":\"jhklkjh\"},{\"name\":\"URI\",\"value\":\"khgjklhgj\"}]},\"configuration\":\"automatic\",\"cloudPort\":0}]"
          };
        var result  = {};
        spyOn(ProfileService, "add").and.callFake(function() {
            profiles.push(profile);
            deferred.resolve(profiles);
            return deferred.promise;
        });
        
        ProfileService.add(profile).then(function(data) {
            result = data; 
        });
        
        $scope.$apply();
        expect(result.length).toEqual(3);
    });

    it(' should delete a profile', function() {
        var result  = {};
        spyOn(ProfileService, "delete").and.callFake(function() {
            profiles.pop();
            deferred.resolve(profiles);
            return deferred.promise;
        });
        
        ProfileService.delete().then(function(data) {
            result = data; 
        });
        
        $scope.$apply();
        expect(result.length).toEqual(2);
    });

    it(' should edit a profile', function() {
        var result  = {};
        spyOn(ProfileService, "edit").and.callFake(function() {
            profiles[1].name = 'Profile 3';
            deferred.resolve(profiles);
            return deferred.promise;
        });
        
        ProfileService.edit().then(function(data) {
            result = data; 
        });
        
        $scope.$apply();
        expect(result[1].name).toEqual('Profile 3');
    });
});