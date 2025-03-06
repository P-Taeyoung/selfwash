package com.zerobase.SelfWash.member.service.signup;

import com.zerobase.SelfWash.member.domain.entity.User;
import com.zerobase.SelfWash.member.domain.form.UserSignUpForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public interface UserSignUpService {

  boolean support(MemberType type);

  void signUp(UserSignUpForm form);

  void verifyEmail(String email, String key);


  default void validateEmail(UserSignUpForm form
      , Predicate<String> existsByEmail) {
    if (existsByEmail.test(form.getEmail())) {
      throw new RuntimeException("이미 존재하는 이메일 입니다.");
    }
  }

  default void verifyEmail(String email
      , String key
      , Function<String, Optional<? extends User>> findByEmail) {
    
    User user = findByEmail.apply(email)
        .orElseThrow(()-> new RuntimeException("존재하지 않는 이메일입니다."));
    if (user.isEmailAuthYn()) {
      throw new RuntimeException("이미 인증받은 이메일입니다.");
    }
    if (!user.getEmailAuthKey().equals(key)) {
      throw new RuntimeException("인증키가 일치하지 않습니다.");
    }
    user.setEmailAuthYn(true);
  }




}
