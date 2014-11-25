/* Module de notre appli */


var app = angular.module("MonApp",["MonApp.routes","MonApp.ui"]);

/* route qui spécifie les URLS */
angular.module('MonApp.routes', ['ngRoute']).config(function($routeProvider){
  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
  .when('/device/:id', {templateUrl: 'partials/device.html'})
  .otherwise({redirectTo : '/'});
});

/* Module pour bootstrap UI */
angular.module('MonApp.ui', ['ui.bootstrap']);

//header Auth pour requetes http
app.run(function($http){
	$http.defaults.headers.common.Authorization = 'Basic YWRtaW46YWRtaW4='
});