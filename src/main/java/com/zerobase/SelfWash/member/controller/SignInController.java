package com.zerobase.SelfWash.member.controller;

import static com.zerobase.SelfWash.member.domain.type.MemberType.ADMIN;
import static com.zerobase.SelfWash.member.domain.type.MemberType.CUSTOMER;
import static com.zerobase.SelfWash.member.domain.type.MemberType.OWNER;

import com.zerobase.SelfWash.config.security.JwtProvider;
import com.zerobase.SelfWash.member.application.SignInApplication;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
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

  @PostMapping("/customer")
  public ResponseEntity<String> customerSignIn(@RequestBody SignInForm signInForm) {

    signInApplication.signIn(CUSTOMER, signInForm);

    return ResponseEntity.ok(jwtProvider.createToken(signInForm.getMemberIdOrAdminId(), CUSTOMER));
  }

  @PostMapping("/owner")
  public ResponseEntity<String> ownerSignIn(@RequestBody SignInForm signInForm) {

    signInApplication.signIn(OWNER, signInForm);

    return ResponseEntity.ok(jwtProvider.createToken(signInForm.getMemberIdOrAdminId(), OWNER));
  }


  @PostMapping("/admin")
  public ResponseEntity<String> adminSignIn(@RequestBody SignInForm signInForm) {

    signInApplication.adminSignIn(signInForm);

    return ResponseEntity.ok(jwtProvider.createToken(signInForm.getMemberIdOrAdminId(), ADMIN));
  }


}
