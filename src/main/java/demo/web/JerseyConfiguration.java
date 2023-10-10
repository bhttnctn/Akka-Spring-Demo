package demo.web;

import demo.web.controller.StatefulAsyncController;
import demo.web.controller.StatelessAsyncController;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class JerseyConfiguration extends ResourceConfig {

    public JerseyConfiguration() {
        register(StatefulAsyncController.class);
        register(StatelessAsyncController.class);
    }
}
