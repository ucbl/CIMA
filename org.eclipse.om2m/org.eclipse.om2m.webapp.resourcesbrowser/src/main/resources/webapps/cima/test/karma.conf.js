// Karma configuration
// Generated on Sat Oct 17 2015 16:41:20 GMT+0200 (South Africa Standard Time)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: '..',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine'],


    // list of files / patterns to load in the browser
    files: [
      'lib/angular1312.min.js',
      'lib/ngToast.min.js',
      'lib/angular1312-route.min.js',
      'lib/angular1312-sanitize.min.js',
      'lib/angular1312-animate.min.js',
      'lib/ui-bootstrap-tpls-0.11.2.min.js',
      'lib/ng-tags-input.min.js',
      'lib/angular-google-plus.js',
      'lib/angular-md5.js',
      'lib/ngstorage.js',
      'lib/angular-mocks.js',
      'lib/jasmine-matchers.js',
      'lib/jquery-1.11.1.js',
      'js/url/url.js',
      'js/app.js',
      'js/controller/*.js',
      'js/service/*.js',
      'test/unit/*.spec.js'
    ],


    // list of files to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },


    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9876,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_INFO,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false
  })
}
