package demo.web.service;

import demo.web.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BusinessService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Request perform(String actorName, Request message) {
        logger.info("Actor: {} perform message: {}", actorName, message.getMessage());

        message.getMessage().setPayload("Message processed successfully...");
        message.getMessage().setId(message.getMessage().getId() + 1);

        return message;
    }
}
