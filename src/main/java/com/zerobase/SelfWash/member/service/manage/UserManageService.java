package com.zerobase.SelfWash.member.service.manage;

import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.entity.User;
import com.zerobase.SelfWash.member.domain.form.UserUpdateForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import java.util.Optional;
import java.util.function.Function;

public interface UserManageService {

  boolean support(MemberType memberType);

  UserDto updateUser (Long id, UserUpdateForm form);

  void deleteUser(Long id);

  //User 정보 수정
  default UserDto updateUserInfo(
      Long id, UserUpdateForm form,
      Function<Long, Optional<? extends User>> findById
  ) {
    User user = findById.apply(id)
        .orElseThrow(() -> new RuntimeException("존재하는 회원 정보가 없습니다."));

    user.setPhone(form.getPhone());
    user.setName(form.getName());
    user.setAccount(form.getAccount());

    return UserDto.from(user);
  }

  //User 계정 삭제
  default void deleteUserInfo(
      Long id,
      Function<Long, Optional<? extends User>> findById
  ) {
    User user = findById.apply(id)
        .orElseThrow(() -> new RuntimeException("존재하는 회원 정보가 없습니다."));

    user.setDeleted(true);
  }

}
