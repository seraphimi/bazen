package com.bazen.management.controller;

import com.bazen.management.service.IzvestajService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/izvestaji")
@CrossOrigin(origins = "*")
public class IzvestajController {

    private static final Logger logger = LoggerFactory.getLogger(IzvestajController.class);

    @Autowired
    private IzvestajService izvestajService;

    @GetMapping("/opsti")
    public ResponseEntity<Map<String, Object>> getGeneralReport() {
        logger.info("GET /api/izvestaji/opsti - Generisanje opšteg izveštaja");
        Map<String, Object> report = izvestajService.getGeneralReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/posecenost")
    public ResponseEntity<Map<String, Object>> getPoolAttendanceReport() {
        logger.info("GET /api/izvestaji/posecenost - Generisanje izveštaja o posećenosti");
        Map<String, Object> report = izvestajService.getPoolAttendanceReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/clanovi")
    public ResponseEntity<Map<String, Object>> getMemberStatusReport() {
        logger.info("GET /api/izvestaji/clanovi - Generisanje izveštaja o članovima");
        Map<String, Object> report = izvestajService.getMemberStatusReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/prihodi")
    public ResponseEntity<Map<String, Object>> getRevenueReport() {
        logger.info("GET /api/izvestaji/prihodi - Generisanje izveštaja o prihodima");
        Map<String, Object> report = izvestajService.getRevenueReport();
        return ResponseEntity.ok(report);
    }
}