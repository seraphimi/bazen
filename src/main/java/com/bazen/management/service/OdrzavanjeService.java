package com.bazen.management.service;

import com.bazen.management.entity.Odrzavanje;
import com.bazen.management.entity.Bazen;
import com.bazen.management.exception.ResourceNotFoundException;
import com.bazen.management.repository.OdrzavanjeRepository;
import com.bazen.management.repository.BazenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OdrzavanjeService {

    private static final Logger logger = LoggerFactory.getLogger(OdrzavanjeService.class);

    @Autowired
    private OdrzavanjeRepository odrzavanjeRepository;

    @Autowired
    private BazenRepository bazenRepository;

    public List<Odrzavanje> getAllOdrzavanje() {
        logger.debug("Dohvatanje svih održavanja");
        return odrzavanjeRepository.findAll();
    }

    public Optional<Odrzavanje> getOdrzavanjeById(Long id) {
        logger.debug("Dohvatanje održavanja sa ID: {}", id);
        return odrzavanjeRepository.findById(id);
    }

    public Odrzavanje saveOdrzavanje(Odrzavanje odrzavanje) {
        logger.info("Kreiranje novog održavanja za bazen ID: {}", odrzavanje.getBazen().getId());
        return odrzavanjeRepository.save(odrzavanje);
    }

    public Odrzavanje createOdrzavanje(Long bazenId, String opisRadova, LocalDateTime datumPocetka, Integer predvidjenoTrajanjeSati) {
        logger.info("Kreiranje održavanja za bazen ID: {}", bazenId);
        
        Bazen bazen = bazenRepository.findById(bazenId)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + bazenId));
        
        Odrzavanje odrzavanje = new Odrzavanje(bazen, opisRadova, datumPocetka, predvidjenoTrajanjeSati);
        
        return odrzavanjeRepository.save(odrzavanje);
    }

    public Odrzavanje updateOdrzavanje(Long id, Odrzavanje odrzavanjeDetails) {
        logger.info("Ažuriranje održavanja sa ID: {}", id);
        
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanje.setBazen(odrzavanjeDetails.getBazen());
        odrzavanje.setOpisRadova(odrzavanjeDetails.getOpisRadova());
        odrzavanje.setDatumPocetka(odrzavanjeDetails.getDatumPocetka());
        odrzavanje.setPredvidjenoTrajanjeSati(odrzavanjeDetails.getPredvidjenoTrajanjeSati());
        odrzavanje.setDatumZavrsetka(odrzavanjeDetails.getDatumZavrsetka());
        odrzavanje.setStatus(odrzavanjeDetails.getStatus());

        return odrzavanjeRepository.save(odrzavanje);
    }

    public void deleteOdrzavanje(Long id) {
        logger.info("Brisanje održavanja sa ID: {}", id);
        
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanjeRepository.delete(odrzavanje);
    }

    public List<Odrzavanje> getOdrzavanjeByBazen(Long bazenId) {
        logger.debug("Dohvatanje održavanja za bazen sa ID: {}", bazenId);
        
        Bazen bazen = bazenRepository.findById(bazenId)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + bazenId));
        
        return odrzavanjeRepository.findByBazen(bazen);
    }

    public List<Odrzavanje> getActiveMaintenanceForPool(Long bazenId) {
        logger.debug("Dohvatanje aktivnog održavanja za bazen sa ID: {}", bazenId);
        
        Bazen bazen = bazenRepository.findById(bazenId)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + bazenId));
        
        return odrzavanjeRepository.findActiveMaintenanceForPool(bazen);
    }

    public Odrzavanje startMaintenance(Long id) {
        logger.info("Pokretanje održavanja sa ID: {}", id);
        
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanje.setStatus(Odrzavanje.StatusOdrzavanja.U_TOKU);
        return odrzavanjeRepository.save(odrzavanje);
    }

    public Odrzavanje completeMaintenance(Long id) {
        logger.info("Završavanje održavanja sa ID: {}", id);
        
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanje.setStatus(Odrzavanje.StatusOdrzavanja.ZAVRSENO);
        odrzavanje.setDatumZavrsetka(LocalDateTime.now());
        return odrzavanjeRepository.save(odrzavanje);
    }
}