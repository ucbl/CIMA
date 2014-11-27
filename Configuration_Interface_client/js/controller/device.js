
/* Controller page device.html */
app.controller('DeviceCtrl', function ($http, $scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams) {

	/*Promesses*/
	$rootScope.loading = true;
	$scope.newComment ={};
	$rootScope.EditIsOpen = false;

	/*retrieve information about the device and add them to the view*/
	DeviceFactory.get($routeParams.id).then(function(device){
		$scope.id = device.id;
		$scope.name = device.name;
		$scope.lastConnectionDate = device.dateConnection;
		$scope.connectionMode = device.modeConnection;
		$scope.uri = device.uri;

		if(device.capabilities){
			$scope.capabilities = device.capabilities;
		}else{
			$scope.capabilities = [];
		}
		$scope.keywords = device.keywords; 
		$rootScope.loading = false; 

	}, function(msg){
		alert(msg);
	})

	/*retrieve the available protocols and add them to the view*/
	ProtocolsFactory.find().then(function(protocols){
		$scope.protocols = protocols;
	}, function(msg){
		alert(msg);
	})
	
	/*Add the capability to the model and to the view if it's a success*/
	$scope.addCapability = function(newCapability){
		if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null){			

			var cap = {};
			cap.id = newCapability.id;
			cap.protocol = {};
			cap.protocol.protocolName = newCapability.protocol.protocolName;
			cap.protocol.parameters = newCapability.protocol.parameters;
			
			DeviceFactory.addCapability($scope.id,cap).then(function(){
				$scope.capabilities.push(cap);
			}, function(){
				alert('Votre capability n\'a pas pu être ajoutée');
			});
			$scope.newCapability = {}; 
			$scope.showme = false;
		}
	}
        
    /*remove a capability from the model and the view*/
    $scope.removeCapability = function (row) {
        
        DeviceFactory.removeCapability($scope.id, row.id).then(function(){
        	var index = $scope.capabilities.indexOf(row);
        	if (index !== -1) {
            	$scope.capabilities.splice(index, 1);
        	}
        	$rootScope.EditIsOpen= false;
		}, function(){
			alert('Votre capability n\'a pas pu être supprimé');
		});

        
    }

    /*Testing capability function*/
	$scope.testCapability = function(newCapability){
		var cap = {};
		cap.id = newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = newCapability.protocol.protocolName;
		cap.protocol.parameters = newCapability.protocol.parameters;
		
		DeviceFactory.testCapability($scope.id, cap).then(function(){

		}, function(){
			alert('Votre capability n\'a pas pu être testé');
		});
	}

	/*Function for saving a device */
	$scope.saveDevice = function(){
		var device = {};

		device.id = $scope.id;
		device.name = $scope.name;
		device.lastConnectionDate = $scope.lastConnectionDate;
		device.connectionMode = $scope.connectionMode;
		device.uri = $scope.uri;
		device.capabilities = {};
		device.capabilities = $scope.capabilities;

		DeviceFactory.saveDevice(device).then(function(){

		}, function(){
			alert('Votre device n\'a pas pu être sauvegardé');
		});
	}
	/*Function for modifing a device */
	$scope.modifyCapability = function(capability){
		var cap = {};
		cap.id = capability.id;
		cap.protocol = {};
		cap.protocol.protocolName = capability.protocol.protocolName;
		cap.protocol.parameters = capability.protocol.parameters;
		DeviceFactory.modifyCapability($scope.id, cap).then(function(){

		}, function(){
			alert('Votre capability n\'a pas pu être modifié');
		});
	}

	/*Function for setting the capability to modify to the scope and display the edition section*/
	$scope.openAndEditCapability = function(capability){
		$rootScope.EditIsOpen = true;
		$scope.editedCapability = JSON.parse(JSON.stringify(capability));
	}

    /*Function for closing the edition div*/
	$scope.CloseEditCapability = function(){
		if($rootScope.EditIsOpen){
			$rootScope.EditIsOpen = false;
		}
	}

    /*Auto Indent capabilities searching bloc*/
	$scope.selected = undefined;
  	$scope.getCapability = function(val) {
	  	return $http.get('json/capabilities.json', {
	    	params: {
	      		filter: val
	    	}
	  	}).then(function(response){
	      	return response.data;
	    }); 
  	};

    /*Fucntion for intercepting the existing capability selection*/
  	$scope.onSelectCapability = function ($item, $model, $label) {
	    $scope.NewFromExistingCapability = $item;
	    for (i = $scope.protocols.length - 1; i >= 0; i--) {
		dataset = $scope.protocols[i];
			if (dataset.protocolName == $item.protocol.protocolName) {
				alert("Protocole dans select trouvé !");
				$scope.protocols[i].parameters = $item.protocol.parameters;
			    $scope.NewFromExistingCapability.protocol = $scope.protocols[i];
			    break;
			}
		}
	}; 
	$scope.SelectCapability = function () {
	   /*$scope.editedCapability.protocol = $item; 
	    for (i = $scope.protocols.length - 1; i >= 0; i--) {
		dataset = $scope.protocols[i];
			if (dataset.protocolName == $item.protocol.protocolName) {
				$scope.protocols[i].parameters = $item.protocol.parameters;
			    $scope.editedCapability.protocol = $scope.protocols[i];
			    break;
			}
		}*/ 
		$scope.protocols.protocol =null;
	}; 	
});
