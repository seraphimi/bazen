package com.bazen.management.controller;

import com.bazen.management.entity.Odrzavanje;
import com.bazen.management.service.OdrzavanjeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/odrzavanje")
@CrossOrigin(origins = "*")
public class OdrzavanjeController {

    private static final Logger logger = LoggerFactory.getLogger(OdrzavanjeController.class);

    @Autowired
    private OdrzavanjeService odrzavanjeService;

    @GetMapping
    public ResponseEntity<List<Odrzavanje>> getAllOdrzavanje() {
        logger.info("GET /api/odrzavanje - Dohvatanje svih održavanja");
        List<Odrzavanje> odrzavanja = odrzavanjeService.getAllOdrzavanje();
        return ResponseEntity.ok(odrzavanja);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odrzavanje> getOdrzavanjeById(@PathVariable Long id) {
        logger.info("GET /api/odrzavanje/{} - Dohvatanje održavanja po ID", id);
        return odrzavanjeService.getOdrzavanjeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Odrzavanje> createOdrzavanje(@Valid @RequestBody Odrzavanje odrzavanje) {
        logger.info("POST /api/odrzavanje - Kreiranje novog održavanja");
        Odrzavanje savedOdrzavanje = odrzavanjeService.saveOdrzavanje(odrzavanje);
        return ResponseEntity.ok(savedOdrzavanje);
    }

    @PostMapping("/create")
    public ResponseEntity<Odrzavanje> createOdrzavanjeWithParams(
            @RequestParam Long bazenId,
            @RequestParam String opisRadova,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumPocetka,
            @RequestParam Integer predvidjenoTrajanjeSati) {
        
        logger.info("POST /api/odrzavanje/create - Kreiranje održavanja sa parametrima");
        Odrzavanje odrzavanje = odrzavanjeService.createOdrzavanje(bazenId, opisRadova, datumPocetka, predvidjenoTrajanjeSati);
        return ResponseEntity.ok(odrzavanje);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Odrzavanje> updateOdrzavanje(@PathVariable Long id, @Valid @RequestBody Odrzavanje odrzavanjeDetails) {
        logger.info("PUT /api/odrzavanje/{} - Ažuriranje održavanja", id);
        Odrzavanje updatedOdrzavanje = odrzavanjeService.updateOdrzavanje(id, odrzavanjeDetails);
        return ResponseEntity.ok(updatedOdrzavanje);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOdrzavanje(@PathVariable Long id) {
        logger.info("DELETE /api/odrzavanje/{} - Brisanje održavanja", id);
        odrzavanjeService.deleteOdrzavanje(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/bazen/{bazenId}")
    public ResponseEntity<List<Odrzavanje>> getOdrzavanjeByBazen(@PathVariable Long bazenId) {
        logger.info("GET /api/odrzavanje/bazen/{} - Dohvatanje održavanja po bazenu", bazenId);
        List<Odrzavanje> odrzavanja = odrzavanjeService.getOdrzavanjeByBazen(bazenId);
        return ResponseEntity.ok(odrzavanja);
    }

    @GetMapping("/bazen/{bazenId}/aktivno")
    public ResponseEntity<List<Odrzavanje>> getActiveMaintenanceForPool(@PathVariable Long bazenId) {
        logger.info("GET /api/odrzavanje/bazen/{}/aktivno - Dohvatanje aktivnog održavanja", bazenId);
        List<Odrzavanje> aktivnaOdrzavanja = odrzavanjeService.getActiveMaintenanceForPool(bazenId);
        return ResponseEntity.ok(aktivnaOdrzavanja);
    }

    @PutMapping("/{id}/pokreni")
    public ResponseEntity<Odrzavanje> startMaintenance(@PathVariable Long id) {
        logger.info("PUT /api/odrzavanje/{}/pokreni - Pokretanje održavanja", id);
        Odrzavanje odrzavanje = odrzavanjeService.startMaintenance(id);
        return ResponseEntity.ok(odrzavanje);
    }

    @PutMapping("/{id}/zavrsi")
    public ResponseEntity<Odrzavanje> completeMaintenance(@PathVariable Long id) {
        logger.info("PUT /api/odrzavanje/{}/zavrsi - Završavanje održavanja", id);
        Odrzavanje odrzavanje = odrzavanjeService.completeMaintenance(id);
        return ResponseEntity.ok(odrzavanje);
    }
}