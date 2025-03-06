package com.zerobase.SelfWash.member.domain.entity;

import com.zerobase.SelfWash.member.domain.form.UserSignUpForm;
import jakarta.persistence.Column;
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
public class Customer extends BaseEntity  implements User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(unique = true)
  private String email;

  private String password;
  private String phone;
  private String name;
  private int balance;
  private String account;

  private String emailAuthKey;
  private boolean emailAuthYn;

  public static Customer signUpFrom(UserSignUpForm signUpForm) {
    return Customer.builder()
        .email(signUpForm.getEmail())
        .password(BCrypt.hashpw(signUpForm.getPassword(), BCrypt.gensalt()))
        .phone(signUpForm.getPhone())
        .name(signUpForm.getName())
        .balance(0)
        .account(signUpForm.getAccount())
        .emailAuthKey(signUpForm.getEmailAuthKey())
        .emailAuthYn(false)
        .build();
  }

}
