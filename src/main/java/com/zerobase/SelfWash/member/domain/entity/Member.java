package com.zerobase.SelfWash.member.domain.entity;

import lombok.Getter;


public interface Member {
  long getId();
  String getEmail();
  String getPhone();
  String getName();
  String getAccount();
  boolean isEmailAuthYn();
  String getEmailAuthKey();
  String getPassword();
  void setEmailAuthYn(boolean emailAuthYn);
}
