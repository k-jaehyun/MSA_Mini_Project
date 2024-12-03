package com.sparta.msa_exam.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RefreshScope
@Component
public class WeightRoutingFilterFactory implements GatewayFilterFactory<WeightRoutingFilterFactory.Config> {

  @Value("${product-service.1.port}")
  private String productServicePort_1;

  @Value("${product-service.2.port}")
  private String productServicePort_2;

  @Override
  public GatewayFilter apply(Config config) {
    return new WeightRoutingFilter(config);
  }

  @Override
  public Class<Config> getConfigClass() {
    return Config.class;
  }

  public static class Config {
    // 필요하다면 여기에 필터에서 사용할 속성을 정의할 수 있음. (현재는 제거해도 상관x)
  }

  public class WeightRoutingFilter implements GatewayFilter {
    private final Config config;

    public WeightRoutingFilter(Config config) {
      this.config = config;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
      log.info("####################WeightRoutingFilter##################");

      // uri 분배 로직
      String targetUri = determineTargetUri();

      // 기존 요청 URI을 새로운 URI로 설정
      URI currentUri = exchange.getRequest().getURI();
      String path = currentUri.getPath();  // 기존 경로
      String query = currentUri.getQuery();  // 기존 쿼리 파라미터도

      // targetUri에 기존 경로와 쿼리를 덧붙임
      URI newUri = URI.create(targetUri + path + (query != null ? "?" + query : ""));
      log.info("newUri: "+ newUri);

      // 새로 수정된 URI로 ServerWebExchange 객체를 재구성 (이 때 ServerWebExchange로 바로 cast하면 안됨)
      ServerHttpRequest newRequest = exchange.getRequest().mutate().uri(newUri).build();
      ServerWebExchange newExchange = exchange.mutate().request(newRequest).build();

      return chain.filter(newExchange);
    }

    private String determineTargetUri() {
      double random = Math.random();
      return (random < 0.7) ? "http://localhost:"+productServicePort_1 : "http://localhost:"+productServicePort_2;
    }
  }
}

