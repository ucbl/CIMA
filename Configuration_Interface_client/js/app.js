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

    $scope.groups = [
        {
            title: 'Dynamic Group Header - 1',
            content: 'Dynamic Group Body - 1'
        },
        {
            title: 'Dynamic Group Header - 2',
            content: 'Dynamic Group Body - 2'
        }
    ];

    $scope.status = {
        isFirstOpen: true,
        isFirstDisabled: false
    };
});
ctrl.controller('CollapseDemoCtrl', function ($scope) {
    $scope.isCollapsed = false;
});
angular.module("MonApp",["MonApp.routes","MonApp.mod"]);