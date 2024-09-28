package com.thoughtmechanix.zuulsvr.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ResponseFilter implements GlobalFilter, Ordered {
  private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return chain.filter(exchange).then(Mono.fromRunnable(() -> {
      String correlationId = exchange.getRequest().getHeaders().getFirst(FilterUtils.CORRELATION_ID);
      if (correlationId != null) {
        logger.debug("Adding the correlation id to the outbound headers. {}", correlationId);
        exchange.getResponse().getHeaders().add(FilterUtils.CORRELATION_ID, correlationId);
      } else {
        logger.warn("No correlation ID found in request headers for {}", exchange.getRequest().getURI());
      }
    }));
  }

  @Override
  public int getOrder() {
    return 2;
  }
}
