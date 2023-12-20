package com.isat46.isaback.repository;

import com.isat46.isaback.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {

    @Query(value = "SELECT i FROM InventoryItem i WHERE i.company.id = :companyId",
            countQuery = "SELECT count(*) FROM InventoryItem i WHERE i.company.id = :companyId")
    Page<InventoryItem> findByCompanyId(int companyId, Pageable page);
}
