package com.thoughtmechanix.licenses.events.consumers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtmechanix.licenses.events.models.OrganizationChangeModel;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class OrganizationChangeConsumer{
  private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeConsumer.class);

  private final ObjectMapper objectMapper;

  public OrganizationChangeConsumer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Bean
  public Consumer<Message<byte[]>> consume() {
    return message -> {
      try {
        // Deserialize the byte[] payload into OrganizationChangeModel
        OrganizationChangeModel orgChange = objectMapper.readValue(message.getPayload(), OrganizationChangeModel.class);

        // Handle the deserialized object (log it, process it, etc.)
        System.out.println("Received Organization Change Event: " + orgChange.getOrganizationId());
        // Further processing...
        logger.debug("Received an event for organization id {}", orgChange.getOrganizationId());
      } catch (Exception e) {
        throw new RuntimeException("Failed to deserialize message payload", e);
      }
    };
  }
}
