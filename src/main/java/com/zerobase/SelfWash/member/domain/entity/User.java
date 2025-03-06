package com.zerobase.SelfWash.member.domain.entity;


public interface User {
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
