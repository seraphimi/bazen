package com.bazen.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "clan")
public class Clan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Ime je obavezno")
    @Column(nullable = false)
    private String ime;
    
    @NotBlank(message = "Prezime je obavezno")
    @Column(nullable = false)
    private String prezime;
    
    @Email(message = "Email mora biti validan")
    @Column(unique = true)
    private String email;
    
    @Column
    private String telefon;
    
    @NotNull(message = "Status članarine je obavezan")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusClanarine statusClanarine;
    
    @Column(name = "datum_registracije")
    private LocalDateTime datumRegistracije;
    
    // Constructors
    public Clan() {
        this.datumRegistracije = LocalDateTime.now();
    }
    
    public Clan(String ime, String prezime, String email, String telefon, StatusClanarine statusClanarine) {
        this();
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.telefon = telefon;
        this.statusClanarine = statusClanarine;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getIme() {
        return ime;
    }
    
    public void setIme(String ime) {
        this.ime = ime;
    }
    
    public String getPrezime() {
        return prezime;
    }
    
    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefon() {
        return telefon;
    }
    
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    
    public StatusClanarine getStatusClanarine() {
        return statusClanarine;
    }
    
    public void setStatusClanarine(StatusClanarine statusClanarine) {
        this.statusClanarine = statusClanarine;
    }
    
    public LocalDateTime getDatumRegistracije() {
        return datumRegistracije;
    }
    
    public void setDatumRegistracije(LocalDateTime datumRegistracije) {
        this.datumRegistracije = datumRegistracije;
    }
    
    // Enum
    public enum StatusClanarine {
        AKTIVNO, NEAKTIVNO
    }
}