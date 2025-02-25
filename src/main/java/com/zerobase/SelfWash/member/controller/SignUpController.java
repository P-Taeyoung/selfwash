package com.zerobase.SelfWash.member.controller;

import com.zerobase.SelfWash.member.application.SignUpApplication;
import com.zerobase.SelfWash.member.domain.form.AdminSignUpForm;
import com.zerobase.SelfWash.member.domain.form.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignUpController {

  private final SignUpApplication signUpApplication;

  @PostMapping("/customer")
  public ResponseEntity<String> customerSignUp(@RequestBody SignUpForm signUpForm) {
    return ResponseEntity.ok(signUpApplication.signUp("customer",signUpForm));
  }

  @GetMapping("/verify/customer")
  public ResponseEntity<String> customerVerify(@RequestParam String email, @RequestParam String key) {
    signUpApplication.verifyEmail("customer",email, key);
    return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
  }

  @PostMapping("/owner")
  public ResponseEntity<String> ownerSignUp(@RequestBody SignUpForm signUpForm) {
    return ResponseEntity.ok(signUpApplication.signUp("owner",signUpForm));
  }

  @GetMapping("/verify/owner")
  public ResponseEntity<String> ownerVerify(@RequestParam String email, @RequestParam String key) {
    signUpApplication.verifyEmail("owner",email, key);
    return ResponseEntity.ok("이메일 인증이 완료되었습니다.");
  }

  @PostMapping("/admin")
  public ResponseEntity<String> adminSignUp(@RequestBody AdminSignUpForm signUpForm) {
    return ResponseEntity.ok(signUpApplication.adminSignUp(signUpForm));
  }




}
