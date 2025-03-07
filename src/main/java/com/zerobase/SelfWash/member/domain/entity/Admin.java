package com.zerobase.SelfWash.member.domain.entity;

import com.zerobase.SelfWash.member.domain.form.AdminSignUpForm;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Admin extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String adminId;
  private String password;
  private String phone;
  private String name;

  private boolean deleted;

  private boolean adminAuthYn;

  public static Admin signUpFrom(AdminSignUpForm signUpForm) {
    return Admin.builder()
        .adminId(signUpForm.getAdminId())
        .password(BCrypt.hashpw(signUpForm.getPassword(), BCrypt.gensalt()))
        .phone(signUpForm.getPhone())
        .name(signUpForm.getName())
        .adminAuthYn(false)
        .deleted(false)
        .build();
  }
}
