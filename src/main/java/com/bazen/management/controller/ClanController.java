package com.bazen.management.controller;

import com.bazen.management.entity.Clan;
import com.bazen.management.service.ClanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/clanovi")
@CrossOrigin(origins = "*")
public class ClanController {

    private static final Logger logger = LoggerFactory.getLogger(ClanController.class);

    @Autowired
    private ClanService clanService;

    @GetMapping
    public ResponseEntity<List<Clan>> getAllClanovi() {
        logger.info("GET /api/clanovi - Dohvatanje svih članova");
        List<Clan> clanovi = clanService.getAllClanovi();
        return ResponseEntity.ok(clanovi);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clan> getClanById(@PathVariable Long id) {
        logger.info("GET /api/clanovi/{} - Dohvatanje člana po ID", id);
        return clanService.getClanById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Clan> createClan(@Valid @RequestBody Clan clan) {
        logger.info("POST /api/clanovi - Kreiranje novog člana");
        Clan savedClan = clanService.saveClan(clan);
        return ResponseEntity.ok(savedClan);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Clan> updateClan(@PathVariable Long id, @Valid @RequestBody Clan clanDetails) {
        logger.info("PUT /api/clanovi/{} - Ažuriranje člana", id);
        Clan updatedClan = clanService.updateClan(id, clanDetails);
        return ResponseEntity.ok(updatedClan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClan(@PathVariable Long id) {
        logger.info("DELETE /api/clanovi/{} - Brisanje člana", id);
        clanService.deleteClan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/aktivni")
    public ResponseEntity<List<Clan>> getAktivniClanovi() {
        logger.info("GET /api/clanovi/aktivni - Dohvatanje aktivnih članova");
        List<Clan> aktivniClanovi = clanService.getAktivniClanovi();
        return ResponseEntity.ok(aktivniClanovi);
    }

    @GetMapping("/pretrazi/{searchTerm}")
    public ResponseEntity<List<Clan>> searchClanovi(@PathVariable String searchTerm) {
        logger.info("GET /api/clanovi/pretrazi/{} - Pretraga članova", searchTerm);
        List<Clan> clanovi = clanService.searchClanovi(searchTerm);
        return ResponseEntity.ok(clanovi);
    }

    @GetMapping("/count/aktivni")
    public ResponseEntity<Long> countAktivniClanovi() {
        logger.info("GET /api/clanovi/count/aktivni - Brojanje aktivnih članova");
        Long count = clanService.countAktivniClanovi();
        return ResponseEntity.ok(count);
    }
}