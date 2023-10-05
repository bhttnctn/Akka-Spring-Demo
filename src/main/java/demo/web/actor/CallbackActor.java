package demo.web.actor;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.container.AsyncResponse;

@Component("callbackActor")
@Scope("prototype")
public class CallbackActor extends UntypedActor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void preStart() {
        logger.info("Callback actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        if (message instanceof AsyncResponse) {
            ((AsyncResponse) message).resume("Message processed successfully...");
        } else {
            unhandled(message);
        }
    }
}
