package introsde.project.rest;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.json.JSONObject;


public class ProcessCentricModel {
	
	// Configure client
    ClientConfig clientConfig = new ClientConfig();
    Client client = ClientBuilder.newClient(clientConfig);
    WebTarget storageService = client.target(getStorageURI());
    WebTarget businessLogicService = client.target(getBusinessLogicURI());
    String acceptType = "application/json";
	
    public Response getAllPersons() {
    	Response result = storageService.path("/persons").request().accept(acceptType).get();
    	return result;
    }
    
    public Response getPersonById(int personId) {
    	Response result = storageService.path("/persons/"+personId).request().accept(acceptType).get();
    	return result;
    }
    
    public Response addNewPerson(String personJson) {
    	Response result = storageService.path("/persons/").request().accept(acceptType).post(Entity.json(personJson));
    	return result;
    }
    
    public Response updatePerson(int personId, String personJson) {
    	Response result = storageService.path("/persons/"+personId).request().accept(acceptType).put(Entity.json(personJson));
    	return result;
    }
    
    public Response addNewMeasureCheckGoal(String measureJson, int personId, String measureType) {
    	Response measure = storageService.path("/persons/"+personId+"/"+measureType).request().accept(acceptType).post(Entity.json(measureJson));
    	String jsonString = measure.readEntity(String.class);
    	JSONObject jsonObj = new JSONObject(jsonString);
    	int measureId = jsonObj.getInt("mid");
    	Response result = businessLogicService.path("/persons/"+personId+"/"+measureType+"/"+measureId+"/checkgoal").request().accept(acceptType).get();
    	return result;
    }
    
    public Response addNewGoal(String goalJson, int personId) {
    	Response result = storageService.path("/persons/"+personId+"/goals").request().accept(acceptType).post(Entity.json(goalJson));
    	return result;
    }
    

    private static URI getStorageURI() {
        return UriBuilder.fromUri(
                "http://localhost:5700/").build();
    }

    private static URI getBusinessLogicURI() {
        return UriBuilder.fromUri(
                "http://localhost:4900/").build();
    }
   

}
