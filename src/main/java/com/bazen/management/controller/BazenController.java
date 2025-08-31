package com.bazen.management.controller;

import com.bazen.management.entity.Bazen;
import com.bazen.management.service.BazenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bazeni")
@CrossOrigin(origins = "*")
public class BazenController {

    private static final Logger logger = LoggerFactory.getLogger(BazenController.class);

    @Autowired
    private BazenService bazenService;

    @GetMapping
    public ResponseEntity<List<Bazen>> getAllBazeni() {
        logger.info("GET /api/bazeni - Dohvatanje svih bazena");
        List<Bazen> bazeni = bazenService.getAllBazeni();
        return ResponseEntity.ok(bazeni);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bazen> getBazenById(@PathVariable Long id) {
        logger.info("GET /api/bazeni/{} - Dohvatanje bazena po ID", id);
        return bazenService.getBazenById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bazen> createBazen(@Valid @RequestBody Bazen bazen) {
        logger.info("POST /api/bazeni - Kreiranje novog bazena");
        Bazen savedBazen = bazenService.saveBazen(bazen);
        return ResponseEntity.ok(savedBazen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bazen> updateBazen(@PathVariable Long id, @Valid @RequestBody Bazen bazenDetails) {
        logger.info("PUT /api/bazeni/{} - Ažuriranje bazena", id);
        Bazen updatedBazen = bazenService.updateBazen(id, bazenDetails);
        return ResponseEntity.ok(updatedBazen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBazen(@PathVariable Long id) {
        logger.info("DELETE /api/bazeni/{} - Brisanje bazena", id);
        bazenService.deleteBazen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aktivni")
    public ResponseEntity<List<Bazen>> getAktivniBazeni() {
        logger.info("GET /api/bazeni/aktivni - Dohvatanje aktivnih bazena");
        List<Bazen> aktivniBazeni = bazenService.getAktivniBazeni();
        return ResponseEntity.ok(aktivniBazeni);
    }

    @GetMapping("/tip/{tip}")
    public ResponseEntity<List<Bazen>> getBazeniByTip(@PathVariable Bazen.TipBazena tip) {
        logger.info("GET /api/bazeni/tip/{} - Dohvatanje bazena po tipu", tip);
        List<Bazen> bazeni = bazenService.getBazeniByTip(tip);
        return ResponseEntity.ok(bazeni);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Bazen>> getBazeniByStatus(@PathVariable Bazen.StatusBazena status) {
        logger.info("GET /api/bazeni/status/{} - Dohvatanje bazena po statusu", status);
        List<Bazen> bazeni = bazenService.getBazeniByStatus(status);
        return ResponseEntity.ok(bazeni);
    }
}