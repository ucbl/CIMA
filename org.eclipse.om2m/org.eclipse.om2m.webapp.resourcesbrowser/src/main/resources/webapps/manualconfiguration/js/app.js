/* Application moduls */
var app = angular.module("MonApp",["MonApp.routes","MonApp.ui","MonApp.tags"]);

/*routing URLs*/
angular.module('MonApp.routes', ['ngRoute']).config(function($routeProvider){
  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
  .when('/device/:id', {templateUrl: 'partials/device.html'})
  .otherwise({redirectTo : '/'});
});

/* bootstrap UI modul */
angular.module('MonApp.ui', ['ui.bootstrap']);
angular.module('MonApp.tags', ['ngTagsInput']);
/*Auth header for http requests*/
app.run(function($http){
	$http.defaults.headers.common.Authorization = 'Basic YWRtaW46YWRtaW4='
});
