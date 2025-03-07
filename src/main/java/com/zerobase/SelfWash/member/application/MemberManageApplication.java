package com.zerobase.SelfWash.member.application;

import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.form.AdminUpdateForm;
import com.zerobase.SelfWash.member.domain.form.UserUpdateForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import com.zerobase.SelfWash.member.service.manage.AdminManageService;
import com.zerobase.SelfWash.member.service.manage.UserManageService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberManageApplication {

  private final List<UserManageService> userManageServices;
  private final AdminManageService adminManageService;

  public UserDto updateUser (Long id, UserUpdateForm form, MemberType memberType) {
    return getUserManageService(memberType).updateUser(id, form);
  }

  public void deleteUser (Long id, MemberType memberType) {
    getUserManageService(memberType).deleteUser(id);
  }

  private UserManageService getUserManageService(MemberType memberType) {
    return userManageServices.stream().filter(userManageService -> userManageService.support(memberType))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("로그인 유형이 유효한 값이 아닙니다."));
  }

  public AdminDto updateAdmin (Long id, AdminUpdateForm form) {
    return adminManageService.updateAdmin(id, form);
  }

  public void deleteAdmin (Long id) {
    adminManageService.deleteAdmin(id);
  }

}
