package com.bazen.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "odrzavanje")
public class Odrzavanje {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Bazen je obavezan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bazen_id", nullable = false)
    private Bazen bazen;
    
    @NotBlank(message = "Opis radova je obavezan")
    @Column(name = "opis_radova", nullable = false, length = 500)
    private String opisRadova;
    
    @NotNull(message = "Datum početka je obavezan")
    @Column(name = "datum_pocetka", nullable = false)
    private LocalDateTime datumPocetka;
    
    @NotNull(message = "Predviđeno trajanje je obavezno")
    @Positive(message = "Trajanje mora biti pozitivno")
    @Column(name = "predvidjeno_trajanje_sati", nullable = false)
    private Integer predvidjenoTrajanjeSati;
    
    @Column(name = "datum_zavrsetka")
    private LocalDateTime datumZavrsetka;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusOdrzavanja status;
    
    @Column(name = "datum_kreiranja")
    private LocalDateTime datumKreiranja;
    
    // Constructors
    public Odrzavanje() {
        this.datumKreiranja = LocalDateTime.now();
        this.status = StatusOdrzavanja.PLANIRANO;
    }
    
    public Odrzavanje(Bazen bazen, String opisRadova, LocalDateTime datumPocetka, Integer predvidjenoTrajanjeSati) {
        this();
        this.bazen = bazen;
        this.opisRadova = opisRadova;
        this.datumPocetka = datumPocetka;
        this.predvidjenoTrajanjeSati = predvidjenoTrajanjeSati;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Bazen getBazen() {
        return bazen;
    }
    
    public void setBazen(Bazen bazen) {
        this.bazen = bazen;
    }
    
    public String getOpisRadova() {
        return opisRadova;
    }
    
    public void setOpisRadova(String opisRadova) {
        this.opisRadova = opisRadova;
    }
    
    public LocalDateTime getDatumPocetka() {
        return datumPocetka;
    }
    
    public void setDatumPocetka(LocalDateTime datumPocetka) {
        this.datumPocetka = datumPocetka;
    }
    
    public Integer getPredvidjenoTrajanjeSati() {
        return predvidjenoTrajanjeSati;
    }
    
    public void setPredvidjenoTrajanjeSati(Integer predvidjenoTrajanjeSati) {
        this.predvidjenoTrajanjeSati = predvidjenoTrajanjeSati;
    }
    
    public LocalDateTime getDatumZavrsetka() {
        return datumZavrsetka;
    }
    
    public void setDatumZavrsetka(LocalDateTime datumZavrsetka) {
        this.datumZavrsetka = datumZavrsetka;
    }
    
    public StatusOdrzavanja getStatus() {
        return status;
    }
    
    public void setStatus(StatusOdrzavanja status) {
        this.status = status;
    }
    
    public LocalDateTime getDatumKreiranja() {
        return datumKreiranja;
    }
    
    public void setDatumKreiranja(LocalDateTime datumKreiranja) {
        this.datumKreiranja = datumKreiranja;
    }
    
    // Enum
    public enum StatusOdrzavanja {
        PLANIRANO, U_TOKU, ZAVRSENO, OTKAZANO
    }
}