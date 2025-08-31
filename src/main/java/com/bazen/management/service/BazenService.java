package com.bazen.management.service;

import com.bazen.management.entity.Bazen;
import com.bazen.management.exception.ResourceNotFoundException;
import com.bazen.management.repository.BazenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BazenService {

    @Autowired
    private BazenRepository bazenRepository;

    public List<Bazen> getAllBazeni() {
        return bazenRepository.findAll();
    }

    public Optional<Bazen> getBazenById(Long id) {
        return bazenRepository.findById(id);
    }

    public Bazen saveBazen(Bazen bazen) {
        return bazenRepository.save(bazen);
    }

    public Bazen updateBazen(Long id, Bazen bazenDetails) {
        Bazen bazen = bazenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + id));

        bazen.setNaziv(bazenDetails.getNaziv());
        bazen.setKapacitet(bazenDetails.getKapacitet());
        bazen.setTip(bazenDetails.getTip());
        bazen.setStatus(bazenDetails.getStatus());

        return bazenRepository.save(bazen);
    }

    public void deleteBazen(Long id) {
        Bazen bazen = bazenRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bazen nije pronađen sa ID: " + id));

        bazenRepository.delete(bazen);
    }

    public List<Bazen> getAktivniBazeni() {
        return bazenRepository.findAktivniBazeni();
    }

    public List<Bazen> getBazeniByTip(Bazen.TipBazena tip) {
        return bazenRepository.findByTip(tip);
    }

    public List<Bazen> getBazeniByStatus(Bazen.StatusBazena status) {
        return bazenRepository.findByStatus(status);
    }
}