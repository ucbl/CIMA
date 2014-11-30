
/* Controller page device.html */
app.controller('DeviceCtrl', function ($http, $scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams) {

	$rootScope.loading = true;
	$scope.EditIsOpen = false;
	$scope.idrequired = false;
	$scope.selected = undefined;

	/*retrieve information about the device and add them to the view*/
	DeviceFactory.get($routeParams.id).then(function(device){
		$scope.id = device.id;
		$scope.name = device.name;
		$scope.dateConnection = device.dateConnection;
		$scope.modeConnection = device.modeConnection;
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
		//List of protocol from an existing capability (obliged to abort cross config problem between new cap and cap from existiong)
		$scope.protocolsFromExisting = JSON.parse(JSON.stringify(protocols));
		$scope.protocolsFromEdited = JSON.parse(JSON.stringify(protocols));

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
		}
	}

	/* Add a capability from an existing capability. Need specific control */
	$scope.addCapabilityFromExisting = function(newCapability){
		var add = true;
		if( JSON.stringify($scope.existingCapability) !== angular.toJson(newCapability) ){
			//alert('newCapFrom existing !=  existing capability');
			
			if(JSON.stringify($scope.existingCapability.protocol) !== angular.toJson(newCapability.protocol)){
			//alert('protocol change');
				
				if($scope.existingCapability.id === newCapability.id){
					//alert('id ddnt change -> problem');
					add = false;
					//alert('you must specified other ID because you change protocol')
					$scope.idrequired = true;

				}
			}
		}


		if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null && add){			
			$scope.idrequired = false;
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
		}
	}
        
    /*remove a capability from the model and the view*/
    $scope.removeCapability = function (row) {
        
        DeviceFactory.removeCapability($scope.id, row.id).then(function(){
        	var index = $scope.capabilities.indexOf(row);
        	if (index !== -1) {
            	$scope.capabilities.splice(index, 1);
        	}
        	$scope.EditIsOpen= false;
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
		device.dateConnection = $scope.dateConnection;
		device.modeConnection = $scope.modeConnection;
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
		$scope.EditIsOpen = true;
		$scope.editedCapability = JSON.parse(JSON.stringify(capability));
	    //Not to have same reference
	    for (i = $scope.protocolsFromEdited.length - 1; i >= 0; i--) {
			dataset = $scope.protocolsFromEdited[i];
			if (dataset.protocolName == capability.protocol.protocolName) {
				$scope.protocolsFromEdited[i].parameters = capability.protocol.parameters;
			    $scope.editedCapability.protocol = $scope.protocolsFromEdited[i];
			    break;
			}
		}
	}

    /*Function for closing the edition div*/
	$scope.CloseEditCapability = function(){
		if($scope.EditIsOpen){
			$scope.EditIsOpen = false;
		}
	}

    /*Auto Indent capabilities searching bloc*/
  	$scope.getCapability = function(val) {
	  	return $http.get(URL_CAPABILITIES, {
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
	    for (i = $scope.protocolsFromExisting.length - 1; i >= 0; i--) {
			dataset = $scope.protocolsFromExisting[i];
			if (dataset.protocolName == $item.protocol.protocolName) {
				$scope.protocolsFromExisting[i].parameters = $item.protocol.parameters;
			    $scope.NewFromExistingCapability.protocol = $scope.protocolsFromExisting[i];
			    break;
			}
		}
	};


});
