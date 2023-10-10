package demo.web.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.FromConfig;
import demo.web.model.Message;
import demo.web.service.SessionManager;
import demo.web.spring.ActorComponent;
import demo.web.spring.SpringExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@ActorComponent("statefulActor")
@Scope("prototype")
public class StatefulActor extends UntypedActor {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SpringExtension springExtension;

    @Autowired
    private ActorSystem actorSystem;

    @Autowired
    private SessionManager manager;

    @Override
    public void preStart() {
        logger.info("StatefulActor actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        if (message instanceof Message.StatefulMessage) {

            Message.StatefulMessage statefulMessage = (Message.StatefulMessage) message;
            Long messageId = statefulMessage.getMessageId();

            ActorRef statefulWorkerActor;
            if (manager.getActor(messageId) == null) {
                Props actorWorkerProp = FromConfig.getInstance()
                        .props(springExtension.props(StatefulWorkerActor.class, "statefulWorkerActor"))
                        .withDispatcher(context().props().dispatcher())
                        .withRouter(context().props().routerConfig());
                statefulWorkerActor = actorSystem.actorOf(actorWorkerProp,
                        "statefulWorkerActor" + messageId);

                manager.putActor(messageId, statefulWorkerActor);
            } else {
                statefulWorkerActor = manager.getActor(messageId);
            }
            statefulWorkerActor.forward(statefulMessage, getContext());
        } else {
            unhandled(message);
        }
    }
}
