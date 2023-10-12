package demo.web.actor;

import akka.actor.UntypedActor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.web.model.Response;
import demo.web.spring.ActorComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

@ActorComponent("callbackActor")
@Scope("prototype")
public class CallbackActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void preStart() {
        logger.info("Callback actor initialized...");
    }

    @Override
    public void onReceive(Object message) throws JsonProcessingException {

        final javax.ws.rs.core.Response.ResponseBuilder responseBuilder;

        if (message instanceof Response) {

            Response response = (Response) message;
            responseBuilder = javax.ws.rs.core.Response.status(200)
                    .entity(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(response));
            responseBuilder.header("Content-Type", "application/json");

            response.getAsyncResponse().resume(responseBuilder.build());
        } else {
            unhandled(message);
        }
    }
}
