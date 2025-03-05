package com.zerobase.SelfWash.member.service.signup.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.CUSTOMER;

import com.zerobase.SelfWash.member.domain.entity.Customer;
import com.zerobase.SelfWash.member.domain.form.MemberSignUpForm;
import com.zerobase.SelfWash.member.domain.repository.CustomerRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signup.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerSignUpServiceImpl implements MemberSignUpService {

  private final CustomerRepository customerRepository;

  @Override
  public boolean support(MemberType type) {
    return CUSTOMER == type;
  }

  @Override
  public void signUp(MemberSignUpForm form) {
    validateEmail(form, customerRepository::existsByEmail);
    customerRepository.save(Customer.signUpFrom(form));
  }

  @Override
  @Transactional
  public void verifyEmail(String email, String key) {
    verifyEmail(email, key, customerRepository::findByEmail);
  }


}
