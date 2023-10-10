package demo.web.actor;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import demo.web.model.Message;
import demo.web.spring.ActorComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

@ActorComponent("backendActor")
@Scope("prototype")
public class BackendActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("actorStateful")
    private ActorRef statefulActor;

    @Autowired
    @Qualifier("actorStateless")
    private ActorRef statelessActor;

    @Override
    public void preStart() {
        logger.info("BackendActor actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        if (message instanceof Message.StatefulMessage) {
            statefulActor.forward(message, getContext());
        } else if (message instanceof Message.StatelessMessage) {
            statelessActor.forward(message, getContext());
        } else {
            unhandled(message);
        }
    }
}
