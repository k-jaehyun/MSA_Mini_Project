package com.sparta.msa_exam.auth.auths;

import com.sparta.msa_exam.auth.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;
  private final JwtUtil jwtUtil;

  @PostMapping("/sign-up")
  public ResponseEntity signUp(@RequestBody SignUpRequestDto signUpRequestDto) {

    SignUpResponseDto signUpResponseDto = authService.signUp(signUpRequestDto);

    return ResponseEntity.ok(signUpResponseDto);
  }

  @PostMapping("/sign-in")
  public ResponseEntity signIn(
      @RequestBody SignInRequestDto signInRequestDto,
      HttpServletResponse response) {

    SignInResponseDto signInResponseDto = authService.signIn(signInRequestDto);

    // 토큰 생성 및 발급
    String token = jwtUtil.createToken(signInResponseDto.getUsername());
    jwtUtil.addJwtToCookie(token, response);

    return ResponseEntity.ok(signInResponseDto);
  }

}
