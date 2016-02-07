'use strict';

describe('HomeController', function() {
    beforeEach(module('HomeController'));
    var $controller,
        $scope;
    var data = [
        {
            "id" : "0123456789",
            "name" : "EV3",
            "configuration" : "automatic",
            "uri" : "http://192.168.0.2",
            "dateConnection" : "10/10/14",
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

    describe('HomeController', function() {
        var DeviceFactory,
            deferred,
            $q;
        beforeEach(inject(function(_$controller_, _$rootScope_,  _DeviceFactory_, _$q_) {
            $controller = _$controller_;
            $scope = _$rootScope_.$new();
            $q = _$q_;
            deferred = _$q_.defer();
            DeviceFactory = _DeviceFactory_;
            spyOn(DeviceFactory, 'find').and.returnValue(deferred.promise);
            //deviceFactory = _DeviceFactory_;
            
            $controller('HomeController', {$scope: $scope, DeviceFactory: DeviceFactory});
            
        }));

        it(' should retrieve 4 devices', function() {
            $scope.loadDevices();
            deferred.resolve(data);
            $scope.$apply();
            expect($scope.devices.length).toBe(4);
        });

        it(' should display all devices', function() {
            expect($scope.devices).toBeEmptyArray();
            $scope.loadDevices();
            deferred.resolve(data);
            //$scope.selectAll();
            $scope.$apply();
            expect($scope.devices).not.toBeEmptyArray();
        });

        it(' should display 0 device', function() {
            $scope.loadDevices();
            deferred.resolve(data);
            $scope.$apply();
            $scope.filters['configuration'] = {};
            $scope.filters['configuration']['http'] = false;
            $scope.filters['configuration']['usb'] = false;
            $scope.notify();
            expect($scope.isSelectAll).toBe(false);
            expect($scope.devices).toBeEmptyArray();
            expect($scope.devices.length).toEqual(0);
        });

        it(' should display 0 device', function() {
            
        });
    });
    
    describe('$interval', function() {
        var $interval,
            $httpBackend;
        beforeEach(inject(function(_$controller_, _$rootScope_, _$interval_, _$httpBackend_) {
            $controller = _$controller_;
            $httpBackend = _$httpBackend_;
            $scope = _$rootScope_.$new();
            //$intervalSpy = jasmine.createSpy('$interval', _$interval_).and.callThrough();
            $interval = _$interval_;
            $controller('HomeController', {$scope: $scope});
            $httpBackend.when('GET', URL_DEVICE).respond(200, {devices: data});
        }));


        it(' tests if $interval is called 4 times', function() {
            $interval.flush(15001);
            expect($scope.countLoadDevices).toBe(4);
        });

    });
});