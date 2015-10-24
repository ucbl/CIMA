'use strict';
// Comment this block to test
var http = require('http');
describe('CIMA Home Page', function() {
    
    beforeEach(function() {
        browser.get('http://localhost:4040/cima/ManualConfiguration.html#/');
    });

    it('should have a title', function() {
        expect(browser.getTitle()).toEqual('CIMA Configuration');
        //expect(by.binding('toggle')).toBe(true);
        //element(by.id('search')).click();
    });

    it('should find 3 devices whose configuration is automatic', function() {
        element(by.model('query.configuration')).sendKeys('auto');
        expect(element.all(by.repeater('device in devices')).count()).toEqual(3);
    });

    it('should toggle search section', function() {
        expect($('[ng-show=toggle]').isDisplayed()).toBe(true);
        element(by.id('search')).click();
        expect($('[ng-show=toggle]').isDisplayed()).toBe(false);
        
        
    });

    it('test select all', function() {
        element(by.id('select-all')).click();
        expect(element.all(by.repeater('device in devices')).count()).toEqual(0);
        element(by.id('select-all')).click();
        expect(element.all(by.repeater('device in devices')).count()).toBeGreaterThan(0);
    });

    it('tests http filter', function() {
        element(by.id('select-all')).click();
        element(by.id('HTTP')).click();
        expect(element.all(by.repeater('device in devices')).count()).toEqual(3);
        element.all(by.repeater('device in devices')).then(function(arr) {
            for (var i = 0; i < arr.length; i++) {
                arr[i].all(by.tagName('td')).then(function(rowElems) {
                    rowElems[4].getText().then(function(text) {
                        expect(text).toEqual("HTTP");
                    });
                    
                });
            }
            
        });
    });

    it('redirect to device info list', function() {
        //element.all(by.repeater('device in devices')).get(0).all(by.tagName('td')).get()
        element(by.id('viewDevice1')).click();
        expect(browser.getCurrentUrl()).toBe('http://localhost:4040/cima/ManualConfiguration.html#/device/0123456789');
    });
});

describe("Device Info Page", function() {
    beforeEach(function() {
        browser.get('http://localhost:4040/cima/ManualConfiguration.html#/device/0123456789');
    });

    it('verifies the device\'s name', function() {
        expect(element(by.model('name')).getAttribute('value')).toEqual("EV3");
        
    });

    it('verifies the protocol', function() {
        element.all(by.repeater('capability in capabilities')).then(function(arr) {
            for (var i = 0; i < arr.length; i++) {
                arr[i].all(by.tagName('td')).then(function(rowElems) {
                    rowElems[2].getText().then(function(text) {
                        expect(text).toEqual("USB");
                    });
                    
                });
            }
            
        });
    });
})