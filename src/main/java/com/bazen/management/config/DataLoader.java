package com.bazen.management.config;

import com.bazen.management.entity.Bazen;
import com.bazen.management.entity.Clan;
import com.bazen.management.entity.Odrzavanje;
import com.bazen.management.repository.BazenRepository;
import com.bazen.management.repository.ClanRepository;
import com.bazen.management.repository.OdrzavanjeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Autowired
    private BazenRepository bazenRepository;

    @Autowired
    private ClanRepository clanRepository;

    @Autowired
    private OdrzavanjeRepository odrzavanjeRepository;

    @Override
    public void run(String... args) throws Exception {
        if (bazenRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        logger.info("Učitavanje početnih podataka...");

        // Kreiranje bazena
        Bazen bazen1 = new Bazen("Glavni bazen", 50, Bazen.TipBazena.OTVORENI, Bazen.StatusBazena.AKTIVNO);
        Bazen bazen2 = new Bazen("Dečiji bazen", 20, Bazen.TipBazena.OTVORENI, Bazen.StatusBazena.AKTIVNO);
        Bazen bazen3 = new Bazen("Zatvoreni bazen", 30, Bazen.TipBazena.ZATVORENI, Bazen.StatusBazena.U_ODRZAVANJU);

        bazen1 = bazenRepository.save(bazen1);
        bazen2 = bazenRepository.save(bazen2);
        bazen3 = bazenRepository.save(bazen3);

        // Kreiranje članova
        Clan clan1 = new Clan("Marko", "Petrović", "marko.petrovic@email.com", "+381601234567", Clan.StatusClanarine.AKTIVNO);
        Clan clan2 = new Clan("Ana", "Nikolić", "ana.nikolic@email.com", "+381602345678", Clan.StatusClanarine.AKTIVNO);
        Clan clan3 = new Clan("Stefan", "Jovanović", "stefan.jovanovic@email.com", "+381603456789", Clan.StatusClanarine.NEAKTIVNO);
        Clan clan4 = new Clan("Milica", "Stojanović", "milica.stojanovic@email.com", "+381604567890", Clan.StatusClanarine.AKTIVNO);

        clanRepository.save(clan1);
        clanRepository.save(clan2);
        clanRepository.save(clan3);
        clanRepository.save(clan4);

        // Kreiranje održavanja
        Odrzavanje odrzavanje1 = new Odrzavanje(bazen3, "Čišćenje i dezinfekcija", 
                LocalDateTime.now().plusDays(1), 4);
        Odrzavanje odrzavanje2 = new Odrzavanje(bazen1, "Redovno održavanje filtara", 
                LocalDateTime.now().plusDays(7), 2);

        odrzavanjeRepository.save(odrzavanje1);
        odrzavanjeRepository.save(odrzavanje2);

        logger.info("Početni podaci su uspešno učitani:");
        logger.info("- {} bazena", bazenRepository.count());
        logger.info("- {} članova", clanRepository.count());
        logger.info("- {} održavanja", odrzavanjeRepository.count());
    }
}