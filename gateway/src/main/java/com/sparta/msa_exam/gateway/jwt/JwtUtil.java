package com.sparta.msa_exam.gateway.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.net.URLDecoder;
import java.util.Base64;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtUtil {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  public static final String BEARER_PREFIX = "Bearer ";

  @Value("${spring.application.name}")
  private String issuer;

  @Value("${service.jwt.access-expiration}")
  private Long TOKEN_TIME;

  @Value("${service.jwt.secret-key}")
  private String secretKey;
  private SecretKey key;

  private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

  @PostConstruct
  public void init() {
    byte[] bytes = Base64.getDecoder().decode(secretKey);
    key = Keys.hmacShaKeyFor(bytes);
  }


  public String getTokenFromRequest(ServerHttpRequest request) {
    HttpCookie cookie = request.getCookies().getFirst(AUTHORIZATION_HEADER);
    try {
      return URLDecoder.decode(cookie.getValue(), "UTF-8");
    } catch (Exception e) {
      return null;
    }
  }

  public String substringToken(String bearerToken) {
    if (bearerToken.startsWith(BEARER_PREFIX)) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public boolean validateToken(String tokenValue) {
    try {
      Jwts.parser().verifyWith(key).build().parseSignedClaims(tokenValue);
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public Claims getUserInfoFromToken(String tokenValue) {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(tokenValue).getPayload();
  }
}
