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
    Page<Equipment> findEquipmentByCompanyId(@Param("companyId") Integer companyId, Pageable pageable);

    @Query("SELECT eq FROM Equipment eq " +
            "WHERE LOWER(eq.name) LIKE LOWER(CONCAT('%', :name, '%')) AND " +
            "eq.price BETWEEN :priceMin AND :priceMax AND " +
            "LOWER(eq.type) LIKE LOWER(CONCAT('%', :type, '%'))")
    Page<Equipment> filterEquipment(@Param("name") String name, @Param("priceMin") double priceMin, @Param("priceMax") double priceMax, @Param("type") String type, Pageable pageable);
}
