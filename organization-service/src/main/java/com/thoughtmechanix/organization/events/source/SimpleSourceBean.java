package com.thoughtmechanix.organization.events.source;

import com.thoughtmechanix.organization.events.models.OrganizationChangeModel;
import com.thoughtmechanix.organization.utils.UserContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;

@Component
public class SimpleSourceBean {
  private static final Logger logger = LoggerFactory.getLogger(SimpleSourceBean.class);
  private final StreamBridge streamBridge;

  public SimpleSourceBean(StreamBridge streamBridge) {
    this.streamBridge = streamBridge;
  }

  public void publishOrgChange(String action, String orgId) {
    logger.debug("Sending Kafka message {} for Organization Id: {}", action, orgId);
    OrganizationChangeModel change = new OrganizationChangeModel(
        OrganizationChangeModel.class.getTypeName(),
        action,
        orgId,
        UserContextHolder.getContext().getCorrelationId());

    streamBridge.send("output", change);  // "outputChannel" should match the binding name
  }
}
