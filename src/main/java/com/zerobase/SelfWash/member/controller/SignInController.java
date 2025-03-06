package com.zerobase.SelfWash.member.controller;

import static com.zerobase.SelfWash.member.domain.type.MemberType.ADMIN;
import static com.zerobase.SelfWash.member.domain.type.MemberType.CUSTOMER;
import static com.zerobase.SelfWash.member.domain.type.MemberType.OWNER;

import com.zerobase.SelfWash.config.security.JwtProvider;
import com.zerobase.SelfWash.member.application.SignInApplication;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/signin")
@RequiredArgsConstructor
public class SignInController {

  private final SignInApplication signInApplication;
  private final JwtProvider jwtProvider;

  @Operation(
      summary = "고객 회원 로그인",
      tags = {"로그인"}
  )
  @PostMapping("/customer")
  public ResponseEntity<String> customerSignIn(@RequestBody SignInForm signInForm) {

    return ResponseEntity.ok(jwtProvider.createToken(
        signInApplication.userSignIn(CUSTOMER, signInForm).getId(),
        signInForm.getMemberIdOrAdminId(), CUSTOMER));
  }

  @Operation(
      summary = "점주 회원 로그인",
      tags = {"로그인"}
  )
  @PostMapping("/owner")
  public ResponseEntity<String> ownerSignIn(@RequestBody SignInForm signInForm) {

    return ResponseEntity.ok(jwtProvider.createToken(
        signInApplication.userSignIn(OWNER, signInForm).getId(),
        signInForm.getMemberIdOrAdminId(), OWNER));
  }

  @Operation(
      summary = "관리인 로그인",
      tags = {"로그인"}
  )
  @PostMapping("/admin")
  public ResponseEntity<String> adminSignIn(@RequestBody SignInForm signInForm) {

    return ResponseEntity.ok(jwtProvider.createToken(
        signInApplication.adminSignIn(signInForm).getId(),
        signInForm.getMemberIdOrAdminId(), ADMIN));
  }


}
