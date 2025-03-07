package com.zerobase.SelfWash.config.security.filter;

import com.zerobase.SelfWash.config.security.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberFilter extends OncePerRequestFilter {

  private final JwtProvider jwtProvider;

  public static final String TOKEN_HEADER = "Authorization";
  public static final String TOKEN_PREFIX = "Bearer ";

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String requestURI = request.getRequestURI();

    if (requestURI.startsWith("/signup/")
        || requestURI.startsWith("/signin/")
        || requestURI.startsWith("/swagger-ui/")
        || requestURI.startsWith("/v3/api-docs"))
    {
      filterChain.doFilter(request, response);
      return;
    }

    String token = tokenFromRequest(request);

    log.info("{} : {}", request, token);

    if (!jwtProvider.validateToken(token)) {
      log.info("유효하지 않은 토큰 값{}", token);
      throw new ServletException("Invalid token");
    }

    Authentication authentication = jwtProvider.getAuthentication(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    log.info("auth 데이터 {}", SecurityContextHolder.getContext().getAuthentication());

    filterChain.doFilter(request, response);
  }

  private String tokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(TOKEN_HEADER);

    if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(TOKEN_PREFIX)) {
      throw new RuntimeException("유효한 토큰이 아닙니다.");
    }

    return bearerToken.substring(TOKEN_PREFIX.length()).trim();
  }
}
