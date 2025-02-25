package com.zerobase.SelfWash.member.service.impl;

import com.zerobase.SelfWash.member.domain.entity.Owner;
import com.zerobase.SelfWash.member.domain.form.SignUpForm;
import com.zerobase.SelfWash.member.domain.repository.OwnerRepository;
import com.zerobase.SelfWash.member.service.SignUpService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerSignUpServiceImpl implements SignUpService {

  private final OwnerRepository ownerRepository;

  @Override
  public boolean support(String userType) {
    return "owner".equalsIgnoreCase(userType);
  }

  @Override
  public void signUp(SignUpForm form) {
    Optional<Owner> optionalOwner = ownerRepository.findByEmail(form.getEmail());
    if (optionalOwner.isPresent()) {
      throw new RuntimeException("이미 존재하는 이메일입니다.");
    }

    ownerRepository.save(Owner.signUpFrom(form));
  }

  @Override
  public void verifyEmail(String email, String key) {
    Optional<Owner> optionalOwner = ownerRepository.findByEmail(email);
    if (!optionalOwner.isPresent()) {
      throw new RuntimeException("존재하지 않는 이메일입니다.");
    }
    Owner owner = optionalOwner.get();

    if (owner.isEmailAuthYn()) {
      throw new RuntimeException("이미 인증받은 이메일입니다.");
    }
    if (!owner.getEmailAuthKey().equals(key)) {
      throw new RuntimeException("인증키가 일치하지 않습니다.");
    }

    owner.setEmailAuthYn(true);
  }
}
