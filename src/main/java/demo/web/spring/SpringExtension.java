package demo.web.spring;

import akka.actor.Extension;
import akka.actor.Props;
import demo.web.SpringActorProducer;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SpringExtension implements Extension {

    private ApplicationContext applicationContext;

    public void initialize(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Props props(String actorBeanName) {
        return Props.create(SpringActorProducer.class, applicationContext, actorBeanName);
    }

    public Props props(Class<?> actorClass, String actorBeanName) {
        return Props.create(actorClass, applicationContext, actorBeanName);
    }
}
