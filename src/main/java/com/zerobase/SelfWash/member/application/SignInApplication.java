package com.zerobase.SelfWash.member.application;

import static com.zerobase.SelfWash.member.domain.type.MemberType.ADMIN;

import com.zerobase.SelfWash.config.security.JwtProvider;
import com.zerobase.SelfWash.member.components.MailComponent;
import com.zerobase.SelfWash.member.domain.form.AdminSignUpForm;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.form.SignUpForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signin.AdminSignInService;
import com.zerobase.SelfWash.member.service.signin.SignInService;
import com.zerobase.SelfWash.member.service.signup.AdminSignUpService;
import com.zerobase.SelfWash.member.service.signup.SignUpService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

  private final List<SignInService> signInServices;
  private final AdminSignInService adminSignInService;

  public void signIn(MemberType memberType, SignInForm signInForm) {
    SignInService signInService = getSignInService(memberType);
    signInService.signIn(signInForm);
  }

  public void adminSignIn(SignInForm signInForm) {
    adminSignInService.signIn(signInForm);
  }


  private SignInService getSignInService(MemberType type) {
    return signInServices.stream().filter(signInService -> signInService.support(type))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("로그인 유형이 유효한 값이 아닙니다."));
  }


  public UserDetails loadMemberByMemberId(String memberId, MemberType type) {
    SignInService signInService = getSignInService(type);
    return signInService.loadUserByUsername(memberId);
  }

  public UserDetails loadAdminByAdminId(String adminId) {
    return adminSignInService.loadUserByUsername(adminId);
  }

}
