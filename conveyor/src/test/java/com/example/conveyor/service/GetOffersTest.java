package com.example.conveyor.service;

import com.example.conveyor.dto.LoanApplicationRequestDTO;
import com.example.conveyor.dto.LoanOfferDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class GetOffersTest {
    private ConveyorService conveyorService;
    BigDecimal INSURANCE_PERCENT = BigDecimal.valueOf(1);
    BigDecimal INSURANCE_BASE = BigDecimal.valueOf(10000);
    int baseRate = 10;// Предположим, что здесь заданы значения baseRate, INSURANCE_BASE и INSURANCE_PERCENT

    @BeforeEach
    public void setup() {
        conveyorService = new ConveyorService();
    }

    @Test
    public void testGetOffersWithBaseRate() {
        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                new BigDecimal(100000),
                12,
                "John",
                "Doe",
                "Smith",
                "john.doe@example.com",
                LocalDate.of(1990, 1, 1),
                "1234",
                "123456"
        );

        List<LoanOfferDTO> offersDTO = conveyorService.getOffers(requestDTO);
        Assertions.assertEquals(4, offersDTO.size());

    }
}
