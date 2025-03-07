package com.zerobase.SelfWash.member.service.manage;

import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.form.AdminUpdateForm;

public interface AdminManageService {

  // 관리인 정보 수정
  AdminDto updateAdmin(Long id, AdminUpdateForm adminUpdateForm);

  // 관리인 계정 삭제
  void deleteAdmin(Long id);

}
