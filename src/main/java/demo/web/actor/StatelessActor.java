package demo.web.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.FromConfig;
import demo.web.model.Message;
import demo.web.spring.ActorComponent;
import demo.web.spring.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@ActorComponent("statelessActor")
@Scope("prototype")
public class StatelessActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringExtension springExtension;

    @Autowired
    private ActorSystem actorSystem;

    @Override
    public void preStart() {
        logger.info("StatelessActor actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        Message.StatelessMessage statelessMessage = (Message.StatelessMessage) message;
        Long messageId = statelessMessage.getMessageId();

        Props actorWorkerProp = FromConfig.getInstance()
                .props(springExtension.props(StatelessWorkerActor.class, "statelessWorkerActor"))
                .withDispatcher(context().props().dispatcher())
                .withRouter(context().props().routerConfig());
        ActorRef statelessWorkerActor = actorSystem.actorOf(actorWorkerProp, "statelessWorkerActor" + messageId);

        statelessWorkerActor.forward(statelessMessage, getContext());
    }
}
