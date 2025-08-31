package com.bazen.management.service;

import com.bazen.management.entity.Bazen;
import com.bazen.management.exception.ResourceNotFoundException;
import com.bazen.management.repository.BazenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BazenService {

    private static final Logger logger = LoggerFactory.getLogger(BazenService.class);

    @Autowired
    private BazenRepository bazenRepository;

    public List<Bazen> getAllBazeni() {
        logger.debug("Dohvatanje svih bazena");
        return bazenRepository.findAll();
    }

    public Optional<Bazen> getBazenById(Long id) {
        logger.debug("Dohvatanje bazena sa ID: {}", id);
        return bazenRepository.findById(id);
    }

    public Bazen saveBazen(Bazen bazen) {
        logger.info("Kreiranje novog bazena: {}", bazen.getNaziv());
        return bazenRepository.save(bazen);
    }

    public Bazen updateBazen(Long id, Bazen bazenDetails) {
        logger.info("Ažuriranje bazena sa ID: {}", id);
        
        Bazen bazen = bazenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + id));

        bazen.setNaziv(bazenDetails.getNaziv());
        bazen.setKapacitet(bazenDetails.getKapacitet());
        bazen.setTip(bazenDetails.getTip());
        bazen.setStatus(bazenDetails.getStatus());

        return bazenRepository.save(bazen);
    }

    public void deleteBazen(Long id) {
        logger.info("Brisanje bazena sa ID: {}", id);
        
        Bazen bazen = bazenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + id));

        bazenRepository.delete(bazen);
    }

    public List<Bazen> getAktivniBazeni() {
        logger.debug("Dohvatanje aktivnih bazena");
        return bazenRepository.findAktivniBazeni();
    }

    public List<Bazen> getBazeniByTip(Bazen.TipBazena tip) {
        logger.debug("Dohvatanje bazena po tipu: {}", tip);
        return bazenRepository.findByTip(tip);
    }

    public List<Bazen> getBazeniByStatus(Bazen.StatusBazena status) {
        logger.debug("Dohvatanje bazena po statusu: {}", status);
        return bazenRepository.findByStatus(status);
    }
}