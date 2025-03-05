package com.zerobase.SelfWash.member.service.signup.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.OWNER;

import com.zerobase.SelfWash.member.domain.entity.Owner;
import com.zerobase.SelfWash.member.domain.form.MemberSignUpForm;
import com.zerobase.SelfWash.member.domain.repository.OwnerRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.signup.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerSignUpServiceImpl implements MemberSignUpService {

  private final OwnerRepository ownerRepository;

  @Override
  public boolean support(MemberType type) {
    return OWNER == type;
  }

  @Override
  public void signUp(MemberSignUpForm form) {
    validateEmail(form, ownerRepository::existsByEmail);
    ownerRepository.save(Owner.signUpFrom(form));
  }

  @Override
  @Transactional
  public void verifyEmail(String email, String key) {
    verifyEmail(email, key, ownerRepository::findByEmail);
  }
}
