package com.sparta.msa_exam.auth.auths;

import com.sparta.msa_exam.auth.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {

  private String username;

  public SignUpResponseDto(User user) {
    this.username = user.getUsername();
  }
}
