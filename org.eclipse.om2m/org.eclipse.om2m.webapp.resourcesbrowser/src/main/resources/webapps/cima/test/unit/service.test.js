'use strict';

describe('DeviceFactory', function() {
    var DeviceFactory, testUtils;
    beforeEach(module('CIMA'));
    var devices = [
        {
            "id" : "0123456789",
            "name" : "EV3",
            "configuration" : "automatic",
            "uri" : "http://192.168.0.2",
            "dateConnecti" : "10/10/14",
            "modeConnection" : "USB",
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
        },
        {
            "id" : "hdhhdhdhdh",
            "name" : "Motorola005",
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
    var $q,
        deferred,
        $scope;
    beforeEach(inject(function(_DeviceFactory_, _$q_, _$rootScope_) {
        $q = _$q_;
        $scope = _$rootScope_.$new();
        DeviceFactory = _DeviceFactory_;
        deferred = $q.defer();
        
    }));

    it(' should find 4 devices', function() {
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