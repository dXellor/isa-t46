package com.isat46.isaback.repository;

import com.isat46.isaback.model.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Page<Company> findAll(Pageable pageable);

    @Query("SELECT c FROM Company c JOIN c.equipment e WHERE e.id = :equipmentId")
    Page<Company> findCompaniesThatHaveEquipment(@Param("equipmentId") Integer equipmentId, Pageable page);

    Page<Company> findByNameIgnoreCaseContainingAndAddressCityIgnoreCaseContainingAndAddressCountryIgnoreCaseContaining(
            String name, String city, String country, Pageable page);

    Page<Company> findByAverageRatingBetween(double minAverageRating, double maxAverageRating, Pageable page);
}
