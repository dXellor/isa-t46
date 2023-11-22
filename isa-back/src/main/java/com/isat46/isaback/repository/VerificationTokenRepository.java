package com.isat46.isaback.repository;

import com.isat46.isaback.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findOneByEmailOrderByExpirationDateDesc(String email);
    VerificationToken findOneByTokenOrderByExpirationDateDesc(String token);
}
