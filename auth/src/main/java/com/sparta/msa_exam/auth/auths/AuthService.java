package com.sparta.msa_exam.auth.auths;

import com.sparta.msa_exam.auth.core.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
    String username = signUpRequestDto.getUsername();
    String encodedPassword = passwordEncoder.encode(signUpRequestDto.getPassword());

    if (userRepository.findUserByUsername(username).isPresent()) {
      throw new IllegalArgumentException(username + " : 이미 존재하는 username 입니다.");
    }

    User user = new User(username, encodedPassword);
    userRepository.save(user);

    return new SignUpResponseDto(user);
  }
}
