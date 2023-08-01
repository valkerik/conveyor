package com.example.conveyor.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanApplicationRequestDTOTest {

    @Test
    public void testLoanApplicationRequestDTOGetterAndSetter() {
        // Arrange
        BigDecimal amount = new BigDecimal("20000");
        Integer term = 12;
        String lastName = "Smith";
        String firstName = "John";
        String middleName = "Michael";
        String email = "john.smith@example.com";
        LocalDate birthDate = LocalDate.of(1990, 5, 15);
        String passportSeries = "1234";
        String passportNumber = "567890";


        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(
                amount, term,lastName, firstName, middleName, email, birthDate, passportSeries, passportNumber
        );

        // Assert
        Assertions.assertEquals(amount, requestDTO.getAmount());
        Assertions.assertEquals(term, requestDTO.getTerm());
        Assertions.assertEquals(lastName, requestDTO.getLastName());
        Assertions.assertEquals(firstName, requestDTO.getFirstName());
        Assertions.assertEquals(middleName, requestDTO.getMiddleName());
        Assertions.assertEquals(email, requestDTO.getEmail());
        Assertions.assertEquals(birthDate, requestDTO.getBirthDate());
        Assertions.assertEquals(passportSeries, requestDTO.getPassportSeries());
        Assertions.assertEquals(passportNumber, requestDTO.getPassportNumber());
    }

    @Test
    public void testLoanApplicationRequestDTOToString() {
        // Arrange
        BigDecimal amount = new BigDecimal("15000");
        Integer term = 24;
        String lastName = "Doe";
        String firstName = "Jane";
        String middleName = null;
        String email = "jane.doe@example.com";
        LocalDate birthDate = LocalDate.of(1985, 9, 20);
        String passportSeries = "9876";
        String passportNumber = "543210";

        LoanApplicationRequestDTO requestDTO = new LoanApplicationRequestDTO(amount, term, lastName, firstName, middleName, email, birthDate, passportSeries, passportNumber);

        // Act
        String str = requestDTO.toString();

        // Assert
        String expectedString = "LoanApplicationRequestDTO(amount=15000, term=24, lastName=Doe, firstName=Jane, middleName=null, email=jane.doe@example.com, birthDate=1985-09-20, passportSeries=9876, passportNumber=543210)";
        Assertions.assertEquals(expectedString, str);
    }
}

