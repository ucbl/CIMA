
/* Controller page device.html */
app.controller('DeviceCtrl', function($http, $scope, $rootScope, DeviceFactory, ProtocolsFactory, $routeParams, ngToast) {

	$rootScope.loading = true;
	$scope.EditIsOpen = false;
	$scope.idrequired = false;
	$scope.selected = undefined;
	$scope.ShowIsOpen = false;

	/*retrieve information about the device and add them to the view*/
	DeviceFactory.get($routeParams.id).then(function(device){
		
        ngToast.create("Device loaded.");


		$scope.id = device.id;
		$scope.name = device.name;
		$scope.dateConnection = device.dateConnection;
		$scope.modeConnection = device.modeConnection;
		$scope.uri = device.uri;
		$scope.configuration = device.configuration;
		$scope.keywords = device.keywords; 

		if(device.configuration=='automatic'){
			$scope.isDeviceNameEditable = false;
		}else{
			$scope.isDeviceNameEditable = true;
		}

		//Nécessaire pour connaitre possibilitée ou non d'éditer
		if(device.capabilities){
			$scope.capabilities = device.capabilities;
			angular.forEach($scope.capabilities, function(value, key) {
				if(value.configuration=='automatic'){
					value.isEditable=false;
				}else{
					value.isEditable=true;
				}
			});
		}else{
			$scope.capabilities = [];
		}
		$rootScope.loading = false; 

	}, function(msg){
		//error
		ngToast.create({
            content: "Unable to get device : "+msg,
            className: "danger"
        });
	})

	/*retrieve the available protocols and add them to the view*/
	ProtocolsFactory.find().then(function(protocols){
		ngToast.create("Protocols loaded.");
		$scope.protocols = protocols;
		//List of protocol from an existing capability (obliged to abort cross config problem between new cap and cap from existiong)
		$scope.protocolsFromExisting = JSON.parse(JSON.stringify(protocols));
		$scope.protocolsFromEdited = JSON.parse(JSON.stringify(protocols));

	}, function(msg){
		ngToast.create({
            content: "Unable to get protocols : "+msg,
            className: "danger"
        });	
	})
	
	/*Add the capability to the model and to the view if it's a success*/
	$scope.addCapability = function(newCapability){
		
		if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null){			
			var cap = {};
			cap.id = newCapability.id;
			cap.protocol = {};
			cap.protocol.protocolName = newCapability.protocol.protocolName;
			cap.protocol.parameters = newCapability.protocol.parameters;
			cap.configuration='manual';
			cap.isEditable=true;

			DeviceFactory.addCapability($scope.id,cap).then(function(){
				$scope.capabilities.push(cap);
				ngToast.create("Capability added.");

			}, function(msg){
				//error
				ngToast.create({
            		content: "Unable to add capability : "+msg,
            		className: "danger"
        		});	
			});
			$scope.newCapability = {}; 
		}
	}

	/* Add a capability from an existing capability. Need specific control */
	$scope.addCapabilityFromExisting = function(newCapability){
		var add = true;
		//Test if id change
		if($scope.existingCapability.id === newCapability.id){
			add = false;
			$scope.idrequired = true;
		}

		//If id change -> add
		if(newCapability.id !=null && newCapability.protocol.protocolName != null && newCapability.protocol.parameters!= null && add){			
			
			$scope.idrequired = false;
			var cap = {};
			cap.id = newCapability.id;
			cap.protocol = {};
			cap.protocol.protocolName = newCapability.protocol.protocolName;
			cap.protocol.parameters = newCapability.protocol.parameters;
			cap.configuration='manual';
			cap.isEditable=true;
			
			DeviceFactory.addCapability($scope.id,cap).then(function(){
				$scope.capabilities.push(cap);
				ngToast.create("Capability added.");
			}, function(msg){
				//error
				ngToast.create({
            		content: "Unable to add capability : "+msg,
            		className: "danger"
        		});	
			});
			$scope.newCapability = {}; 
		}
	}
        
    /*remove a capability from the model and the view*/
    $scope.removeCapability = function (row) {
        
        var r = confirm("Are you sure to delete ?");
		if (r == true) {
			DeviceFactory.removeCapability($scope.id, row.id).then(function(){
        	var index = $scope.capabilities.indexOf(row);
        	if (index !== -1) {
            	$scope.capabilities.splice(index, 1);
        	}
        	ngToast.create("Capability removed.");
        	$scope.EditIsOpen= false;
			}, function(msg){
				//error
				ngToast.create({
            		content: "Unable to remove capability : "+msg,
            		className: "danger"
        		});				
			});
		}
    }

    /*Testing capability function*/
	$scope.testCapability = function(newCapability){
		var cap = {};
		cap.id = newCapability.id;
		cap.protocol = {};
		cap.protocol.protocolName = newCapability.protocol.protocolName;
		cap.protocol.parameters = newCapability.protocol.parameters;
		
		DeviceFactory.testCapability($scope.id, cap).then(function(){
		ngToast.create("Capability tested.");

		}, function(msg){
			//error
			ngToast.create({
            	content: "Unable to test capability : "+msg,
            	className: "danger"
        	});		
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
			ngToast.create("Device saved.");

		}, function(msg){
			//error
			ngToast.create({
            	content: "Unable to save device : "+msg,
            	className: "danger"
        	});
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
			ngToast.create("Capability modified.");

		}, function(msg){
			//error
			ngToast.create({
            	content: "Unable to modify capability : "+msg,
            	className: "danger"
        	});		
		});
	}

	/*Function for setting the capability to modify to the scope and display the edition section*/
	$scope.openAndEditCapability = function(capability){
		if($scope.EditIsOpen){
			$scope.EditIsOpen = false;
		}else{
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

  		/*Function for show capability*/
	$scope.openAndShowCapability = function(capability){
		if($scope.ShowIsOpen){
			$scope.ShowIsOpen = false;
		}else{
			$scope.ShowIsOpen = true;
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
	};

	/*Function for closing the edition div*/
	$scope.CloseShowCapability = function(){
		if($scope.ShowIsOpen){
			$scope.ShowIsOpen = false;
		}
	};

    /*Fucntion for intercepting the existing capability selection*/
  	$scope.onSelectCapability = function ($item, $model, $label) {
  		$scope.existingCapability = JSON.parse(JSON.stringify($item));
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