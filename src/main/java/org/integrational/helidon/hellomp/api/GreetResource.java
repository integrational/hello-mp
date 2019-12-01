package org.integrational.helidon.hellomp.api;

import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * A simple JAX-RS resource to greet you. Examples:
 * <p>
 * Get default greeting message:
 * curl -X GET http://localhost:8080/greet
 * <p>
 * Get greeting message for Joe:
 * curl -X GET http://localhost:8080/greet/Joe
 * <p>
 * Change greeting
 * curl -X PUT -H "Content-Type: application/json" -d '{"greeting" : "Howdy"}' http://localhost:8080/greet/greeting
 * <p>
 * The message is returned as a JSON object.
 */
@Path("/greet")
public interface GreetResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getDefaultMessage();

    @Path("/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getMessage(@PathParam("name") String name);

    @Path("/greeting")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateGreeting(JsonObject jsonObject);
}
