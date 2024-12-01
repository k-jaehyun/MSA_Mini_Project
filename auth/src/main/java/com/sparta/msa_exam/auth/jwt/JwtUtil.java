package com.sparta.msa_exam.auth.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

  public String createToken(String username) {
    Date date = new Date();

    return BEARER_PREFIX +
        Jwts.builder()
            .subject(username)
            .issuer(issuer)
            .expiration(new Date(date.getTime() + TOKEN_TIME))
            .issuedAt(date)
            .signWith(key, signatureAlgorithm)
            .compact();
  }

  public void addJwtToCookie(String token, HttpServletResponse res) {
    try {
      token = URLEncoder.encode(token, "utf-8")
          .replaceAll("\\+", "%20");

      Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
      cookie.setPath("/");

      res.addCookie(cookie);
    } catch (UnsupportedEncodingException e) {
      System.out.println(e.getMessage());
    }
  }

}
