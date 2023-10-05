package demo.web.controller;

import akka.actor.ActorRef;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.web.model.Message;
import demo.web.model.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

@Provider
@Path("/stateful-async-non-blocking")
public class StatefulAsyncController {

    @Autowired
    @Qualifier("actorBackend")
    private ActorRef workerActor;

    @Autowired
    @Qualifier("actorCallback")
    private ActorRef callbackActor;

    @POST
    @Consumes({"application/json"})
    public void postStatefulAsyncNonBlocking(@Suspended AsyncResponse asyncResponse, //
            @Context HttpHeaders headers, //
            @Context SecurityContext securityContext, //
            @Context UriInfo uriInfo, //
            String body) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        Message message = objectMapper.readValue(body, Message.class);

        Request request = new Request(asyncResponse, message);

        workerActor.tell(request, callbackActor);
    }
}
