package com.thoughtmechanix.licenses.events.consumers;

import com.thoughtmechanix.licenses.events.models.OrganizationChangeModel;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class OrganizationChangeConsumer {
  private static final Logger logger = LoggerFactory.getLogger(OrganizationChangeConsumer.class);

  @Bean
  public Consumer<OrganizationChangeModel> input() {
    return orgChange -> {
      logger.debug("Received an event for organization id {}", orgChange.getOrganizationId());
    };
  }
}
