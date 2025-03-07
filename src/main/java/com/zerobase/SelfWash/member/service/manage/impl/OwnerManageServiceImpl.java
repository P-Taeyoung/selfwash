package com.zerobase.SelfWash.member.service.manage.impl;

import static com.zerobase.SelfWash.member.domain.type.MemberType.CUSTOMER;
import static com.zerobase.SelfWash.member.domain.type.MemberType.OWNER;

import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.form.UserUpdateForm;
import com.zerobase.SelfWash.member.domain.repository.OwnerRepository;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.manage.UserManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerManageServiceImpl implements UserManageService {

  private final OwnerRepository ownerRepository;

  @Transactional
  @Override
  public UserDto updateUser(Long id, UserUpdateForm form) {
    return updateUserInfo(id, form, ownerRepository::findById);
  }

  @Transactional
  public void deleteUser(Long id) {
    deleteUserInfo(id, ownerRepository::findById);
  }

  @Override
  public boolean support(MemberType memberType) {
    return OWNER == memberType;

  }
}
