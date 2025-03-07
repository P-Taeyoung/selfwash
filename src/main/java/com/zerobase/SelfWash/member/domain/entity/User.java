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
  void setWithdraw(boolean deleted);
  boolean isWithdraw();
  void setEmailAuthYn(boolean emailAuthYn);
  void setPhone(String phone);
  void setName(String name);
  void setAccount(String account);
}
