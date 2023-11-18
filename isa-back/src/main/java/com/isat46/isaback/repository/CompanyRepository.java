package com.isat46.isaback.repository;

import com.isat46.isaback.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Page<Company> findAll(Pageable pageable);
}
