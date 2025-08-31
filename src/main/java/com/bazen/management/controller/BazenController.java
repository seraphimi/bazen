package com.bazen.management.controller;

import com.bazen.management.entity.Bazen;
import com.bazen.management.service.BazenService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/bazeni")
@CrossOrigin(origins = "*")
public class BazenController {


    @Autowired
    private BazenService bazenService;

    @GetMapping
    public ResponseEntity<List<Bazen>> getAllBazeni() {
        List<Bazen> bazeni = bazenService.getAllBazeni();
        return ResponseEntity.ok(bazeni);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bazen> getBazenById(@PathVariable Long id) {
        return bazenService.getBazenById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Bazen> createBazen(@Valid @RequestBody Bazen bazen) {
        Bazen savedBazen = bazenService.saveBazen(bazen);
        return ResponseEntity.ok(savedBazen);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bazen> updateBazen(@PathVariable Long id, @Valid @RequestBody Bazen bazenDetails) {
        Bazen updatedBazen = bazenService.updateBazen(id, bazenDetails);
        return ResponseEntity.ok(updatedBazen);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBazen(@PathVariable Long id) {
        bazenService.deleteBazen(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aktivni")
    public ResponseEntity<List<Bazen>> getAktivniBazeni() {
        List<Bazen> aktivniBazeni = bazenService.getAktivniBazeni();
        return ResponseEntity.ok(aktivniBazeni);
    }

    @GetMapping("/tip/{tip}")
    public ResponseEntity<List<Bazen>> getBazeniByTip(@PathVariable Bazen.TipBazena tip) {
        List<Bazen> bazeni = bazenService.getBazeniByTip(tip);
        return ResponseEntity.ok(bazeni);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Bazen>> getBazeniByStatus(@PathVariable Bazen.StatusBazena status) {
        List<Bazen> bazeni = bazenService.getBazeniByStatus(status);
        return ResponseEntity.ok(bazeni);
    }
}