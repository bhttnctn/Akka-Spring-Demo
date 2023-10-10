package demo.web.service;

import akka.actor.ActorRef;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SessionManager {

    private Map<Long, ActorRef> actor = new HashMap<>();

    public ActorRef getActor(Long actorName) {
        return this.actor.get(actorName);
    }

    public void putActor(Long actorName, ActorRef actor) {
        this.actor.put(actorName, actor);
    }
}
