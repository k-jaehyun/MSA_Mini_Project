package com.sparta.msa_exam.gateway.filter;

import com.sparta.msa_exam.gateway.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements GlobalFilter, Ordered {

  private final JwtUtil jwtUtil;

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    // 로그인, 회원가입 경로 예외
    String path = exchange.getRequest().getURI().getPath();
    if (path.equals("/auth/sign-in") || path.equals("/auth/sign-up")) {
      return chain.filter(exchange);
    }

    String bearerToken = jwtUtil.getTokenFromRequest(exchange.getRequest());

    if (StringUtils.hasText(bearerToken)) {

      String tokenValue = jwtUtil.substringToken(bearerToken);

      if (tokenValue == null || !jwtUtil.validateToken(tokenValue)) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
      }

      // 헤더에 사용자 정보 추가
      Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
      String username = info.getSubject();

      ServerHttpRequest mofiedRequest = exchange.getRequest()
          .mutate()
          .header("username", username)
          .header("issuer", info.getIssuer())
          .header("issuedAt", info.getIssuedAt().toString())
          .build();

      exchange = exchange.mutate().request(mofiedRequest).build();
    } else {
      exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
      return exchange.getResponse().setComplete();
    }

    return chain.filter(exchange);
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
