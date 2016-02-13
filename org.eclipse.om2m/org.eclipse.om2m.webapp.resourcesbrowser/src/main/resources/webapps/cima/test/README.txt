Prerequiste: install node, npm
1) For unit test
    npm install karma --save-dev
    npm install -g karma-cli
    npm install karma-jasmine karma-chrome-launcher --save-dev
    karma start karma.conf.js (make sure in the right directory)
2) For e2e test
    npm install -g protractor
    webdriver-manager update
    webdriver-manager start
    protractor protractor.conf.js (make sure in the right directory)

/!\ : Re-write the test to "meet" the login needs
/!\ : Re-structure the DI in the controllers (HomeController, DeviceController...)