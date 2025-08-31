package com.bazen.management.repository;

import com.bazen.management.entity.Odrzavanje;
import com.bazen.management.entity.Bazen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OdrzavanjeRepository extends JpaRepository<Odrzavanje, Long> {
    
    List<Odrzavanje> findByBazen(Bazen bazen);
    
    List<Odrzavanje> findByStatus(Odrzavanje.StatusOdrzavanja status);
    
    @Query("SELECT o FROM Odrzavanje o WHERE o.bazen = :bazen AND o.status = :status")
    List<Odrzavanje> findByBazenAndStatus(@Param("bazen") Bazen bazen, 
                                        @Param("status") Odrzavanje.StatusOdrzavanja status);
    
    @Query("SELECT o FROM Odrzavanje o WHERE o.datumPocetka BETWEEN :start AND :end")
    List<Odrzavanje> findByDatumPocetkaeBetween(@Param("start") LocalDateTime start, 
                                              @Param("end") LocalDateTime end);
    
    @Query("SELECT o FROM Odrzavanje o WHERE o.bazen = :bazen AND o.status IN ('PLANIRANO', 'U_TOKU')")
    List<Odrzavanje> findActiveMaintenanceForPool(@Param("bazen") Bazen bazen);
    
    @Query("SELECT COUNT(o) FROM Odrzavanje o WHERE o.status = 'ZAVRSENO'")
    Long countCompletedMaintenance();
}