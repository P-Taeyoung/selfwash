package com.zerobase.SelfWash.member.service.impl;

import com.zerobase.SelfWash.member.domain.entity.Customer;
import com.zerobase.SelfWash.member.domain.form.SignUpForm;
import com.zerobase.SelfWash.member.domain.repository.CustomerRepository;
import com.zerobase.SelfWash.member.service.SignUpService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerSignUpServiceImpl implements SignUpService {

  private final CustomerRepository customerRepository;

  @Override
  public boolean support(String userType) {
    return "customer".equalsIgnoreCase(userType);
  }

  @Override
  public void signUp(SignUpForm form) {
    Optional<Customer> optionalCustomer = customerRepository.findByEmail(form.getEmail());
    if (optionalCustomer.isPresent()) {
      throw new RuntimeException("이미 존재하는 이메일입니다.");
    }

    customerRepository.save(Customer.signUpFrom(form));
  }

  @Override
  @Transactional
  public void verifyEmail(String email, String key) {
    Optional<Customer> optionalCustomer = customerRepository.findByEmail(email);
    if (!optionalCustomer.isPresent()) {
      throw new RuntimeException("존재하지 않는 이메일입니다.");
    }
    Customer customer = optionalCustomer.get();

    if (customer.isEmailAuthYn()) {
      throw new RuntimeException("이미 인증받은 이메일입니다.");
    }
    if (!customer.getEmailAuthKey().equals(key)) {
      throw new RuntimeException("인증키가 일치하지 않습니다.");
    }

    customer.setEmailAuthYn(true);
  }


}
