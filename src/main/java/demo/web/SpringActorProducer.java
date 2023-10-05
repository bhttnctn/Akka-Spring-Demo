package demo.web;

import akka.actor.Actor;
import akka.actor.IndirectActorProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

public class SpringActorProducer implements IndirectActorProducer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ApplicationContext applicationContext;
    private final String actorBeanName;

    public SpringActorProducer(ApplicationContext applicationContext, String actorBeanName) {
        this.applicationContext = applicationContext;
        this.actorBeanName = actorBeanName;
    }

    @Override
    public Actor produce() {
        return (Actor) applicationContext.getBean(actorBeanName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends Actor> actorClass() {
        return (Class<? extends Actor>) applicationContext.getType(actorBeanName);
    }
}