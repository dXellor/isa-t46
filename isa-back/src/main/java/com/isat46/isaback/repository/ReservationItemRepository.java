package com.isat46.isaback.repository;

import com.isat46.isaback.model.ReservationItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationItemRepository extends JpaRepository<ReservationItem, Integer> {

    List<ReservationItem> findReservationItemsByReservationId(Integer reservationId);
    Page<ReservationItem> findAll(Pageable page);
}
