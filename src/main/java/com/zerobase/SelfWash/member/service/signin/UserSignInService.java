package com.zerobase.SelfWash.member.service.signin;

import com.zerobase.SelfWash.member.domain.dto.UserDto;
import com.zerobase.SelfWash.member.domain.entity.User;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;

public interface UserSignInService extends UserDetailsService {

  boolean support(MemberType type);

  UserDto signIn(SignInForm form);

  default UserDto verifySignIn(SignInForm signInForm
  , Function<String, Optional<? extends User>> findByEmail) {

    User user = findByEmail.apply(signInForm.getMemberIdOrAdminId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

    if (!user.isEmailAuthYn()) {
      throw new RuntimeException("인증받은 이메일이 아닙니다.");
    }

    if (!BCrypt.checkpw(signInForm.getPassword(), user.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    if (user.isWithdraw()) {
      throw new RuntimeException("삭제된 회원입니다.");
    }

    return UserDto.from(user);
  };
}
