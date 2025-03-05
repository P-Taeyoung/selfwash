package com.zerobase.SelfWash.member.service.signin.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.CUSTOMER;

import com.zerobase.SelfWash.member.domain.entity.Customer;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.repository.CustomerRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signin.MemberSignInService;
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
public class CustomerSignInServiceImpl implements MemberSignInService {

  private final CustomerRepository customerRepository;

  @Override
  public boolean support(MemberType type) {
    return CUSTOMER == type;
  }

  @Override
  public void signIn(SignInForm signInForm) {
    verifySignIn(signInForm, customerRepository::findByEmail);
  }


  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Customer customer = customerRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException(username + " 사용자를 찾을 수 없습니다."));

    List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

    grantedAuthorities.add(new SimpleGrantedAuthority(CUSTOMER.toString()));

    return new User(customer.getEmail(), customer.getPassword(), grantedAuthorities);
  }
}
