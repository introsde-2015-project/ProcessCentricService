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
    	String measureString = measure.readEntity(String.class);
    	JSONObject measureObj = new JSONObject(measureString);
    	int measureId = measureObj.getInt("mid");
    	
    	Response timeline = businessLogicService.path("/persons/"+personId+"/"+measureType+"/"+measureId+"/checkgoal").request().accept(acceptType).get();
    	String timelineString = timeline.readEntity(String.class);
    	if (timelineString == null || timelineString.isEmpty()) {
    		return Response.ok(measureObj.toString()).build();
    	}
    	
    	timelineString = timelineString.replace("'", "");
    	timelineString = timelineString.replace("\"","'");
  
    	String timelineJson = "{" + "\"JSONString\": \""+timelineString+"\"}";
    	Response result = storageService.path("/persons/"+personId+"/timelines").request().accept(acceptType).post(Entity.json(timelineJson));
    	
    	return result;
    }
    
    public Response addNewGoal(String goalJson, int personId) {
    	Response result = storageService.path("/persons/"+personId+"/goals").request().accept(acceptType).post(Entity.json(goalJson));
    	return result;
    }
    
    public Response updateGoal(String goal, int personId, int goalId) {
    	Response result = storageService.path("/persons/"+personId+"/goals/"+goalId).request().accept(acceptType).put(Entity.json(goal));
    	return result;
    }
    
    public Response addNewTimeline(String timelineJson, int personId) {
    	Response result = storageService.path("/persons/"+personId+"/timeline").request().accept(acceptType).post(Entity.json(timelineJson));
    	return result;
    }

    private static URI getStorageURI() {
        return UriBuilder.fromUri(
                "https://storage-introsde.herokuapp.com/").build();
    }

    private static URI getBusinessLogicURI() {
        return UriBuilder.fromUri(
                "https://businesslogic-introsde.herokuapp.com/").build();
    }
   

}
