package com.bazen.management.controller;

import com.bazen.management.entity.Clan;
import com.bazen.management.service.ClanService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clanovi")
@CrossOrigin(origins = "*")
public class ClanController {


    @Autowired
    private ClanService clanService;

    @GetMapping
    public ResponseEntity<List<Clan>> getAllClanovi() {
        List<Clan> clanovi = clanService.getAllClanovi();
        return ResponseEntity.ok(clanovi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clan> getClanById(@PathVariable Long id) {
        return clanService.getClanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Clan> createClan(@Valid @RequestBody Clan clan) {
        Clan savedClan = clanService.saveClan(clan);
        return ResponseEntity.ok(savedClan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clan> updateClan(@PathVariable Long id, @Valid @RequestBody Clan clanDetails) {
        Clan updatedClan = clanService.updateClan(id, clanDetails);
        return ResponseEntity.ok(updatedClan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClan(@PathVariable Long id) {
        clanService.deleteClan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aktivni")
    public ResponseEntity<List<Clan>> getAktivniClanovi() {
        List<Clan> aktivniClanovi = clanService.getAktivniClanovi();
        return ResponseEntity.ok(aktivniClanovi);
    }

    @GetMapping("/pretrazi/{searchTerm}")
    public ResponseEntity<List<Clan>> searchClanovi(@PathVariable String searchTerm) {
        List<Clan> clanovi = clanService.searchClanovi(searchTerm);
        return ResponseEntity.ok(clanovi);
    }

    @GetMapping("/count/aktivni")
    public ResponseEntity<Long> countAktivniClanovi() {
        Long count = clanService.countAktivniClanovi();
        return ResponseEntity.ok(count);
    }
}