package demo.web.controller;

import akka.actor.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.web.model.Message;
import demo.web.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
@Path("/stateless-async-non-blocking")
public class StatelessAsyncController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    @Qualifier("actorBackend")
    private ActorRef backendActor;

    @Autowired
    @Qualifier("actorCallback")
    private ActorRef callbackActor;

    @POST
    @Path("/{id}")
    @Consumes({"application/json"})
    public void postStatelessAsyncNonBlockingMessage(@Suspended AsyncResponse asyncResponse, //
            @Context HttpHeaders headers, //
            @Context SecurityContext securityContext, //
            @Context UriInfo uriInfo, //
            @PathParam("id") Long id, //
            String body) throws JsonProcessingException {

        Request request = objectMapper.readValue(body, Request.class);
        Message.SetStatelessMessage message = new Message.SetStatelessMessage(asyncResponse, id, request);

        backendActor.tell(message, callbackActor);
    }

    @GET
    @Path("/{id}")
    @Consumes({"application/json"})
    public void getStatelessAsyncNonBlockingMessage(@Suspended AsyncResponse asyncResponse, //
            @Context HttpHeaders headers, //
            @Context SecurityContext securityContext, //
            @Context UriInfo uriInfo,
            @PathParam("id") Long id) {

        Message.GetStatelessMessage message = new Message.GetStatelessMessage(asyncResponse, id);

        backendActor.tell(message, callbackActor);
    }
}
