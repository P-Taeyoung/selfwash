package com.zerobase.SelfWash.member.service.signin.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.ADMIN;

import com.zerobase.SelfWash.config.security.JwtProvider;
import com.zerobase.SelfWash.member.domain.entity.Admin;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.repository.AdminRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signin.AdminSignInService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSignInServiceImpl implements AdminSignInService {

  private final AdminRepository adminRepository;

  @Override
  public void signIn(SignInForm signInForm) {
    Admin admin = adminRepository.findByAdminId(signInForm.getMemberIdOrAdminId())
        .orElseThrow(() -> new RuntimeException("아이디가 존재하지 않습니다."));

    if (!admin.isAdminAuthYn()) {
      throw new RuntimeException("승인받지 못한 아이디입니다.");
    }

    if (!BCrypt.checkpw(signInForm.getPassword(), admin.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Admin admin = adminRepository.findByAdminId(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " 해당 사용자를 찾을 수 없습니다."));

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    grantedAuthorities.add(new SimpleGrantedAuthority(ADMIN.toString()));

    return new User(admin.getAdminId(), admin.getPassword(), grantedAuthorities);
  }
}
