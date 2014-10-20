/* Module de notre appli */
var app = angular.module('MonApp', ['ngRoute'])

/* route qui spécifie les URLS */
app.config(function($routeProvider){
  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
  .when('/device/:id', {templateUrl: 'partials/device.html', controller: 'DeviceCtrl'})
  .otherwise({redirectTo : '/'});
});