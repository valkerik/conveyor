package com.example.conveyor.service;

import com.example.conveyor.dto.LoanApplicationRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PreScoringTest {

    ConveyorService conveyorService;

    @BeforeEach
    public void init(){
        conveyorService = new ConveyorService();
    }
    @Test
    public void testValidLoanApplication() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                24,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(1990,2,2),
                "1234",
                "123456"
                );
        Assertions.assertDoesNotThrow(() -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidName() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                24,
                "Fedra",
                "F",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(1990,2,2),
                "1234",
                "123456"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidAmount() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(10),
                24,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(1990,2,2),
                "1234",
                "123456"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidTerm() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                3,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(1990,2,2),
                "1234",
                "123456"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidBirthDate() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                24,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(2020,2,2),
                "1234",
                "123456"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidEmail() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                24,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgtu",
                LocalDate.of(1990,2,2),
                "1234",
                "123456"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidPassportSerial() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                24,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(1990,2,2),
                "12",
                "123456"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }

    @Test
    public void testInvalidPassportNumber() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(1000000),
                24,
                "Fedra",
                "Ferdaa",
                "Petrovich",
                "sdgdsf@lkgs.tu",
                LocalDate.of(1990,2,2),
                "1234",
                "123456565656"
        );
        Assertions.assertThrows(IllegalArgumentException.class, () -> conveyorService.preScoring(requestDTO));
    }
}
