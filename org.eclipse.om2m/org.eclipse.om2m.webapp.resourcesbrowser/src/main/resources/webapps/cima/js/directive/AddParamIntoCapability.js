'use strict';

ProfileController.directive('addParamIntoCapability',['$compile', function($compile) {
    return {
        restrict: 'AC',
        link: function(scope, elem, attrs) {
            scope.counter = 0;
            scope.addParamIntoCapability = function() {
                var template = '<div class="form-inline paramDirective" role="form" style="margin-top: 10px;" id="parameter'+scope.counter+'"><h4>Parameter</h4><div class="form-group"><label for="name">Name</label><input type="text" class="form-control" ng-model="newCapability.params['+scope.counter+'].idp" /><div class="form-group"><label for="type">Type</label><select class="form-control" ng-model="newCapability.params['+scope.counter+'].type"><option value="int">int</option><option value="string">string</option></select></div><div class="form-group"><button type="button" class="btn btn-danger" ng-click="removeParamFromCapability('+scope.counter+')"><span class="glyphicon glyphicon-minus"></span></button></div></div></div>';
                elem.append($compile(template)(scope));
                scope.counter++;
            };

            scope.removeParamFromCapability = function(id) {
                angular.element(document.querySelector('#parameter'+id)).remove();
                scope.newCapability.params.splice(id, 1);
                
            };

           
        }
    };
}]);