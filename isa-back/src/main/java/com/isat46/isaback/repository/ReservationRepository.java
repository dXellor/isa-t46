package com.isat46.isaback.repository;

import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.User;
import com.isat46.isaback.model.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Page<Reservation> findAll(Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE :companyAdminId IN (SELECT admins.id FROM r.company.admins admins) AND EXTRACT(year FROM r.dateTime) = :year AND EXTRACT(month FROM r.dateTime) = :month AND EXTRACT(day FROM r.dateTime) = :day")
    List<Reservation> findByDay(@Param("companyAdminId") int companyAdminId, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query("SELECT r FROM Reservation r WHERE :companyAdminId IN (SELECT admins.id FROM r.company.admins admins) AND EXTRACT(year FROM r.dateTime) = :year AND EXTRACT(month FROM r.dateTime) = :month AND EXTRACT(day FROM r.dateTime) BETWEEN :day AND :day + 6")
    List<Reservation> findByWeek(@Param("companyAdminId") int companyAdminId, @Param("year") int year, @Param("month") int month, @Param("day") int day);

    @Query("SELECT r FROM Reservation r WHERE :companyAdminId IN (SELECT admins.id FROM r.company.admins admins) AND EXTRACT(month FROM r.dateTime) = :month AND EXTRACT(year FROM r.dateTime) = :year")
    List<Reservation> findByMonthAndYear(@Param("companyAdminId") int companyAdminId, @Param("year") int year, @Param("month") int month);

    @Query("SELECT r FROM Reservation r WHERE :companyAdminId IN (SELECT admins.id FROM r.company.admins admins) AND EXTRACT(year FROM r.dateTime) = :year")
    List<Reservation> findByYear(@Param("companyAdminId") int companyAdminId, @Param("year") int year);

    @Query("SELECT r FROM Reservation r WHERE r.companyAdmin.id = :companyAdminId AND (r.status = 0 OR r.status = 1)")
    List<Reservation> findByCompanyAdminId(@Param("companyAdminId") int companyAdminId);

    List<Reservation> findByCompanyIdAndDateTimeBetweenAndStatusIn(int companyId, LocalDateTime start, LocalDateTime end, Collection<ReservationStatus> statuses);
}
