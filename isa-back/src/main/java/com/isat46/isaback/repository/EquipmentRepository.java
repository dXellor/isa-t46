package com.isat46.isaback.repository;

import com.isat46.isaback.model.Equipment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    Page<Equipment> findAll(Pageable pageable);
    @Query("SELECT e FROM Company c JOIN c.equipment e WHERE c.id = :companyId")
    Page<Equipment> findEquipmentByCompanyId(Integer companyId, Pageable pageable);


}
