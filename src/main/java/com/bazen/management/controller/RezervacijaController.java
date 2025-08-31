package com.bazen.management.controller;

import com.bazen.management.entity.Rezervacija;
import com.bazen.management.service.RezervacijaService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rezervacije")
@CrossOrigin(origins = "*")
public class RezervacijaController {


    @Autowired
    private RezervacijaService rezervacijaService;

    @GetMapping
    public ResponseEntity<List<Rezervacija>> getAllRezervacije() {
        List<Rezervacija> rezervacije = rezervacijaService.getAllRezervacije();
        return ResponseEntity.ok(rezervacije);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rezervacija> getRezervacijaById(@PathVariable Long id) {
        return rezervacijaService.getRezervacijaById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rezervacija> createRezervacija(@Valid @RequestBody Rezervacija rezervacija) {
        Rezervacija savedRezervacija = rezervacijaService.saveRezervacija(rezervacija);
        return ResponseEntity.ok(savedRezervacija);
    }

    @PostMapping("/create")
    public ResponseEntity<Rezervacija> createRezervacijaWithParams(
            @RequestParam Long clanId,
            @RequestParam Long bazenId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumVreme,
            @RequestParam Integer brojOsoba) {
        
        Rezervacija rezervacija = rezervacijaService.createRezervacija(clanId, bazenId, datumVreme, brojOsoba);
        return ResponseEntity.ok(rezervacija);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rezervacija> updateRezervacija(@PathVariable Long id, @Valid @RequestBody Rezervacija rezervacijaDetails) {
        Rezervacija updatedRezervacija = rezervacijaService.updateRezervacija(id, rezervacijaDetails);
        return ResponseEntity.ok(updatedRezervacija);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRezervacija(@PathVariable Long id) {
        rezervacijaService.deleteRezervacija(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bazen/{bazenId}")
    public ResponseEntity<List<Rezervacija>> getRezervacijeByBazen(@PathVariable Long bazenId) {
        List<Rezervacija> rezervacije = rezervacijaService.getRezervacijeByBazen(bazenId);
        return ResponseEntity.ok(rezervacije);
    }

    @GetMapping("/clan/{clanId}")
    public ResponseEntity<List<Rezervacija>> getRezervacijeByClan(@PathVariable Long clanId) {
        List<Rezervacija> rezervacije = rezervacijaService.getRezervacijeByClan(clanId);
        return ResponseEntity.ok(rezervacije);
    }

    @GetMapping("/buduce")
    public ResponseEntity<List<Rezervacija>> getFutureRezervacije() {
        List<Rezervacija> rezervacije = rezervacijaService.getFutureRezervacije();
        return ResponseEntity.ok(rezervacije);
    }
}