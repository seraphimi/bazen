package com.bazen.management.service;

import com.bazen.management.entity.Odrzavanje;
import com.bazen.management.entity.Bazen;
import com.bazen.management.exception.ResourceNotFoundException;
import com.bazen.management.repository.OdrzavanjeRepository;
import com.bazen.management.repository.BazenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OdrzavanjeService {

    @Autowired
    private OdrzavanjeRepository odrzavanjeRepository;

    @Autowired
    private BazenRepository bazenRepository;

    public List<Odrzavanje> getAllOdrzavanje() {
        return odrzavanjeRepository.findAll();
    }

    public Optional<Odrzavanje> getOdrzavanjeById(Long id) {
        return odrzavanjeRepository.findById(id);
    }

    public Odrzavanje saveOdrzavanje(Odrzavanje odrzavanje) {
        return odrzavanjeRepository.save(odrzavanje);
    }

    public Odrzavanje createOdrzavanje(Long bazenId, String opisRadova, LocalDateTime datumPocetka, Integer predvidjenoTrajanjeSati) {
        Bazen bazen = bazenRepository.findById(bazenId)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + bazenId));
        
        Odrzavanje odrzavanje = new Odrzavanje(bazen, opisRadova, datumPocetka, predvidjenoTrajanjeSati);
        
        return odrzavanjeRepository.save(odrzavanje);
    }

    public Odrzavanje updateOdrzavanje(Long id, Odrzavanje odrzavanjeDetails) {
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
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanjeRepository.delete(odrzavanje);
    }

    public List<Odrzavanje> getOdrzavanjeByBazen(Long bazenId) {
        Bazen bazen = bazenRepository.findById(bazenId)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + bazenId));
        
        return odrzavanjeRepository.findByBazen(bazen);
    }

    public List<Odrzavanje> getActiveMaintenanceForPool(Long bazenId) {
        Bazen bazen = bazenRepository.findById(bazenId)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + bazenId));
        
        return odrzavanjeRepository.findActiveMaintenanceForPool(bazen);
    }

    public Odrzavanje startMaintenance(Long id) {
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanje.setStatus(Odrzavanje.StatusOdrzavanja.U_TOKU);
        return odrzavanjeRepository.save(odrzavanje);
    }

    public Odrzavanje completeMaintenance(Long id) {
        Odrzavanje odrzavanje = odrzavanjeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Održavanje nije pronađeno sa ID: " + id));

        odrzavanje.setStatus(Odrzavanje.StatusOdrzavanja.ZAVRSENO);
        odrzavanje.setDatumZavrsetka(LocalDateTime.now());
        return odrzavanjeRepository.save(odrzavanje);
    }
}