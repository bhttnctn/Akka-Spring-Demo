package demo.web.actor;

import akka.actor.UntypedActor;
import demo.web.model.Request;
import demo.web.service.BusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("workerActor")
@Scope("prototype")
public class WorkerActor extends UntypedActor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BusinessService businessService;

    @Override
    public void preStart() {
        logger.info("WorkerActor actor initialized...");
    }

    @Override
    public void onReceive(Object message) {

        if (message instanceof Request) {
            Request response = businessService.perform(self().path().toStringWithoutAddress(), (Request) message);
            sender().tell(response.getAsyncResponse(), self());
        } else {
            unhandled(message);
        }
    }
}
