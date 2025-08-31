package com.bazen.management.controller;

import com.bazen.management.service.IzvestajService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/izvestaji")
@CrossOrigin(origins = "*")
public class IzvestajController {

    @Autowired
    private IzvestajService izvestajService;

    @GetMapping("/opsti")
    public ResponseEntity<Map<String, Object>> getGeneralReport() {
        Map<String, Object> report = izvestajService.getGeneralReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/posecenost")
    public ResponseEntity<Map<String, Object>> getPoolAttendanceReport() {
        Map<String, Object> report = izvestajService.getPoolAttendanceReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/clanovi")
    public ResponseEntity<Map<String, Object>> getMemberStatusReport() {
        Map<String, Object> report = izvestajService.getMemberStatusReport();
        return ResponseEntity.ok(report);
    }

    @GetMapping("/prihodi")
    public ResponseEntity<Map<String, Object>> getRevenueReport() {
        Map<String, Object> report = izvestajService.getRevenueReport();
        return ResponseEntity.ok(report);
    }
}