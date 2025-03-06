package com.zerobase.SelfWash.member.domain.dto;

import com.zerobase.SelfWash.member.domain.entity.User;
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
public class UserDto {

  private long id;
  private String email;
  private String phone;
  private String name;
  private String account;

  public static UserDto from(User user) {
    return UserDto.builder()
        .id(user.getId())
        .email(user.getEmail())
        .phone(user.getPhone())
        .name(user.getName())
        .account(user.getAccount())
        .build();
  }

}
