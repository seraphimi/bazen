package com.bazen.management.service;

import com.bazen.management.entity.Bazen;
import com.bazen.management.entity.Clan;
import com.bazen.management.entity.Rezervacija;
import com.bazen.management.exception.CapacityExceededException;
import com.bazen.management.repository.BazenRepository;
import com.bazen.management.repository.ClanRepository;
import com.bazen.management.repository.RezervacijaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RezervacijaServiceTest {

    @Mock
    private RezervacijaRepository rezervacijaRepository;

    @Mock
    private BazenRepository bazenRepository;

    @Mock
    private ClanRepository clanRepository;

    @InjectMocks
    private RezervacijaService rezervacijaService;

    private Bazen testBazen;
    private Clan testClan;

    @BeforeEach
    void setUp() {
        testBazen = new Bazen("Test Bazen", 10, Bazen.TipBazena.OTVORENI, Bazen.StatusBazena.AKTIVNO);
        testBazen.setId(1L);

        testClan = new Clan("Marko", "Petrovic", "marko@test.com", "123456789", Clan.StatusClanarine.AKTIVNO);
        testClan.setId(1L);
    }

    @Test
    void testCreateRezervacijaSuccess() {
        // Arrange
        when(clanRepository.findById(1L)).thenReturn(Optional.of(testClan));
        when(bazenRepository.findById(1L)).thenReturn(Optional.of(testBazen));
        when(rezervacijaRepository.getTotalReservationsForPeriod(any(), any(), any())).thenReturn(5);
        when(rezervacijaRepository.save(any(Rezervacija.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Rezervacija rezultat = rezervacijaService.createRezervacija(1L, 1L, LocalDateTime.now().plusDays(1), 3);

        // Assert
        assertNotNull(rezultat);
        assertEquals(testClan, rezultat.getClan());
        assertEquals(testBazen, rezultat.getBazen());
        assertEquals(3, rezultat.getBrojOsoba());
        verify(rezervacijaRepository).save(any(Rezervacija.class));
    }

    @Test
    void testCreateRezervacijaCapacityExceeded() {
        // Arrange
        when(clanRepository.findById(1L)).thenReturn(Optional.of(testClan));
        when(bazenRepository.findById(1L)).thenReturn(Optional.of(testBazen));
        when(rezervacijaRepository.getTotalReservationsForPeriod(any(), any(), any())).thenReturn(8);

        // Act & Assert
        assertThrows(CapacityExceededException.class, () -> {
            rezervacijaService.createRezervacija(1L, 1L, LocalDateTime.now().plusDays(1), 5);
        });

        verify(rezervacijaRepository, never()).save(any(Rezervacija.class));
    }

    @Test
    void testCreateRezervacijaInactiveMember() {
        // Arrange
        testClan.setStatusClanarine(Clan.StatusClanarine.NEAKTIVNO);
        when(clanRepository.findById(1L)).thenReturn(Optional.of(testClan));
        when(bazenRepository.findById(1L)).thenReturn(Optional.of(testBazen));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            rezervacijaService.createRezervacija(1L, 1L, LocalDateTime.now().plusDays(1), 3);
        });

        verify(rezervacijaRepository, never()).save(any(Rezervacija.class));
    }

    @Test
    void testCreateRezervacijaInactivePool() {
        // Arrange
        testBazen.setStatus(Bazen.StatusBazena.U_ODRZAVANJU);
        when(clanRepository.findById(1L)).thenReturn(Optional.of(testClan));
        when(bazenRepository.findById(1L)).thenReturn(Optional.of(testBazen));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            rezervacijaService.createRezervacija(1L, 1L, LocalDateTime.now().plusDays(1), 3);
        });

        verify(rezervacijaRepository, never()).save(any(Rezervacija.class));
    }
}