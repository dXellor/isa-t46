package com.isat46.isaback.repository;

import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Page<Reservation> findAll(Pageable pageable);
}
