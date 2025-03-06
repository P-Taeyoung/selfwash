package com.zerobase.SelfWash.member.application;

import com.zerobase.SelfWash.member.components.MailComponent;
import com.zerobase.SelfWash.member.domain.form.UserSignUpForm;
import com.zerobase.SelfWash.member.domain.form.AdminSignUpForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signup.AdminSignUpService;
import com.zerobase.SelfWash.member.service.signup.UserSignUpService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpApplication {

  private final List<UserSignUpService> userSignUpServices;
  private final AdminSignUpService adminSignUpService;
  private final MailComponent mailComponent;

  public void signUp(MemberType type, UserSignUpForm signUpForm) {
    UserSignUpService userSignUpService = getSignUpService(type);

    String key = UUID.randomUUID().toString();
    signUpForm.setEmailAuthKey(key);
    userSignUpService.signUp(signUpForm);

    sendMail(type, signUpForm.getEmail(), key);

  }

  public void verifyEmail(MemberType type ,String email, String key) {
    UserSignUpService userSignUpService = getSignUpService(type);

    userSignUpService.verifyEmail(email, key);
  }

  public void adminSignUp(AdminSignUpForm adminSignUpForm) {
    adminSignUpService.signUp(adminSignUpForm);
  }

  private void sendMail(MemberType type, String email, String key) {
    String subject = "SelfWash 가입을 축하드립니다.";
    String text = "<p> SelfWash 가입을 축하드립니다.</p><p>아래 링크를 클릭하셔서 가입을 완료해주세요</p>" +
        "<div><a target = '_blank' href = 'http://localhost:8080/signup/verify/" + type.toString().toLowerCase() + "?email=" + email + "&key=" + key + "'> 가입 완료 </a></div>";
    mailComponent.sendMail(email, subject, text);
  }

  private UserSignUpService getSignUpService(MemberType type) {
    return userSignUpServices.stream().filter(userSignUpService -> userSignUpService.support(type))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("회원가입 유형이 유효한 값이 아닙니다."));
  }
}
