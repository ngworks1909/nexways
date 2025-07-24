package com.nithin.auth.authentication;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, String> {
    Optional<Auth> findByEmail(String email);
}
