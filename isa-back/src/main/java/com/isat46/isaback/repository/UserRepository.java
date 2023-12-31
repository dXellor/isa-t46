package com.isat46.isaback.repository;

import com.isat46.isaback.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {

    Page<User> findAll(Pageable pageable);
    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.name = 'ROLE_COMPADMIN'")
    Page<User> findAllCompanyAdmins(Pageable pageable);
    User findByEmail(String email);
}
