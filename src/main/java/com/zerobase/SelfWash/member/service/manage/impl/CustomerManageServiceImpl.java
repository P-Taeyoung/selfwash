package com.zerobase.SelfWash.member.service.manage.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.CUSTOMER;

import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.form.UserUpdateForm;
import com.zerobase.SelfWash.member.domain.repository.CustomerRepository;
import com.zerobase.SelfWash.member.domain.repository.OwnerRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.manage.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerManageServiceImpl implements UserManageService {

  private final CustomerRepository customerRepository;

  @Transactional
  @Override
  public UserDto updateUser(Long id, UserUpdateForm form) {
    return updateUserInfo(id, form, customerRepository::findById);
  }

  @Transactional
  public void deleteUser(Long id) {
    deleteUserInfo(id, customerRepository::findById);
  }

  @Override
  public boolean support(MemberType memberType) {
    return CUSTOMER == memberType;
  }
}
