package com.example.conveyor.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

public class LoanOfferDTOTest {

    @Test
    public void testLoanOfferDTOGetterAndSetter() {
        // Arrange
        Long applicationId = 12345L;
        BigDecimal totalAmount = new BigDecimal("20000");
        BigDecimal requestedAmount = new BigDecimal("15000");
        Integer term = 24;
        BigDecimal monthlyPayment = new BigDecimal("1250");
        BigDecimal rate = new BigDecimal("0.1");
        Boolean isInsuranceEnabled = true;
        Boolean isSalaryClient = false;

        // Act
        LoanOfferDTO loanOfferDTO = new LoanOfferDTO();
        loanOfferDTO.setApplicationId(applicationId);
        loanOfferDTO.setTotalAmount(totalAmount);
        loanOfferDTO.setRequestedAmount(requestedAmount);
        loanOfferDTO.setTerm(term);
        loanOfferDTO.setMonthlyPayment(monthlyPayment);
        loanOfferDTO.setRate(rate);
        loanOfferDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        loanOfferDTO.setIsSalaryClient(isSalaryClient);

        // Assert
        Assertions.assertEquals(applicationId, loanOfferDTO.getApplicationId());
        Assertions.assertEquals(totalAmount, loanOfferDTO.getTotalAmount());
        Assertions.assertEquals(requestedAmount, loanOfferDTO.getRequestedAmount());
        Assertions.assertEquals(term, loanOfferDTO.getTerm());
        Assertions.assertEquals(monthlyPayment, loanOfferDTO.getMonthlyPayment());
        Assertions.assertEquals(rate, loanOfferDTO.getRate());
        Assertions.assertEquals(isInsuranceEnabled, loanOfferDTO.getIsInsuranceEnabled());
        Assertions.assertEquals(isSalaryClient, loanOfferDTO.getIsSalaryClient());
    }

    @Test
    public void testLoanOfferDTOToString() {
        // Arrange
        Long applicationId = 98765L;
        BigDecimal totalAmount = new BigDecimal("30000");
        BigDecimal requestedAmount = new BigDecimal("25000");
        Integer term = 36;
        BigDecimal monthlyPayment = new BigDecimal("1800");
        BigDecimal rate = new BigDecimal("0.08");
        Boolean isInsuranceEnabled = false;
        Boolean isSalaryClient = true;

        LoanOfferDTO loanOfferDTO = new LoanOfferDTO(applicationId, totalAmount, requestedAmount, term, monthlyPayment, rate, isInsuranceEnabled, isSalaryClient);

        // Act
        String str = loanOfferDTO.toString();

        // Assert
        String expectedString = "LoanOfferDTO(applicationId=98765, totalAmount=30000, requestedAmount=25000, term=36, monthlyPayment=1800, rate=0.08, isInsuranceEnabled=false, isSalaryClient=true)";
        Assertions.assertEquals(expectedString, str);
    }

    @Test
    public void testLoanOfferDTOBuilder() {
        // Arrange
        Long applicationId = 12345L;
        BigDecimal totalAmount = new BigDecimal("20000");
        BigDecimal requestedAmount = new BigDecimal("15000");
        Integer term = 24;
        BigDecimal monthlyPayment = new BigDecimal("1250");
        BigDecimal rate = new BigDecimal("0.1");
        Boolean isInsuranceEnabled = true;
        Boolean isSalaryClient = false;

        // Act
        LoanOfferDTO loanOfferDTO = LoanOfferDTO.builder()
                .applicationId(applicationId)
                .totalAmount(totalAmount)
                .requestedAmount(requestedAmount)
                .term(term)
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();

        // Assert
        Assertions.assertEquals(applicationId, loanOfferDTO.getApplicationId());
        Assertions.assertEquals(totalAmount, loanOfferDTO.getTotalAmount());
        Assertions.assertEquals(requestedAmount, loanOfferDTO.getRequestedAmount());
        Assertions.assertEquals(term, loanOfferDTO.getTerm());
        Assertions.assertEquals(monthlyPayment, loanOfferDTO.getMonthlyPayment());
        Assertions.assertEquals(rate, loanOfferDTO.getRate());
        Assertions.assertEquals(isInsuranceEnabled, loanOfferDTO.getIsInsuranceEnabled());
        Assertions.assertEquals(isSalaryClient, loanOfferDTO.getIsSalaryClient());
    }
}
