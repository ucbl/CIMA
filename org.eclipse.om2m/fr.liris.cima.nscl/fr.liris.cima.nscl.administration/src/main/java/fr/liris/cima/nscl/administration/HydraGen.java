package fr.liris.cima.nscl.administration;

/**
 * Created by ricardo on 04/02/16.
 */
public class HydraGen {
    public static String URI_SERVER = System.getProperty("org.eclipse.om2m.sclBaseAddress") + ":" + System.getProperty("org.eclipse.equinox.http.jetty.http.port") + "/om2m/nscl/applications/CIMA/administration/";
    public static String URI_CONTEXT_CAPABILITIES = URI_SERVER + "contextCapabilities";
    public static String URI_CONTEXT_DEVICE = URI_SERVER + "contextDevice";
    public static String URI_VOCAB = URI_SERVER + "vocabulaire";

    public static String vocabEV3() {
        return "{\n" +
                "\t\"@context\": {\n" +
                "\t\t\"@vocab\": \"" + URI_VOCAB + "#\",\n" +
                "\t\t\"hydra\": \"http://www.w3.org/ns/hydra/core#\",\n" +
                "\t\t\"ApiDocumentation\": \"hydra:ApiDocumentation\",\n" +
                "\t\t\"property\": {\n" +
                "\t\t\t\"@id\": \"hydra:property\",\n" +
                "\t\t\t\"@type\": \"@id\"\n" +
                "\t\t},\n" +
                "\t\t\"readonly\": \"hydra:readonly\",\n" +
                "\t\t\"writeonly\": \"hydra:writeonly\",\n" +
                "\t\t\"supportedClass\": \"hydra:supportedClass\",\n" +
                "\t\t\"supportedProperty\": \"hydra:supportedProperty\",\n" +
                "\t\t\"supportedOperation\": \"hydra:supportedOperation\",\n" +
                "\t\t\"method\": \"hydra:method\",\n" +
                "\t\t\"expects\": {\n" +
                "\t\t\t\"@id\": \"hydra:expects\",\n" +
                "\t\t\t\"@type\": \"@id\"\n" +
                "\t\t},\n" +
                "\t\t\"returns\": {\n" +
                "\t\t\t\"@id\": \"hydra:returns\",\n" +
                "\t\t\t\"@type\": \"@id\"\n" +
                "\t\t},\n" +
                "\t\t\"statusCodes\": \"hydra:statusCodes\",\n" +
                "\t\t\"code\": \"hydra:statusCode\",\n" +
                "\t\t\"rdf\": \"http://www.w3.org/1999/02/22-rdf-syntax-ns#\",\n" +
                "\t\t\"rdfs\": \"http://www.w3.org/2000/01/rdf-schema#\",\n" +
                "\t\t\"label\": \"rdfs:label\",\n" +
                "\t\t\"description\": \"rdfs:comment\",\n" +
                "\t\t\"domain\": {\n" +
                "\t\t\t\"@id\": \"rdfs:domain\",\n" +
                "\t\t\t\"@type\": \"@id\"\n" +
                "\t\t},\n" +
                "\t\t\"range\": {\n" +
                "\t\t\t\"@id\": \"rdfs:range\",\n" +
                "\t\t\t\"@type\": \"@id\"\n" +
                "\t\t},\n" +
                "\t\t\"subClassOf\": {\n" +
                "\t\t\t\"@id\": \"rdfs:subClassOf\",\n" +
                "\t\t\t\"@type\": \"@id\"\n" +
                "\t\t}\n" +
                "\t},\n" +
                "\t\"@id\": \"" + URI_VOCAB + "\",\n" +
                "\t\"@type\": \"ApiDocumentation\",\n" +
                "\t\"supportedClass\": [{\n" +
                "\t\t\"@id\": \"vocab:robotEV3\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"title\": \"robotEV3\",\n" +
                "\t\t\"label\": \"robotEV3\",\n" +
                "\t\t\"description\": \"Device robot EV3\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:robotEV3\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"Retrieve infos about the robot EV3\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": \"vocab:robotEV3\",\n" +
                "\t\t\t\"statusCodes\": [{\n" +
                "\t\t\t\t\"code\": 404,\n" +
                "\t\t\t\t\"description\": \"If the robot wasn't found.\"\n" +
                "\t\t\t}]\n" +
                "\t\t}],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:robotEV3/name\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"name\",\n" +
                "\t\t\t\t\"description\": \"The robot's name\",\n" +
                "\t\t\t\t\"domain\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"name\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's name\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:robotEV3/description\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"description\",\n" +
                "\t\t\t\t\"description\": \"The robot's description\",\n" +
                "\t\t\t\t\"domain\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"description\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's description\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:robotEV3/Portforwarding\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"description\",\n" +
                "\t\t\t\t\"description\": \"The robot's description\",\n" +
                "\t\t\t\t\"domain\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"description\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's description\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:robotEV3/id\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"id\",\n" +
                "\t\t\t\t\"description\": \"The robot's id\",\n" +
                "\t\t\t\t\"domain\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"id\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's id\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:Connection\",\n" +
                "\t\t\t\"hydra:title\": \"connection\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's connection\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:Configuration\",\n" +
                "\t\t\t\"hydra:title\": \"configuration\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's configuration\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:robotEV3/capabilities\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"capabilities\",\n" +
                "\t\t\t\t\"description\": \"The capabilities of the robot\",\n" +
                "\t\t\t\t\"domain\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\"range\": \"vocab:CapabilityCollection\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"capabilities\",\n" +
                "\t\t\t\"hydra:description\": \"The capabilities of the robot\",\n" +
                "\t\t\t\"required\": null,\n" +
                "\t\t\t\"readonly\": true,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:CapabilityCollection\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"http://www.w3.org/ns/hydra/core#Collection\",\n" +
                "\t\t\"label\": \"CapabilityCollection\",\n" +
                "\t\t\"description\": \"A collection of capabilities\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:capability_retrieve_all\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"Retrieve connected robot's capabilities\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": \"vocab:CapabilityCollection\",\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}],\n" +
                "\t\t\"supportedProperty\": []\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Configuration\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"Configuration\",\n" +
                "\t\t\"description\": \"The configurations of robot\",\n" +
                "\t\t\"supportedOperation\": [],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:Configuration/AutomaticConfiguration\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"AutomaticConfiguration\",\n" +
                "\t\t\t\t\"description\": \"The automatic configuration of the robot\",\n" +
                "\t\t\t\t\"domain\": \"vocab:Configuration\",\n" +
                "\t\t\t\t\"range\": \"http://schema.org/Boolean\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"AutomaticConfiguration\",\n" +
                "\t\t\t\"hydra:description\": \"The automatic configuration's name\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:Configuration/Profile\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"Profile\",\n" +
                "\t\t\t\t\"description\": \"The user's profile\",\n" +
                "\t\t\t\t\"domain\": \"vocab:Configuration\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"Profile\",\n" +
                "\t\t\t\"hydra:description\": \"The profile's name\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Connection\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"Connection\",\n" +
                "\t\t\"description\": \"The robot's connection\",\n" +
                "\t\t\"supportedOperation\": [],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:Connection/DateConnection\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"DateConnection\",\n" +
                "\t\t\t\t\"description\": \"The robot's connected date\",\n" +
                "\t\t\t\t\"domain\": \"vocab:Connection\",\n" +
                "\t\t\t\t\"range\": \"http://schema.org/DateTime\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"DateConnection\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's connected date\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:Connection/Protocol\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"Protocol\",\n" +
                "\t\t\t\t\"description\": \"The robot's connecting protocol\",\n" +
                "\t\t\t\t\"domain\": \"vocab:Connection\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"protocol\",\n" +
                "\t\t\t\"hydra:description\": \"The protocol's name\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:Connection/Address\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"Address\",\n" +
                "\t\t\t\t\"description\": \"The robot's connecting address\",\n" +
                "\t\t\t\t\"domain\": \"vocab:Connection\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"Address\",\n" +
                "\t\t\t\"hydra:description\": \"The robot's connecting address\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:MotorSpeed\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"MotorSpeed\",\n" +
                "\t\t\"description\": \"The value of the motor speed.\",\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:MotorSpeed/value\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"speed\",\n" +
                "\t\t\t\t\"description\": \"The speed of a motor\",\n" +
                "\t\t\t\t\"domain\": \"vocab:MotorSpeed\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#int\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"speed\",\n" +
                "\t\t\t\"hydra:description\": \"The speed of a motor\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:MotorAngle\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"MotorAngle\",\n" +
                "\t\t\"description\": \"The value of the motor rotation.\",\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:MotorAngle/value\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"angle\",\n" +
                "\t\t\t\t\"description\": \"The motor's rotation angle\",\n" +
                "\t\t\t\t\"domain\": \"vocab:MotorAngle\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#int\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"angle\",\n" +
                "\t\t\t\"hydra:description\": \"The motor's rotation angle\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Capability\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": null,\n" +
                "\t\t\"label\": \"Capability\",\n" +
                "\t\t\"description\": \"A simple capability.\",\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": \"http://schema.org/url\",\n" +
                "\t\t\t\"hydra:title\": \"accessPort\",\n" +
                "\t\t\t\"hydra:description\": \"The accessPort's name\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"http://www.w3.org/2001/XMLSchema#string\",\n" +
                "\t\t\t\"hydra:title\": \"configuration\",\n" +
                "\t\t\t\"hydra:description\": \"The capability's configuration\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Motor\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"Motor\",\n" +
                "\t\t\"description\": \"The motor of a robot EV3.\",\n" +
                "\t\t\"supportedOperation\": []\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Motor/A\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"Motor\",\n" +
                "\t\t\"description\": \"The motor of a robot EV3.\",\n" +
                "\t\t\"supportedOperation\": [],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": \"vocab:CapabilityBackward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityBackward\",\n" +
                "\t\t\t\"hydra:description\": \"The backward capability of the motor A\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityForward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityForward\",\n" +
                "\t\t\t\"hydra:description\": \"The forward capability of the motor A\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityRotate\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityRotate\",\n" +
                "\t\t\t\"hydra:description\": \"The rotate capability of the motor A\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityStop\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityStop\",\n" +
                "\t\t\t\"hydra:description\": \"The stop capability of the motor A\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Motor/B\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"Motor\",\n" +
                "\t\t\"description\": \"The motor of a robot EV3.\",\n" +
                "\t\t\"supportedOperation\": [],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": \"vocab:CapabilityBackward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityBackward\",\n" +
                "\t\t\t\"hydra:description\": \"The backward capability of the motor B\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityForward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityForward\",\n" +
                "\t\t\t\"hydra:description\": \"The forward capability of the motor B\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityRotate\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityRotate\",\n" +
                "\t\t\t\"hydra:description\": \"The rotate capability of the motor B\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityStop\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityStop\",\n" +
                "\t\t\t\"hydra:description\": \"The stop capability of the motor B\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Motor/C\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"Motor\",\n" +
                "\t\t\"description\": \"The motor of a robot EV3.\",\n" +
                "\t\t\"supportedOperation\": [],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": \"vocab:CapabilityBackward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityBackward\",\n" +
                "\t\t\t\"hydra:description\": \"The backward capability of the motor C\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityForward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityForward\",\n" +
                "\t\t\t\"hydra:description\": \"The forward capability of the motor C\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityRotate\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityRotate\",\n" +
                "\t\t\t\"hydra:description\": \"The rotate capability of the motor C\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityStop\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityStop\",\n" +
                "\t\t\t\"hydra:description\": \"The stop capability of the motor C\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:Motor/D\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"Motor\",\n" +
                "\t\t\"description\": \"The motor of a robot EV3.\",\n" +
                "\t\t\"supportedOperation\": [],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": \"vocab:CapabilityBackward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityBackward\",\n" +
                "\t\t\t\"hydra:description\": \"The backward capability of the motor D\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityForward\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityForward\",\n" +
                "\t\t\t\"hydra:description\": \"The forward capability of the motor D\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityRotate\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityRotate\",\n" +
                "\t\t\t\"hydra:description\": \"The rotate capability of the motor D\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}, {\n" +
                "\t\t\t\"property\": \"vocab:CapabilityStop\",\n" +
                "\t\t\t\"hydra:title\": \"CapabilityStop\",\n" +
                "\t\t\t\"hydra:description\": \"The stop capability of the motor D\",\n" +
                "\t\t\t\"required\": true,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:CapabilityBackward\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"CapabilityBackward\",\n" +
                "\t\t\"description\": \"The backward motor capability.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:backwardMotor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"POST\",\n" +
                "\t\t\t\"label\": \"Move backward the motor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": \"vocab:MotorSpeed\",\n" +
                "\t\t\t\"returns\": null,\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:CapabilityRotate\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"CapabilityRotate\",\n" +
                "\t\t\"description\": \"The rotate motor capability.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:rotateMotor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"POST\",\n" +
                "\t\t\t\"label\": \"Rotate the motor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": [\n" +
                "\t\t\t\t\"vocab:MotorSpeed\",\n" +
                "\t\t\t\t\"vocab:MotorAngle\"\n" +
                "\t\t\t],\n" +
                "\t\t\t\"returns\": null,\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:CapabilityStop\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"CapabilityStop\",\n" +
                "\t\t\"description\": \"The stopping motor capability.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:stopMotor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"Stop the motor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": null,\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:CapabilityForward\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Motor\",\n" +
                "\t\t\"label\": \"CapabilityForward\",\n" +
                "\t\t\"description\": \"The forwarding motor capability.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:forwardMotor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"POST\",\n" +
                "\t\t\t\"label\": \"Move forward the motor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": \"vocab:MotorSpeed\",\n" +
                "\t\t\t\"returns\": null,\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:CapabilityInformationMotor\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Capability\",\n" +
                "\t\t\"label\": \"CapabilityInformationMotor\",\n" +
                "\t\t\"description\": \"The information of a motor.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:informationMotor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"The information of a motor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": \"vocab:MotorValue\",\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:InformationGyroSensor\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Capability\",\n" +
                "\t\t\"label\": \"CapabilityInformationSensor\",\n" +
                "\t\t\"description\": \"The information of a Gyro sensor.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:informationSensor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"The information of a Gyro sensor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": \"vocab:GyroSensorValue\",\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:InformationIRSensor\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": \"Capability\",\n" +
                "\t\t\"label\": \"CapabilityInformationSensor\",\n" +
                "\t\t\"description\": \"The information of a InfraRed sensor.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:informationSensor\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"The information of a InfraRed sensor\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": \"vocab:IRSensorValue\",\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:GyroSensorValue\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"GyroSensorValue\",\n" +
                "\t\t\"description\": \"A Gyro sensor value.\",\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:GyroSensorValue/value\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"value\",\n" +
                "\t\t\t\t\"description\": \"The value of a gyroSensor\",\n" +
                "\t\t\t\t\"domain\": \"vocab:GyroSensorValue\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#int\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"value\",\n" +
                "\t\t\t\"hydra:description\": \"The value of a gyroSensor\",\n" +
                "\t\t\t\"required\": false,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:IRSensorValue\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"label\": \"IRSensorValue\",\n" +
                "\t\t\"description\": \"A InfraRed sensor value.\",\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:IRSensorValue/value\",\n" +
                "\t\t\t\t\"@type\": \"rdf:Property\",\n" +
                "\t\t\t\t\"label\": \"value\",\n" +
                "\t\t\t\t\"description\": \"The value of a InfraRedSensor\",\n" +
                "\t\t\t\t\"domain\": \"vocab:IRSensorValue\",\n" +
                "\t\t\t\t\"range\": \"http://www.w3.org/2001/XMLSchema#int\",\n" +
                "\t\t\t\t\"supportedOperation\": []\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"value\",\n" +
                "\t\t\t\"hydra:description\": \"The value of a InfraRedSensor\",\n" +
                "\t\t\t\"required\": false,\n" +
                "\t\t\t\"readonly\": false,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}, {\n" +
                "\t\t\"@id\": \"vocab:EntryPoint\",\n" +
                "\t\t\"@type\": \"hydra:Class\",\n" +
                "\t\t\"subClassOf\": null,\n" +
                "\t\t\"label\": \"EntryPoint\",\n" +
                "\t\t\"description\": \"The main entry point of the device's API.\",\n" +
                "\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\"@id\": \"_:entry_point\",\n" +
                "\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\"label\": \"The APIs main entry point.\",\n" +
                "\t\t\t\"description\": null,\n" +
                "\t\t\t\"expects\": null,\n" +
                "\t\t\t\"returns\": \"vocab:EntryPoint\",\n" +
                "\t\t\t\"statusCodes\": []\n" +
                "\t\t}],\n" +
                "\t\t\"supportedProperty\": [{\n" +
                "\t\t\t\"property\": {\n" +
                "\t\t\t\t\"@id\": \"vocab:EntryPoint/robotEV3\",\n" +
                "\t\t\t\t\"@type\": \"hydra:Link\",\n" +
                "\t\t\t\t\"label\": \"device\",\n" +
                "\t\t\t\t\"description\": \"The device robot EV3\",\n" +
                "\t\t\t\t\"domain\": \"vocab:EntryPoint\",\n" +
                "\t\t\t\t\"range\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\"supportedOperation\": [{\n" +
                "\t\t\t\t\t\"@id\": \"_:device_retrieve\",\n" +
                "\t\t\t\t\t\"@type\": \"hydra:Operation\",\n" +
                "\t\t\t\t\t\"method\": \"GET\",\n" +
                "\t\t\t\t\t\"label\": \"Retrieves the device\",\n" +
                "\t\t\t\t\t\"description\": null,\n" +
                "\t\t\t\t\t\"expects\": null,\n" +
                "\t\t\t\t\t\"returns\": \"vocab:robotEV3\",\n" +
                "\t\t\t\t\t\"statusCodes\": []\n" +
                "\t\t\t\t}]\n" +
                "\t\t\t},\n" +
                "\t\t\t\"hydra:title\": \"robotEV3\",\n" +
                "\t\t\t\"hydra:description\": \"The device robot EV3\",\n" +
                "\t\t\t\"required\": null,\n" +
                "\t\t\t\"readonly\": true,\n" +
                "\t\t\t\"writeonly\": false\n" +
                "\t\t}]\n" +
                "\t}]\n" +
                "}";
    }

    public static String contextDevice() {
        return "{\n" +
                "  \"@context\": {\n" +
                "    \"hydra\": \"http://www.w3.org/ns/hydra/core#\",\n" +
                "    \"@vocab\": \"" + URI_VOCAB + "#\",\n" +
                "    \"name\": \"vocab:robotEV3/name\",\n" +
                "    \"description\": \"vocab:robotEV3/description\",\n" +
                "    \"id\": \"vocab:robotEV3/id\",\n" +
                "    \"connection\": \"vocab:Connection\",\n" +
                "    \"dateConnection\": \"vocab:Connection/DateConnection\",\n" +
                "    \"protocol\": \"vocab:Connection/Protocol\",\n" +
                "    \"address\": \"vocab:Connection/Address\",\n" +
                "    \"configuration\": \"vocab:Configuration\",\n" +
                "    \"portforwarding\": \"vocab:robotEV3/Portforwarding\",\n" +
                "    \"automaticConfiguration\": \"vocab:Configuration/AutomaticConfiguration\",\n" +
                "    \"profile\": \"vocab:Configuration/Profile\",\n" +
                "    \"capabilities\": \"vocab:robotEV3/capabilities\"\n" +
                "  }\n" +
                "}";
    }

    public static String contextCapabilities() {
        return "{\n" +
                "    \"@context\": {\n" +
                "        \"hydra\": \"http://www.w3.org/ns/hydra/core#\",\n" +
                "        \"@vocab\": \"" + URI_VOCAB + "#\",\n" +
                "        \"id\": \"vocab:id\",\n" +
                "        \"result\": \"vocab:result\",\n" +
                "        \"protocol\": \"vocab:protocol\",\n" +
                "        \"protocolName\": \"vocab:protocolName\",\n" +
                "        \"parameters\": \"vocab:parameters\",\n" +
                "        \"name\": \"vocab:name\",\n" +
                "        \"value\": \"vocab:value\",\n" +
                "        \"cloudPort\": \"vocab:cloudPort\",\n" +
                "        \"params\": \"vocab:params\",\n" +
                "        \"desc\": \"vocab:desc\",\n" +
                "        \"idp\": \"vocab:idp\",\n" +
                "        \"type\": \"vocab:type\",\n" +
                "        \"configuration\": \"vocab:configuration\"\n" +
                "    }\n" +
                "}";
    }

    public static String sensorS1EV3UltrasonicSensorDistance () {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S1-EV3UltrasonicSensor/Distance\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S1/EV3UltrasonicSensor/Distance\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S1-EV3UltrasonicSensor/Distance\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S1/EV3UltrasonicSensor/Distance\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String sensorS1EV3UltrasonicSensorListen () {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S1-EV3UltrasonicSensor/Listen\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S1/EV3UltrasonicSensor/Listen\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S1-EV3UltrasonicSensor/Listen\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S1/EV3UltrasonicSensor/Listen\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String sensorS1EV3UltrasonicSensor () {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S1-EV3UltrasonicSensor\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S1/EV3UltrasonicSensor\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S1-EV3UltrasonicSensor\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S1/EV3UltrasonicSensor\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String sensorS4EV3GyroSensorRate () {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S4-EV3GyroSensor/Rate\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S4/EV3GyroSensor/Rate\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S4-EV3GyroSensor/Rate\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S4/EV3GyroSensor/Rate\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String sensorS4EV3GyroSensorAngle() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S4-EV3GyroSensor/Angle\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S4/EV3GyroSensor/Angle\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S4-EV3GyroSensor/Angle\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S4/EV3GyroSensor/Angle\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String sensorS4EV3GyroSensorAngleRate() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S4-EV3GyroSensor/Angle and Rate\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S4/EV3GyroSensor/Angle and Rate\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S4-EV3GyroSensor/Angle and Rate\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S4/EV3GyroSensor/Angle and Rate\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String sensorS4EV3GyroSensor() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/sensor-S4-EV3GyroSensor\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/S4/EV3GyroSensor\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"sensor-S4-EV3GyroSensor\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"GET\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/S4/EV3GyroSensor\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorARotate() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-A-rotate\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/A/rotate\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-A-rotate\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/A/rotate\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"speed\",\n" +
                "\"type\": \"int\"\n" +
                "},\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"angle\",\n" +
                "\"type\": \"int\"\n" +
                "}\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorAForward() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-A-forward\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/A/forward\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-A-forward\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/A/forward\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"speed\",\n" +
                "\"type\": \"int\"\n" +
                "}\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorABackward() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-A-backward\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/A/backward\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-A-backward\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/A/backward\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"speed\",\n" +
                "\"type\": \"int\"\n" +
                "}\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorAStop() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-A-stop\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/A/stop\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-A-stop\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/A/stop\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorDRotate() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-D-rotate\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/D/rotate\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-D-rotate\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/D/rotate\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"speed\",\n" +
                "\"type\": \"int\"\n" +
                "},\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"angle\",\n" +
                "\"type\": \"int\"\n" +
                "}\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorDForward() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-D-forward\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/D/forward\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-D-forward\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/D/forward\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"speed\",\n" +
                "\"type\": \"int\"\n" +
                "}\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorDBackward() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-D-backward\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/D/backward\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-D-backward\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/D/backward\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "{\n" +
                "\"desc\": \"null\",\n" +
                "\"idp\": \"speed\",\n" +
                "\"type\": \"int\"\n" +
                "}\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }

    public static String motorDStop() {
        return "{\n" +
                "  \"@context\": \"127.0.0.1:8080/om2m/nscl/applications/CIMA/administration/contextCapabilities\",\n" +
                "  \"@id\": \"127.0.0.1:8080/motor-D-stop\",\n" +
                "  \"@type\": \"vocab:Capability\",\n" +
                "  \"protocol\": \"HTTP\",\n" +
                "  \"access\": \n" +
                "  {\n" +
                "    \"standard\": \"127.0.0.1:8080/device/1234567\",\n" +
                "    \"direct\": \"127.0.0.1:null/D/stop\"\n" +
                "  },\n" +
                "  \"parameters\":   [\n" +
                "    {\n" +
                "      \"name\": \"port\",\n" +
                "      \"value\": \"8080\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"body\",\n" +
                "      \"value\": \"motor-D-stop\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"method\",\n" +
                "      \"value\": \"POST\"\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"uri\",\n" +
                "      \"value\": \"/D/stop\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"params\": [\n" +
                "  ],\n" +
                "  \"configuration\": \"automatic\"\n" +
                "}";
    }
}
