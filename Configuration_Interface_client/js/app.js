/* Application moduls */
var app = angular.module("MonApp", ["MonApp.routes", "MonApp.ui", "MonApp.tags", "MonApp.toast"]);

/*routing URLs*/
angular.module('MonApp.routes', ['ngRoute']).config(['$routeProvider', function($routeProvider){

  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeCtrl'})
  .when('/device/:id', {templateUrl: 'partials/device.html'})
  .otherwise({redirectTo : '/'});
}]);

/* bootstrap UI module */
angular.module('MonApp.ui', ['ui.bootstrap']);
angular.module('MonApp.tags', ['ngTagsInput']);
angular.module('MonApp.toast', ['ngToast'])
  .config(['ngToastProvider', function(ngToast) {
    ngToast.configure({
      verticalPosition: 'top',
      horizontalPosition: 'right',
      dismissButton: true,
      maxNumber: 3,
    });
  }]);

/*Auth header for http requests*/
app.run(function($http){
	$http.defaults.headers.common.Authorization = 'Basic YWRtaW46YWRtaW4='

});

