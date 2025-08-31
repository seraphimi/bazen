package com.bazen.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "rezervacija")
public class Rezervacija {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Član je obavezan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clan_id", nullable = false)
    private Clan clan;
    
    @NotNull(message = "Bazen je obavezan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bazen_id", nullable = false)
    private Bazen bazen;
    
    @NotNull(message = "Datum i vreme su obavezni")
    @Future(message = "Rezervacija mora biti u budućnosti")
    @Column(name = "datum_vreme", nullable = false)
    private LocalDateTime datumVreme;
    
    @NotNull(message = "Broj osoba je obavezan")
    @Min(value = 1, message = "Broj osoba mora biti veći od 0")
    @Column(name = "broj_osoba", nullable = false)
    private Integer brojOsoba;
    
    @Column(name = "datum_kreiranja")
    private LocalDateTime datumKreiranja;
    
    // Constructors
    public Rezervacija() {
        this.datumKreiranja = LocalDateTime.now();
    }
    
    public Rezervacija(Clan clan, Bazen bazen, LocalDateTime datumVreme, Integer brojOsoba) {
        this();
        this.clan = clan;
        this.bazen = bazen;
        this.datumVreme = datumVreme;
        this.brojOsoba = brojOsoba;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Clan getClan() {
        return clan;
    }
    
    public void setClan(Clan clan) {
        this.clan = clan;
    }
    
    public Bazen getBazen() {
        return bazen;
    }
    
    public void setBazen(Bazen bazen) {
        this.bazen = bazen;
    }
    
    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }
    
    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }
    
    public Integer getBrojOsoba() {
        return brojOsoba;
    }
    
    public void setBrojOsoba(Integer brojOsoba) {
        this.brojOsoba = brojOsoba;
    }
    
    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }
    
    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
}