package com.zerobase.SelfWash.member.application;

import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.dto.MemberDto;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signin.AdminSignInService;
import com.zerobase.SelfWash.member.service.signin.MemberSignInService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignInApplication {

  private final List<MemberSignInService> memberSignInServices;
  private final AdminSignInService adminSignInService;

  public MemberDto memberSignIn(MemberType memberType, SignInForm signInForm) {
    return getSignInService(memberType).signIn(signInForm);
  }

  public AdminDto adminSignIn(SignInForm signInForm) {
    return adminSignInService.signIn(signInForm);
  }


  private MemberSignInService getSignInService(MemberType type) {
    return memberSignInServices.stream().filter(memberSignInService -> memberSignInService.support(type))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("로그인 유형이 유효한 값이 아닙니다."));
  }


  public UserDetails loadMemberByMemberId(String memberId, MemberType type) {
    MemberSignInService memberSignInService = getSignInService(type);
    return memberSignInService.loadUserByUsername(memberId);
  }

  public UserDetails loadAdminByAdminId(String adminId) {
    return adminSignInService.loadUserByUsername(adminId);
  }

}
