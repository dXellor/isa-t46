package com.isat46.isaback.repository;

import com.isat46.isaback.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Query("SELECT c.address FROM Company c WHERE c.id = :companyId")
    Address findAddressByCompanyId(@Param("companyId") Integer companyId);

}
