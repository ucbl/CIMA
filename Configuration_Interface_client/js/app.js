/* Module de notre appli */

var app = angular.module('MonApp.routes', ['ngRoute'])

/* route qui spécifie les URLS */
app.config(function($routeProvider){
  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
  .when('/device/:id', {templateUrl: 'partials/device.html', controller: 'DeviceCtrl'})
  .otherwise({redirectTo : '/'});
});
angular.module('MonApp.mod', ['ui.bootstrap']);
var ctrl = angular.module('MonApp.mod').controller('AccordionDemoCtrl', function ($scope) {
    $scope.oneAtATime = true;
    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
});
angular.module("MonApp",["MonApp.routes","MonApp.mod"]);