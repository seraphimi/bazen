package com.bazen.management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "bazen")
public class Bazen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Naziv bazena je obavezan")
    @Column(nullable = false)
    private String naziv;
    
    @NotNull(message = "Kapacitet je obavezan")
    @Min(value = 1, message = "Kapacitet mora biti veći od 0")
    @Column(nullable = false)
    private Integer kapacitet;
    
    @NotNull(message = "Tip bazena je obavezan")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipBazena tip;
    
    @NotNull(message = "Status bazena je obavezan")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusBazena status;
    
    // Constructors
    public Bazen() {}
    
    public Bazen(String naziv, Integer kapacitet, TipBazena tip, StatusBazena status) {
        this.naziv = naziv;
        this.kapacitet = kapacitet;
        this.tip = tip;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNaziv() {
        return naziv;
    }
    
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    
    public Integer getKapacitet() {
        return kapacitet;
    }
    
    public void setKapacitet(Integer kapacitet) {
        this.kapacitet = kapacitet;
    }
    
    public TipBazena getTip() {
        return tip;
    }
    
    public void setTip(TipBazena tip) {
        this.tip = tip;
    }
    
    public StatusBazena getStatus() {
        return status;
    }
    
    public void setStatus(StatusBazena status) {
        this.status = status;
    }
    
    // Enums
    public enum TipBazena {
        OTVORENI, ZATVORENI
    }
    
    public enum StatusBazena {
        AKTIVNO, U_ODRZAVANJU
    }
}