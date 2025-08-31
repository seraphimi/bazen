package com.bazen.management.service;

import com.bazen.management.entity.Bazen;
import com.bazen.management.entity.Clan;
import com.bazen.management.repository.BazenRepository;
import com.bazen.management.repository.ClanRepository;
import com.bazen.management.repository.RezervacijaRepository;
import com.bazen.management.repository.OdrzavanjeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IzvestajService {

    @Autowired
    private BazenRepository bazenRepository;

    @Autowired
    private ClanRepository clanRepository;

    @Autowired
    private RezervacijaRepository rezervacijaRepository;

    @Autowired
    private OdrzavanjeRepository odrzavanjeRepository;

    public Map<String, Object> getGeneralReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Pool statistics
        List<Bazen> sviBaseni = bazenRepository.findAll();
        long aktivniBazeni = sviBaseni.stream()
                .filter(b -> b.getStatus() == Bazen.StatusBazena.AKTIVNO)
                .count();
        
        report.put("ukupnoBazena", sviBaseni.size());
        report.put("aktivniBazeni", aktivniBazeni);
        report.put("bazeniUOdrzavanju", sviBaseni.size() - aktivniBazeni);
        
        // Member statistics
        Long ukupnoClanova = clanRepository.count();
        Long aktivniClanovi = clanRepository.countAktivniClanovi();
        
        report.put("ukupnoClanova", ukupnoClanova);
        report.put("aktivniClanovi", aktivniClanovi);
        report.put("neaktivniClanovi", ukupnoClanova - aktivniClanovi);
        
        // Reservation statistics
        Long ukupnoRezervacija = rezervacijaRepository.count();
        report.put("ukupnoRezervacija", ukupnoRezervacija);
        
        // Maintenance statistics
        Long ukupnoOdrzavanja = odrzavanjeRepository.count();
        Long zavrsenoOdrzavanje = odrzavanjeRepository.countCompletedMaintenance();
        
        report.put("ukupnoOdrzavanja", ukupnoOdrzavanja);
        report.put("zavrsenoOdrzavanje", zavrsenoOdrzavanje);
        report.put("aktivnoOdrzavanje", ukupnoOdrzavanja - zavrsenoOdrzavanje);
        
        return report;
    }

    public Map<String, Object> getPoolAttendanceReport() {
        Map<String, Object> report = new HashMap<>();
        List<Bazen> bazeni = bazenRepository.findAll();
        
        for (Bazen bazen : bazeni) {
            Long brojRezervacija = rezervacijaRepository.countByBazen(bazen);
            Map<String, Object> bazenData = new HashMap<>();
            bazenData.put("naziv", bazen.getNaziv());
            bazenData.put("kapacitet", bazen.getKapacitet());
            bazenData.put("tip", bazen.getTip());
            bazenData.put("status", bazen.getStatus());
            bazenData.put("brojRezervacija", brojRezervacija);
            
            report.put("bazen_" + bazen.getId(), bazenData);
        }
        
        return report;
    }

    public Map<String, Object> getMemberStatusReport() {
        Map<String, Object> report = new HashMap<>();
        
        List<Clan> aktivniClanovi = clanRepository.findByStatusClanarine(Clan.StatusClanarine.AKTIVNO);
        List<Clan> neaktivniClanovi = clanRepository.findByStatusClanarine(Clan.StatusClanarine.NEAKTIVNO);
        
        report.put("aktivniClanovi", aktivniClanovi.size());
        report.put("neaktivniClanovi", neaktivniClanovi.size());
        report.put("ukupnoClanova", aktivniClanovi.size() + neaktivniClanovi.size());
        
        // Add detailed member lists
        report.put("listaAktivnihClanova", aktivniClanovi);
        report.put("listaNeaktivnihClanova", neaktivniClanovi);
        
        return report;
    }

    public Map<String, Object> getRevenueReport() {
        Map<String, Object> report = new HashMap<>();
        
        // This is a simplified revenue report
        // In a real application, you'd have pricing and payment entities
        Long ukupnoRezervacija = rezervacijaRepository.count();
        Long aktivniClanovi = clanRepository.countAktivniClanovi();
        
        // Assumed prices for demonstration
        double cenaPoRezervaciji = 500.0; // dinars
        double mesecnaClanarina = 2000.0; // dinars
        
        double prihodOdRezervacija = ukupnoRezervacija * cenaPoRezervaciji;
        double prihodOdClanarina = aktivniClanovi * mesecnaClanarina;
        double ukupnoPrihodi = prihodOdRezervacija + prihodOdClanarina;
        
        report.put("prihodOdRezervacija", prihodOdRezervacija);
        report.put("prihodOdClanarina", prihodOdClanarina);
        report.put("ukupnoPrihodi", ukupnoPrihodi);
        report.put("brojRezervacija", ukupnoRezervacija);
        report.put("brojAktivnihClanova", aktivniClanovi);
        
        return report;
    }
}