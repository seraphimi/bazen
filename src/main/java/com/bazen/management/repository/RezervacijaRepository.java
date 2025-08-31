package com.bazen.management.repository;

import com.bazen.management.entity.Rezervacija;
import com.bazen.management.entity.Bazen;
import com.bazen.management.entity.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RezervacijaRepository extends JpaRepository<Rezervacija, Long> {
    
    List<Rezervacija> findByBazen(Bazen bazen);
    
    List<Rezervacija> findByClan(Clan clan);
    
    @Query("SELECT r FROM Rezervacija r WHERE r.bazen = :bazen AND r.datumVreme BETWEEN :start AND :end")
    List<Rezervacija> findByBazenAndDatumVremeBetween(@Param("bazen") Bazen bazen, 
                                                      @Param("start") LocalDateTime start, 
                                                      @Param("end") LocalDateTime end);
    
    @Query("SELECT SUM(r.brojOsoba) FROM Rezervacija r WHERE r.bazen = :bazen AND r.datumVreme BETWEEN :start AND :end")
    Integer getTotalReservationsForPeriod(@Param("bazen") Bazen bazen, 
                                        @Param("start") LocalDateTime start, 
                                        @Param("end") LocalDateTime end);
    
    @Query("SELECT r FROM Rezervacija r WHERE r.datumVreme >= :from ORDER BY r.datumVreme")
    List<Rezervacija> findFutureReservations(@Param("from") LocalDateTime from);
    
    @Query("SELECT COUNT(r) FROM Rezervacija r WHERE r.bazen = :bazen")
    Long countByBazen(@Param("bazen") Bazen bazen);
}