package com.zerobase.SelfWash.member.application;

import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signin.AdminSignInService;
import com.zerobase.SelfWash.member.service.signin.UserSignInService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

  private final List<UserSignInService> userSignInServices;
  private final AdminSignInService adminSignInService;

  public UserDto userSignIn(MemberType memberType, SignInForm signInForm) {
    return getSignInService(memberType).signIn(signInForm);
  }

  public AdminDto adminSignIn(SignInForm signInForm) {
    return adminSignInService.signIn(signInForm);
  }


  private UserSignInService getSignInService(MemberType type) {
    return userSignInServices.stream().filter(userSignInService -> userSignInService.support(type))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("로그인 유형이 유효한 값이 아닙니다."));
  }


  public UserDetails loadUserByUserId(String memberId, MemberType type) {
    UserSignInService userSignInService = getSignInService(type);
    return userSignInService.loadUserByUsername(memberId);
  }

  public UserDetails loadAdminByAdminId(String adminId) {
    return adminSignInService.loadUserByUsername(adminId);
  }

}
