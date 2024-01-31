package com.isat46.isaback.repository;

import com.isat46.isaback.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {

    @Query(value = "SELECT i FROM InventoryItem i WHERE i.company.id = :companyId",
            countQuery = "SELECT count(*) FROM InventoryItem i WHERE i.company.id = :companyId")
    Page<InventoryItem> findByCompanyId(int companyId, Pageable page);

    @Query("SELECT CASE WHEN i.count - :count >= 0 THEN true ELSE false END FROM InventoryItem i WHERE i.equipment.name = :equipmentName AND i.company.name = :companyName")
    boolean isEquipmentInStock(@Param("equipmentName") String equipmentName, @Param("companyName") String companyName, @Param("count") Integer count);


    @Transactional
    @Modifying
    @Query("UPDATE InventoryItem i SET i.count = i.count - :count WHERE i.equipment IN (SELECT e FROM Equipment e WHERE e.name = :equipmentName) AND i.company IN (SELECT c FROM Company c WHERE c.name = :companyName) AND i.count >= :count")
    void subtractEquipmentCount(@Param("equipmentName") String equipmentName, @Param("companyName") String companyName, @Param("count") Integer count);
}
