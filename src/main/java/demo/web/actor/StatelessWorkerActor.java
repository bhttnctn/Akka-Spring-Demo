package demo.web.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import demo.web.model.Message;
import demo.web.model.Response;
import demo.web.spring.ActorComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

@ActorComponent("statelessWorkerActor")
@Scope("prototype")
public class StatelessWorkerActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ApplicationContext applicationContext;
    private String actorName;

    public StatelessWorkerActor(ApplicationContext applicationContext, String actorName) {
        this.applicationContext = applicationContext;
        this.actorName = actorName;
    }

    @Override
    public void preStart() {
        logger.info("StatelessWorkerActor actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        if (message instanceof Message.GetStatelessMessage) {
            Message.GetStatelessMessage statelessMessage = (Message.GetStatelessMessage) message;
            Response response = new Response(statelessMessage.getAsyncResponse(), statelessMessage.getMessageId(),
                    "GET method - Stateless actor does not hold any message !!!", 1);
            sender().tell(response, self());
        } else if (message instanceof Message.SetStatelessMessage) {
            Message.SetStatelessMessage statelessMessage = (Message.SetStatelessMessage) message;
            Response response = new Response(statelessMessage.getAsyncResponse(), statelessMessage.getMessageId(),
                    "POST method - Stateless actor does not hold any message !!!", 1);
            sender().tell(response, self());
        } else {
            unhandled(message);
        }
        self().tell(PoisonPill.getInstance(), ActorRef.noSender());
    }
}
