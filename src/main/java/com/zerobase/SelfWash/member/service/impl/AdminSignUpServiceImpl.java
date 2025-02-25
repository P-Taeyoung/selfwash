package com.zerobase.SelfWash.member.service.impl;

import com.zerobase.SelfWash.member.domain.entity.Admin;
import com.zerobase.SelfWash.member.domain.form.AdminSignUpForm;
import com.zerobase.SelfWash.member.domain.form.SignUpForm;
import com.zerobase.SelfWash.member.domain.repository.AdminRepository;
import com.zerobase.SelfWash.member.service.AdminSignUpService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminSignUpServiceImpl implements AdminSignUpService {
  private final AdminRepository adminRepository;

  @Override
  public void signUp(AdminSignUpForm form) {
    Optional<Admin> admin = adminRepository.findByMemberId(form.getMemberId());
    if (admin.isPresent()) {
      throw new RuntimeException("이미 존재하는 아이디입니다.");
    }

    adminRepository.save(Admin.signUpFrom(form));
  }

}
