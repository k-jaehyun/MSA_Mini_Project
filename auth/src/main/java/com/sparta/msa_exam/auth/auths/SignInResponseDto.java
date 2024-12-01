package com.sparta.msa_exam.auth.auths;

import com.sparta.msa_exam.auth.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignInResponseDto {

  private Long userId;

  private String username;

  public SignInResponseDto(User user) {
    this.userId = user.getId();
    this.username = user.getUsername();
  }
}
