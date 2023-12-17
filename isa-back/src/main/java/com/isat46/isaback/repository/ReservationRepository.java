package com.isat46.isaback.repository;

import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Page<Reservation> findAll(Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.companyAdmin.id = :companyAdminId AND (r.status = 0 OR r.status = 1)")
    List<Reservation> findByCompanyAdminId(@Param("companyAdminId") int companyAdminId);
}
