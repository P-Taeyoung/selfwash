package com.zerobase.SelfWash.member.controller;

import static com.zerobase.SelfWash.member.domain.type.MemberType.*;

import com.zerobase.SelfWash.config.security.JwtProvider;
import com.zerobase.SelfWash.member.application.MemberManageApplication;
import com.zerobase.SelfWash.member.domain.dto.AdminDto;
import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.form.AdminUpdateForm;
import com.zerobase.SelfWash.member.domain.form.UserUpdateForm;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage")
@RequiredArgsConstructor
public class MemberManageController {

  private final MemberManageApplication memberManageApplication;
  private final JwtProvider jwtProvider;

  @Operation(
      summary = "고객 회원 정보 수정",
      tags = {"회원 정보 관리"}
  )
  @PutMapping("/customer/update")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<UserDto> updateCustomer(
      @RequestHeader(name = "Authorization") String token,
      @RequestBody UserUpdateForm signUpForm) {

    return ResponseEntity.ok(memberManageApplication.updateUser(
        jwtProvider.getPkId(token),
        signUpForm,
        CUSTOMER));
  }

  @Operation(
      summary = "고객 회원 삭제",
      tags = {"회원 정보 관리"}
  )
  @DeleteMapping("/customer/withdraw")
  @PreAuthorize("hasAuthority('CUSTOMER')")
  public ResponseEntity<String> deleteCustomer(
      @RequestHeader(name = "Authorization") String token) {

    memberManageApplication.deleteUser(jwtProvider.getPkId(token), CUSTOMER);

    return ResponseEntity.ok("회원이 삭제되었습니다.");
  }


  @Operation(
      summary = "점주 회원 정보 수정",
      tags = {"회원 정보 관리"}
  )
  @PutMapping("/owner/update")
  @PreAuthorize("hasAuthority('OWNER')")
  public ResponseEntity<UserDto> updateOwner(
      @RequestHeader(name = "Authorization") String token,
      @RequestBody UserUpdateForm signUpForm) {

    return ResponseEntity.ok(memberManageApplication.updateUser(
        jwtProvider.getPkId(token),
        signUpForm,
        OWNER));
  }

  @Operation(
      summary = "점주 회원 삭제",
      tags = {"회원 정보 관리"}
  )
  @DeleteMapping("/owner/withdraw")
  @PreAuthorize("hasAuthority('OWNER')")
  public ResponseEntity<String> deleteOwner(
      @RequestHeader(name = "Authorization") String token) {

    memberManageApplication.deleteUser(jwtProvider.getPkId(token), OWNER);

    return ResponseEntity.ok("회원이 삭제되었습니다.");
  }

  @Operation(
      summary = "관리자 정보 수정",
      tags = {"회원 정보 관리"}
  )
  @PutMapping("/admin/update")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<AdminDto> updateAdmin(
      @RequestHeader(name = "Authorization") String token,
      @RequestBody AdminUpdateForm signUpForm) {

    return ResponseEntity.ok(memberManageApplication.updateAdmin(
        jwtProvider.getPkId(token),
        signUpForm));
  }

  @Operation(
      summary = "관리자 삭제",
      tags = {"회원 정보 관리"}
  )
  @DeleteMapping("/admin/withdraw")
  @PreAuthorize("hasAuthority('ADMIN')")
  public ResponseEntity<String> deleteAdmin(
      @RequestHeader(name = "Authorization") String token) {

    memberManageApplication.deleteAdmin(jwtProvider.getPkId(token));

    return ResponseEntity.ok("회원이 삭제되었습니다.");
  }


}
