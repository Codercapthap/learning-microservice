package com.thoughtmechanix.zuulsvr.filters;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.logging.Filter;

@Component
public class TrackingFilter implements GlobalFilter, Ordered {

  private static final Logger logger = LoggerFactory.getLogger(TrackingFilter.class);

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    ServerHttpRequest request = exchange.getRequest();

    // Check for correlation ID in the headers
    if (isCorrelationIdPresent(request)) {
      logger.debug("tmx-correlation-id found in tracking filter: {}", getCorrelationId(request));
    } else {
      // Generate a new correlation ID if not found
      String correlationId = generateCorrelationId();
      exchange = setCorrelationId(exchange, correlationId);
      logger.debug("tmx-correlation-id generated in tracking filter: {}", correlationId);
    }

    logger.debug("Processing incoming request for {}", request.getURI());
    return chain.filter(exchange);
  }

  private boolean isCorrelationIdPresent(ServerHttpRequest request) {
    return request.getHeaders().getFirst(FilterUtils.CORRELATION_ID) != null;
  }

  private String getCorrelationId(ServerHttpRequest request) {
    return request.getHeaders().getFirst(FilterUtils.CORRELATION_ID);
  }

  private String generateCorrelationId() {
    return UUID.randomUUID().toString();
  }

  private ServerWebExchange setCorrelationId(ServerWebExchange exchange, String correlationId) {
    ServerHttpRequest request = exchange.getRequest().mutate()
            .header(FilterUtils.CORRELATION_ID, correlationId)
            .build();
    return exchange.mutate().request(request).build();
  }

  @Override
  public int getOrder() {
    return 1;
  }
}
