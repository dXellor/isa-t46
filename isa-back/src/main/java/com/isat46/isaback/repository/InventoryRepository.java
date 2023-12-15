package com.isat46.isaback.repository;

import com.isat46.isaback.model.InventoryItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<InventoryItem, Integer> {

    Page<InventoryItem> findByCompanyId(int companyId, Pageable page);
}
