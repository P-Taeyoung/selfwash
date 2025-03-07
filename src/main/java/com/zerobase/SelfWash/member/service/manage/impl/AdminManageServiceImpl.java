package com.zerobase.SelfWash.member.service.manage.impl;

import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.entity.Admin;
import com.zerobase.SelfWash.member.domain.form.AdminUpdateForm;
import com.zerobase.SelfWash.member.domain.repository.AdminRepository;
import com.zerobase.SelfWash.member.service.manage.AdminManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminManageServiceImpl implements AdminManageService {

  private final AdminRepository adminRepository;

  @Override
  @Transactional
  public AdminDto updateAdmin(Long id, AdminUpdateForm adminUpdateForm) {
    Admin admin = adminRepository.findById(id).orElseThrow(RuntimeException::new);
    admin.setName(adminUpdateForm.getName());
    admin.setPhone(adminUpdateForm.getPhone());
    return AdminDto.from(admin);
  }

  @Override
  @Transactional
  public void deleteAdmin(Long id) {
    Admin admin = adminRepository.findById(id).orElseThrow(RuntimeException::new);
    admin.setWithdraw(true);
  }

}
