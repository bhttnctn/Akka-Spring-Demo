package demo.web.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import demo.web.model.Message;
import demo.web.model.Response;
import demo.web.spring.ActorComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

@ActorComponent("statefulWorkerActor")
@Scope("prototype")
public class StatefulWorkerActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private String actorName;
    private String message;
    private int messageCounter = 0;

    @Autowired
    @Qualifier("actorStateful")
    private ActorRef statefulActor;

    public StatefulWorkerActor(ApplicationContext applicationContext, String actorName) {
        this.applicationContext = applicationContext;
        this.actorName = actorName;
    }

    @Override
    public void preStart() {
        logger.info("StatefulWorkerActor actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        if (message instanceof Message.GetStatefulMessage) {
            Message.GetStatefulMessage statefulMessage = (Message.GetStatefulMessage) message;
            Response response = new Response(statefulMessage.getAsyncResponse(), statefulMessage.getMessageId(),
                    this.message, ++messageCounter);
            sender().tell(response, self());
        } else if (message instanceof Message.SetStatefulMessage) {
            Message.SetStatefulMessage statefulMessage = (Message.SetStatefulMessage) message;
            this.message = statefulMessage.getMessage().getPayload();
            Response response = new Response(statefulMessage.getAsyncResponse(), statefulMessage.getMessageId(),
                    this.message, ++messageCounter);
            sender().tell(response, self());
        } else if (message instanceof Message.DeleteStatefulMessage) {
            Message.DeleteStatefulMessage statefulMessage = (Message.DeleteStatefulMessage) message;
            Response response = new Response(statefulMessage.getAsyncResponse(), statefulMessage.getMessageId(),
                    "Session deleted...", ++messageCounter);
            sender().tell(response, self());

            self().tell(PoisonPill.getInstance(), ActorRef.noSender());
        } else {
            unhandled(message);
        }
    }
}
