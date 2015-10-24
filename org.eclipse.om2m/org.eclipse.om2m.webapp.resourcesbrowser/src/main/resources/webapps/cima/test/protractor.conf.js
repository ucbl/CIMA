exports.config = {
  allScriptsTimeout: 11000,
  specs: [
    'e2e/*.js'
  ],

  capabilities: {
    'browserName': 'chrome'
  },
  // chromeOnly: true,
  // chromeDriver: '../node_modules/protractor/selenium/chromedriver',

  baseUrl: 'http://localhost:4444/wd/hub',

  framework: 'jasmine2',

  jasmineNodeOpts: {
    defaultTimeoutInterval: 30000
  }
};
