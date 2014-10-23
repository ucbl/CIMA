/* Module de notre appli */

var app = angular.module('MonApp.routes', ['ngRoute'])
/* route qui spécifie les URLS */
app.config(function($routeProvider){
  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
  .when('/device/:id', {templateUrl: 'partials/device.html', controller: 'DeviceCtrl'})
  .otherwise({redirectTo : '/'});
});

app.run(function($http){
	$http.defaults.headers.common.Authorization = 'Basic YWRtaW46YWRtaW4='
});

angular.module('MonApp.mod', ['ui.bootstrap']).controller('AccordionCtrl', function ($scope) {
    $scope.status = {
    isOpen: true
  };
});

angular.module("MonApp",["MonApp.routes","MonApp.mod"]);