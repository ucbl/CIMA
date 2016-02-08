'use strict';

ProfileController.directive('addParamIntoCapability',['$compile', function($compile) {
    return {
        restrict: 'AEC',
        replace: true,
        scope: {
            directiveScope: '=',
            newCapability: '=',
            currentIndex: '='
        },
        link: function(scope, elem, attrs) {
            
            scope.directiveScope.loadParams = function(params) {
                elem.html('');
                if (params) {
                    for (var i = 0; i < params.length; i++) {
                        var template = '<div class="form-inline" role="form" style="margin-top: 10px;" id="parameter'+i+'"><h4>Parameter</h4><div class="form-group"><label for="name">Name</label><input type="text" class="form-control" ng-model="newCapability.params['+i+'].idp" /><div class="form-group"><label for="type">Type</label><select class="form-control" ng-model="newCapability.params['+i+'].type"><option value="int">int</option><option value="string">string</option></select></div><div class="form-group"><button type="button" class="btn btn-danger" ng-click="removeParamFromCapability('+i+')"><span class="glyphicon glyphicon-minus"></span></button></div></div></div>';
                        
                        elem.append($compile(template)(scope));
                    }
                }
            };
            
            scope.directiveScope.addParamIntoCapability = function() {
                var template = '';
                if (scope.currentIndex) {
                    //console.log('Current index in directive : ' + scope.currentIndex);
                    //console.log('Counter in current directive scope : ' + scope.directiveScope[scope.currentIndex].counter);

                    template = '<div class="form-inline" role="form" style="margin-top: 10px;" id="parameter'+scope.directiveScope[scope.currentIndex].counter+'"><h4>Parameter</h4><div class="form-group"><label for="name">Name</label><input type="text" class="form-control" ng-model="newCapability.params['+scope.directiveScope[scope.currentIndex].counter+'].idp" /><div class="form-group"><label for="type">Type</label><select class="form-control" ng-model="newCapability.params['+scope.directiveScope[scope.currentIndex].counter+'].type"><option value="int">int</option><option value="string">string</option></select></div><div class="form-group"><button type="button" class="btn btn-danger" ng-click="removeParamFromCapability('+scope.directiveScope[scope.currentIndex].counter+')"><span class="glyphicon glyphicon-minus"></span></button></div></div></div>';
                    scope.directiveScope[scope.currentIndex].counter++;
                } else {
                    template = '<div class="form-inline" role="form" style="margin-top: 10px;" id="parameter'+scope.directiveScope.counter+'"><h4>Parameter</h4><div class="form-group"><label for="name">Name</label><input type="text" class="form-control" ng-model="newCapability.params['+scope.directiveScope.counter+'].idp" /><div class="form-group"><label for="type">Type</label><select class="form-control" ng-model="newCapability.params['+scope.directiveScope.counter+'].type"><option value="int">int</option><option value="string">string</option></select></div><div class="form-group"><button type="button" class="btn btn-danger" ng-click="removeParamFromCapability('+scope.directiveScope.counter+')"><span class="glyphicon glyphicon-minus"></span></button></div></div></div>';
                    scope.directiveScope.counter++;
                }
                elem.append($compile(template)(scope));
            };

            scope.removeParamFromCapability = function(id) {

                angular.element(document.querySelector('#parameter'+id)).remove();
                // Here, we shouldn't use scope.newCapability.splice because it will re-arrange the array's order which occured the bug
                delete scope.newCapability.params[id];
                console.log(scope.newCapability);
            };

           
        }
    };
}]);