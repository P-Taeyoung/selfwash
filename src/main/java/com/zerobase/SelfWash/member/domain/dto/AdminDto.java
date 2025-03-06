package com.zerobase.SelfWash.member.domain.dto;

import com.zerobase.SelfWash.member.domain.entity.Admin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDto {

  private long id;
  private String adminId;
  private String phone;
  private String name;

  public static AdminDto from(Admin admin) {
    return AdminDto.builder()
        .id(admin.getId())
        .adminId(admin.getAdminId())
        .phone(admin.getPhone())
        .name(admin.getName())
        .build();
  }

}
