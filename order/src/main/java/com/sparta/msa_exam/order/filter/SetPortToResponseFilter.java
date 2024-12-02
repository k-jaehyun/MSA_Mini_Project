package com.sparta.msa_exam.order.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@WebFilter(value = "/*", asyncSupported = true) // 모든 요청에 대해 필터를 적용 + 비동기적으로 필터 실행
public class SetPortToResponseFilter implements Filter {

  @Value("${server.port}")
  private String serverPort;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader("Server-Port", serverPort); // 헤더에 서버 포트 추가

    chain.doFilter(request, response);
  }

}
