package com.zerobase.SelfWash.member.service.signin;

import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AdminSignInService extends UserDetailsService {

  AdminDto signIn(SignInForm signInForm);
}
