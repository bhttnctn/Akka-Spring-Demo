package demo.web;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.FromConfig;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import demo.web.spring.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
class ApplicationConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SpringExtension springExtension;

    ActorSystem actorSystem;

    @Primary
    @Bean
    public Config akkaConfiguration() {
        return ConfigFactory.load();
    }

    @Primary
    @Bean(destroyMethod = "shutdown")
    public ActorSystem actorSystem() {
        actorSystem = ActorSystem.create("demo-actor-system", akkaConfiguration());
        springExtension.initialize(applicationContext);
        return actorSystem;
    }

    @Bean(name = "actorBackend")
    public ActorRef actorBackend() {
        Props actorBackendProp = FromConfig.getInstance()
                .props(springExtension.props("backendActor"));
        return actorSystem.actorOf(actorBackendProp, "backendActor");
    }

    @Bean(name = "actorStateful")
    public ActorRef actorStateful() {
        Props actorStatefulProp = FromConfig.getInstance()
                .props(springExtension.props("statefulActor"));
        return actorSystem.actorOf(actorStatefulProp, "statefulActor");
    }

    @Bean(name = "actorStateless")
    public ActorRef actorStateless() {
        Props actorStatelessProp = FromConfig.getInstance()
                .props(springExtension.props("statelessActor"));
        return actorSystem.actorOf(actorStatelessProp, "statelessActor");
    }

    @Bean(name = "actorCallback")
    public ActorRef actorCallback() {
        Props asyncCallbackProp = FromConfig.getInstance()
                .props(springExtension.props("callbackActor"));
        return actorSystem.actorOf(asyncCallbackProp, "callbackActor");
    }
}