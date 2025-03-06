package com.zerobase.SelfWash.member.domain.dto;

import com.zerobase.SelfWash.member.domain.entity.Member;
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
public class MemberDto {

  private long id;
  private String email;
  private String phone;
  private String name;
  private String account;

  public static MemberDto from(Member member) {
    return MemberDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .phone(member.getPhone())
        .name(member.getName())
        .account(member.getAccount())
        .build();
  }

}
