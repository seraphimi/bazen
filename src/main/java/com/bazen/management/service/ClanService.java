package com.bazen.management.service;

import com.bazen.management.entity.Clan;
import com.bazen.management.exception.ResourceNotFoundException;
import com.bazen.management.repository.ClanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClanService {

    private static final Logger logger = LoggerFactory.getLogger(ClanService.class);

    @Autowired
    private ClanRepository clanRepository;

    public List<Clan> getAllClanovi() {
        logger.debug("Dohvatanje svih članova");
        return clanRepository.findAll();
    }

    public Optional<Clan> getClanById(Long id) {
        logger.debug("Dohvatanje člana sa ID: {}", id);
        return clanRepository.findById(id);
    }

    public Clan saveClan(Clan clan) {
        logger.info("Kreiranje novog člana: {} {}", clan.getIme(), clan.getPrezime());
        
        // Check if email already exists
        if (clan.getEmail() != null && clanRepository.findByEmail(clan.getEmail()).isPresent()) {
            throw new RuntimeException("Email već postoji: " + clan.getEmail());
        }
        
        return clanRepository.save(clan);
    }

    public Clan updateClan(Long id, Clan clanDetails) {
        logger.info("Ažuriranje člana sa ID: {}", id);
        
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Član nije pronađen sa ID: " + id));

        // Check if email is being changed and if new email already exists
        if (clanDetails.getEmail() != null && !clanDetails.getEmail().equals(clan.getEmail())) {
            Optional<Clan> existingClan = clanRepository.findByEmail(clanDetails.getEmail());
            if (existingClan.isPresent() && !existingClan.get().getId().equals(id)) {
                throw new RuntimeException("Email već postoji: " + clanDetails.getEmail());
            }
        }

        clan.setIme(clanDetails.getIme());
        clan.setPrezime(clanDetails.getPrezime());
        clan.setEmail(clanDetails.getEmail());
        clan.setTelefon(clanDetails.getTelefon());
        clan.setStatusClanarine(clanDetails.getStatusClanarine());

        return clanRepository.save(clan);
    }

    public void deleteClan(Long id) {
        logger.info("Brisanje člana sa ID: {}", id);
        
        Clan clan = clanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Član nije pronađen sa ID: " + id));

        clanRepository.delete(clan);
    }

    public List<Clan> getAktivniClanovi() {
        logger.debug("Dohvatanje aktivnih članova");
        return clanRepository.findAktivniClanovi();
    }

    public List<Clan> searchClanovi(String searchTerm) {
        logger.debug("Pretraga članova sa terminom: {}", searchTerm);
        return clanRepository.findByImeContainingIgnoreCaseOrPrezimeContainingIgnoreCase(searchTerm, searchTerm);
    }

    public Long countAktivniClanovi() {
        logger.debug("Brojanje aktivnih članova");
        return clanRepository.countAktivniClanovi();
    }
}