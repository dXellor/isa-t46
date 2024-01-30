package com.isat46.isaback.repository;

import com.isat46.isaback.dto.reservation.ReservationDto;
import com.isat46.isaback.model.Reservation;
import com.isat46.isaback.model.User;
import com.isat46.isaback.model.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    @Query("SELECT r FROM Reservation r WHERE r.company.id = :companyId AND r.status = 0")
    List<Reservation> findAvailableAppointmentsByCompany(@Param("companyId") int companyId);
    Page<Reservation> findByEmployeeEmailAndEmployeeNotNull(Pageable page, String email);
    Page<Reservation> findByEmployeeIsNull(Pageable page);

    @Query("SELECT r FROM Reservation r WHERE r.id = :reservationId AND r.employee.email = :employeeEmail AND r.status = 1")
    Reservation findReservationToCancel(@Param("reservationId") int reservationId, @Param("employeeEmail") String employeeEmail);

    @Query("SELECT r FROM Reservation r WHERE r.employee.email = :employeeEmail AND r.status = 4")
    List<Reservation> findCompletedByUser(@Param("employeeEmail") String employeeEmail);

    @Query("SELECT r FROM Reservation r WHERE r.employee.email = :employeeEmail AND r.status = 2")
    List<Reservation> findConfirmedByUser(@Param("employeeEmail") String employeeEmail);

    @Query("SELECT r FROM Reservation r WHERE r.employee.email = :employeeEmail AND r.status = 4"
            + "ORDER BY "
            + "CASE WHEN :orderByDuration IS NOT NULL AND :orderByDuration = 'asc' THEN r.duration END ASC, "
            + "CASE WHEN :orderByDuration IS NOT NULL AND :orderByDuration = 'desc' THEN r.duration END DESC, "
            + "CASE WHEN :orderByDateTime IS NOT NULL AND :orderByDateTime = 'asc' THEN r.dateTime END ASC, "
            + "CASE WHEN :orderByDateTime IS NOT NULL AND :orderByDateTime = 'desc' THEN r.dateTime END DESC")
    List<Reservation> orderByDurationAndDateTime(
            @Param("employeeEmail") String employeeEmail,
            @Param("orderByDuration") String orderByDuration,
            @Param("orderByDateTime") String orderByDateTime);

    @Modifying
    @Query("UPDATE Reservation r SET r.status = 6 WHERE r.status = 1 AND r.dateTime < CURRENT_TIMESTAMP")
    int invalidateOutdatedReservations();

    @Query("SELECT r FROM Reservation r WHERE r.status = 6 AND r.dateTime < CURRENT_TIMESTAMP")
    List<Reservation> findExpiredReservations();
    
    
    @Query("select r from Reservation r where r.id = :reservationId and r.status = 2")
    Reservation findReservationToDeliver(@Param("reservationId") int reservationId);
    
    @Query("select r from Reservation r where r.status = 3 and r.companyAdmin.email = :email")
    Reservation findInProgressDeliveryByAdmin(@Param("email") String email);

    @Query("select r from Reservation r where r.status = 2 and r.companyAdmin.email = :email")
    Page<Reservation> findConfirmedReservationsByAdmin(@Param("email") String email, Pageable page);

    @Query("SELECT r FROM Reservation r WHERE r.employee.email = :employeeEmail AND r.status = 1")
    List<Reservation> findPendingByUser(@Param("employeeEmail") String employeeEmail);

    @Query("SELECT r FROM Reservation r WHERE r.employee.email = :employeeEmail AND r.status = 5")
    List<Reservation> findCancelledByUser(@Param("employeeEmail") String employeeEmail);
}
