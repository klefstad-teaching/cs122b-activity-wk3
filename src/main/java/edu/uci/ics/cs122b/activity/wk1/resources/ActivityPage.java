package edu.uci.ics.cs122b.activity.wk1.resources;


import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uci.ics.cs122b.activity.wk1.logger.ServiceLogger;
import edu.uci.ics.cs122b.activity.wk1.models.ExampleRequestModel;
import edu.uci.ics.cs122b.activity.wk1.models.ExampleResponseModel;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.*;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


@Path("example") // Outer path
public class ActivityPage {
    @Path("get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    // Example endpoint to communicate with another microservice
    // Also demonstrates use of headers and Query Parameters

    // Path to this endpoint will look something like: http://localhost:port/activity/example/get?x=100
    public Response exampleRestCall(@Context HttpHeaders headers, @QueryParam("x") Integer x) {

        // Set the path of the endpoint we want to communicate with
        String servicePath = "http://localhost:12345/othermicroservice";
        String endpointPath = "/some/endpoint";

        // Declare models
        ExampleRequestModel requestModel = new ExampleRequestModel(1, 2);
        ExampleResponseModel responseModel = null;

        // Get header strings
        // If there is no header with given key, it will be null
        String email = headers.getHeaderString("email");
        String session_id = headers.getHeaderString("session_id");
        String transaction_id = headers.getHeaderString("transaction_id");

        // Create a new Client
        ServiceLogger.LOGGER.info("Building client...");
        Client client = ClientBuilder.newClient();
        client.register(JacksonFeature.class);

        // Create a WebTarget to send a request at
        ServiceLogger.LOGGER.info("Building WebTarget...");
        WebTarget webTarget = client.target(servicePath).path(endpointPath);

        // Create an InvocationBuilder to create the HTTP request
        ServiceLogger.LOGGER.info("Starting invocation builder...");
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

        // Send the request and save it to a Response
        ServiceLogger.LOGGER.info("Sending request...");
        Response response = invocationBuilder.post(Entity.entity(requestModel, MediaType.APPLICATION_JSON));
        ServiceLogger.LOGGER.info("Request sent.");

        ServiceLogger.LOGGER.info("Received status " + response.getStatus());
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonText = response.readEntity(String.class);
            responseModel = mapper.readValue(jsonText, ExampleResponseModel.class);
            ServiceLogger.LOGGER.info("Successfully mapped response to POJO.");
        } catch (IOException e) {
            ServiceLogger.LOGGER.warning("Unable to map response to POJO.");
        }

        // Do work with data contained in response model


        // Return a response with same headers
        Response.ResponseBuilder builder;
        if (responseModel == null)
            builder = Response.status(Response.Status.BAD_REQUEST);
        else
            builder = Response.status(Response.Status.OK).entity(responseModel);

        // Pass along headers
        builder.header("email", email);
        builder.header("session_id", session_id);
        builder.header("transaction_id", transaction_id);

        // Return the response
        return builder.build();
    }

}
