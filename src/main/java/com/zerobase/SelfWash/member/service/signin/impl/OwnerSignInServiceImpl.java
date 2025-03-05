package com.zerobase.SelfWash.member.service.signin.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.OWNER;

import com.zerobase.SelfWash.config.security.JwtProvider;
import com.zerobase.SelfWash.member.domain.entity.Owner;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.repository.OwnerRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signin.SignInService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerSignInServiceImpl implements SignInService {

  private final OwnerRepository ownerRepository;

  @Override
  public boolean support(MemberType type) {
    return OWNER == type;
  }

  @Override
  public void signIn(SignInForm signInForm) {
    verifySignIn(signInForm, ownerRepository::findByEmail);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Owner owner = ownerRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " 해당 사용자를 찾을 수 없습니다."));

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    grantedAuthorities.add(new SimpleGrantedAuthority(OWNER.toString()));

    return new User(owner.getEmail(), owner.getPassword(), grantedAuthorities);
  }
}
