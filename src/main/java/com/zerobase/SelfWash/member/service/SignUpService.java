package com.zerobase.SelfWash.member.service;

import com.zerobase.SelfWash.member.domain.form.SignUpForm;

public interface SignUpService {

  boolean support(String userType);

  void signUp(SignUpForm form);

  void verifyEmail(String email, String key);


}
