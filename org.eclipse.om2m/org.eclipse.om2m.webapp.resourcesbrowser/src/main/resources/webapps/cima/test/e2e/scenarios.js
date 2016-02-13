'use strict';
/* global element, browser, by */
var ec = protractor.ExpectedConditions;
describe('CIMA Login Page', function() {
    var loginURL = 'http://localhost:8080/cima/ManualConfiguration.html#/login';
    beforeEach(function() {
        browser.get(loginURL);
        
    });

  
    it('should login', function() {
        browser.sleep(1000);
        element(by.id('login-google')).click();

        browser.driver.getAllWindowHandles().then(function(handles) {
            console.log(handles.length);
            browser.switchTo().window(handles[1]);
            browser.ignoreSynchronization = true;
            expect(browser.getCurrentUrl()).toContain('accounts.google.com');

            var emailInput = browser.driver.findElement(by.id('Email'));
            emailInput.sendKeys('liristestcima@gmail.com');

            browser.sleep(1000);

            var signInButton = browser.driver.findElement(by.name('signIn'));
            signInButton.click();

            browser.sleep(1000);

            var passwordInput = browser.driver.findElement(by.id('Passwd'));
            passwordInput.sendKeys('Abc1234567');

            browser.sleep(1000);

            var signInButton = browser.driver.findElement(by.id('signIn'));
            signInButton.click();

            browser.waitForAngular();
            browser.switchTo().window(handles[0]);
            expect(browser.getCurrentUrl()).toContain('localhost:8080');
            browser.sleep(3000);
        });
        
        
       
    });
});

describe('CIMA Home Page ', function() {
    
    beforeEach(function() {
        browser.driver.get('http://localhost:8080/cima/ManualConfiguration.html#/');
        browser.executeScript('window.localStorage.setItem("ngStorage-userSession", "admin")');
        browser.sleep(20000);
        browser.driver.get('http://localhost:8080/cima/ManualConfiguration.html#/');
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

    it('tests select all', function() {
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

    it('redirects to device info list', function() {
        //element.all(by.repeater('device in devices')).get(0).all(by.tagName('td')).get()
        element(by.id('viewDevice1')).click();
        expect(browser.getCurrentUrl()).toBe('http://localhost:4040/cima/ManualConfiguration.html#/device/0123456789');
    });
});

describe("Device Info Page ", function() {
    beforeEach(function() {
        browser.get('http://localhost:4040/cima/ManualConfiguration.html#/device/DEVICE_1');
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
});