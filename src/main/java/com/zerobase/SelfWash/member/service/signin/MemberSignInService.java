package com.zerobase.SelfWash.member.service.signin;

import com.zerobase.SelfWash.member.domain.dto.MemberDto;
import com.zerobase.SelfWash.member.domain.entity.Member;
import com.zerobase.SelfWash.member.domain.form.SignInForm;
import com.zerobase.SelfWash.member.domain.type.MemberType;
import java.util.Optional;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;

public interface MemberSignInService extends UserDetailsService {

  boolean support(MemberType type);

  MemberDto signIn(SignInForm form);

  default MemberDto verifySignIn(SignInForm signInForm
  , Function<String, Optional<? extends Member>> findByEmail) {

    Member member = findByEmail.apply(signInForm.getMemberIdOrAdminId())
        .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));

    if (!member.isEmailAuthYn()) {
      throw new RuntimeException("인증받은 이메일이 아닙니다.");
    }

    if (!BCrypt.checkpw(signInForm.getPassword(), member.getPassword())) {
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    return MemberDto.from(member);
  };
}
