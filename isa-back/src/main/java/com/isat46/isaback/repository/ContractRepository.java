package com.isat46.isaback.repository;

import com.isat46.isaback.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Integer> {
    
    List<Contract> findAll();
    void deleteByHospital(@Param("hospital") String hospital);
    Contract findContractByHospital(@Param("hospital") String hospital);
    
}
