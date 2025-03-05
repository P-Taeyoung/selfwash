package com.zerobase.SelfWash.member.domain.entity;

import lombok.Getter;


public interface Member {
  boolean isEmailAuthYn();
  String getEmailAuthKey();
  String getPassword();
  void setEmailAuthYn(boolean emailAuthYn);
}
