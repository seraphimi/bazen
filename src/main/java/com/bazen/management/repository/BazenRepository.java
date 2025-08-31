package com.bazen.management.repository;

import com.bazen.management.entity.Bazen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BazenRepository extends JpaRepository<Bazen, Long> {
    
    List<Bazen> findByStatus(Bazen.StatusBazena status);
    
    List<Bazen> findByTip(Bazen.TipBazena tip);
    
    @Query("SELECT b FROM Bazen b WHERE b.status = 'AKTIVNO'")
    List<Bazen> findAktivniBazeni();
    
    @Query("SELECT b FROM Bazen b WHERE b.kapacitet >= :minKapacitet")
    List<Bazen> findByKapacitetGreaterThanEqual(Integer minKapacitet);
}