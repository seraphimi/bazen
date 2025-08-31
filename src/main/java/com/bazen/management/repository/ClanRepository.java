package com.bazen.management.repository;

import com.bazen.management.entity.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {
    
    List<Clan> findByStatusClanarine(Clan.StatusClanarine statusClanarine);
    
    Optional<Clan> findByEmail(String email);
    
    List<Clan> findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(String ime, String prezime);
    
    @Query("SELECT c FROM Clan c WHERE c.statusClanarine = 'AKTIVNO'")
    List<Clan> findAktivniClanovi();
    
    @Query("SELECT COUNT(c) FROM Clan c WHERE c.statusClanarine = 'AKTIVNO'")
    Long countAktivniClanovi();
}