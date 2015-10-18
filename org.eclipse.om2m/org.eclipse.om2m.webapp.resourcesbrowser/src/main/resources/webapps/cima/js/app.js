'use strict';
/* Application modules */
//var app = angular.module('CIMA', ['CIMA.HomeController', 'CIMA.DeviceController', 'ngRoute', 'CIMA.ui', 'CIMA.tags', 'CIMA.toast'])
var app = angular.module('CIMA', ['ngRoute', 'ui.bootstrap', 'ngTagsInput', 'ngToast']);
/*routing URLs*/
app.config(['$routeProvider', function($routeProvider){

  $routeProvider
  .when('/', {templateUrl: 'partials/home.html', controller: 'HomeController'})
  .when('/device/:id', {templateUrl: 'partials/device.html', controller: 'DeviceController'})
  .otherwise({redirectTo : '/'});
}]);

/* bootstrap UI module */
//angular.module('CIMA.ui', ['ui.bootstrap']);
//angular.module('CIMA.tags', ['ngTagsInput']);
angular.module('CIMA.toast', ['ngToast'])
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

// angular.module('test', []).controller('testcontroller', ['$scope', function($scope) {
//   $scope.num = 0;
// }]);