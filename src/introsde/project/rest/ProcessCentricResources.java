package introsde.project.rest;

import javax.ejb.*;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/")
public class ProcessCentricResources {
	
	ProcessCentricModel pcModel = new ProcessCentricModel();
	
	
	//Getting the list of person in the LocalDatabase Service
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/persons")
    public Response getPersonList() {
        Response persons = pcModel.getAllPersons();
        return persons;
    }
    
    //Getting the list of person in the LocalDatabase Service
    
    //Getting the person information in the LocalDatabase Service
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/persons/{personId}")
    public Response getPerson(@PathParam("personId") int idPerson) {
    	Response person = pcModel.getPersonById(idPerson);
        return person;
    }
	
}