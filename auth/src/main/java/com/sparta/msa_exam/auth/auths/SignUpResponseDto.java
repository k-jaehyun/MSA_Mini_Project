package com.sparta.msa_exam.auth.auths;

import com.sparta.msa_exam.auth.core.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResponseDto {

  private Long id;

  private String username;

  public SignUpResponseDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
