package com.isat46.isaback.repository;

import com.isat46.isaback.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<User, Integer> {

    public Page<User> findAll(Pageable pageable);
}
