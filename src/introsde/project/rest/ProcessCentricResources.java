package introsde.project.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class ProcessCentricResources {
	
	ProcessCentricModel pcModel = new ProcessCentricModel();
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/persons")
    public Response getPersonList() {
        Response persons = pcModel.getAllPersons();
        return persons;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persons/{personId}")
    public Response getPerson(@PathParam("personId") int idPerson) {
    	Response person = pcModel.getPersonById(idPerson);
        return person;
    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response newPerson(String person){           
        Response result = pcModel.addNewPerson(person);
        return result;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON )
    @Path("/persons/{personId}")
    public Response updatePerson(String person, @PathParam("personId") int idPerson) {
    	Response result = pcModel.updatePerson(idPerson, person);
    	return result;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persons/{personId}/{measureType}")
    public Response newMeasure(String measure, @PathParam("personId") int idPerson, @PathParam("measureType") String measureType) throws IOException, ParseException {
        Response result = pcModel.addNewMeasureCheckGoal(measure, idPerson, measureType);
        return result;
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persons/{personId}/goals")
    public Response newGoal(String goal, @PathParam("personId") int idPerson) {
        Response result = pcModel.addNewGoal(goal, idPerson);
        return result;
    }
    
	
}