package com.zerobase.SelfWash.config.security;

import static com.zerobase.SelfWash.member.domain.type.MemberType.ADMIN;

import com.zerobase.SelfWash.config.security.util.Aes256Util;
import com.zerobase.SelfWash.member.application.SignInApplication;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

  private static final String KEY_ROLES = "roles";

  private final SignInApplication signInApplication;

  private static final Key secretKey = Keys.hmacShaKeyFor(System.getenv("TOKEN_SECRETKEY").getBytes(
      StandardCharsets.UTF_8));

  private static final long tokenValidTime = 1000L * 60 * 60 * 24;

  public String createToken(Long pkId, String memberId, MemberType memberType) {

    Claims claims = Jwts.claims()
        .setSubject(Aes256Util.encrypt(memberId))
        .setId(Aes256Util.encrypt(pkId.toString()));

    claims.put(KEY_ROLES, memberType.name());

    Date now = new Date();

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(new Date(now.getTime() + tokenValidTime))
        .signWith(secretKey)
        .compact();
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }
    return !parseToken(token).getExpiration().before(new Date());
  }

  public Authentication getAuthentication(String token) {

    UserDetails userDetails;

    if (getMemberType(token) == ADMIN) {
      userDetails = signInApplication.loadAdminByAdminId(Aes256Util.decrypt(parseToken(token).getSubject()));
    } else {
      userDetails = signInApplication.loadMemberByMemberId(Aes256Util.decrypt(parseToken(token).getSubject())
          , getMemberType(token));
    }

    return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
  }

  // TODO 나중에 이메일 정보를 추출할지 아니면 pk Id 값을 넣을지 결정
  public String getMemberId (String token) {
    return Aes256Util.decrypt(parseToken(token).getId());
  }

  private MemberType getMemberType(String token) {
    return MemberType.valueOf(parseToken(token).get(KEY_ROLES, String.class));
  }

  public Claims parseToken(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token)
          .getBody();
    } catch (ExpiredJwtException e) {
      log.error("{} : {}", e, e.getMessage());
      return e.getClaims();
    }
  }

}
