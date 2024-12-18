package com.sparta.msa_exam.auth.auths;

import com.sparta.msa_exam.auth.core.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {


  Optional<User> findUserByUsername(String username);
}
