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
  void setDeleted(boolean deleted);
  boolean isDeleted();
  void setEmailAuthYn(boolean emailAuthYn);
  void setPhone(String phone);
  void setName(String name);
  void setAccount(String account);
}
