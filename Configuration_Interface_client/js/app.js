/* Module de notre appli */

var app =angular.module('MonApp.routes', ['ngRoute'])
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

angular.module('MonApp.accordion', ['ui.bootstrap']).controller('AccordionCtrl', function ($scope) {
    $scope.status = {
    isOpen: true
  };
});
app.directive('modalDialog', function() {
    return {
        restrict: 'E',
        scope: {
            show: '='
        },
        replace: true, // Replace with the template below
        transclude: true, // we want to insert custom content inside the directive
        link: function(scope, element, attrs) {
            scope.dialogStyle = {};
            if (attrs.width)
                scope.dialogStyle.width = attrs.width;
            if (attrs.height)
                scope.dialogStyle.height = attrs.height;
            scope.hideModal = function() {
                scope.show = false;
            };
        },
        template: "<div class='ng-modal' ng-show='show'>" +
            "<div class='ng-modal-overlay' ng-click='hideModal()'>" +
            "</div><div class='ng-modal-dialog' ng-style='dialogStyle'>" +
            "<div class='ng-modal-close' ng-click='hideModal()'>X</div>" +
            ""+
            "<div class='ng-modal-dialog-content' ng-transclude></div></div></div>"
    };
});

app.controller('MyCtrl', ['$scope', function($scope) {
    $scope.modalShown = false;
    $scope.toggleModal = function() {
        $scope.modalShown = !$scope.modalShown;
    };
}]);  
 angular.module("MonApp",
    ["MonApp.routes",
        "MonApp.accordion",  
    ]);
