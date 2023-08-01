package com.example.conveyor.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CreditDTOTest {

    @Test
    public void testCreditDTOGetterAndSetter() {
        // Arrange
        BigDecimal amount = new BigDecimal("10000");
        Integer term = 12;
        BigDecimal monthlyPayment = new BigDecimal("900");
        BigDecimal rate = new BigDecimal("0.05");
        BigDecimal psk = new BigDecimal("100");
        Boolean isInsuranceEnabled = true;
        Boolean isSalaryClient = false;
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        // Act
        CreditDTO creditDTO = new CreditDTO();
        creditDTO.setAmount(amount);
        creditDTO.setTerm(term);
        creditDTO.setMonthlyPayment(monthlyPayment);
        creditDTO.setRate(rate);
        creditDTO.setPsk(psk);
        creditDTO.setIsInsuranceEnabled(isInsuranceEnabled);
        creditDTO.setIsSalaryClient(isSalaryClient);
        creditDTO.setPaymentSchedule(paymentSchedule);

        // Assert
        Assertions.assertEquals(amount, creditDTO.getAmount());
        Assertions.assertEquals(term, creditDTO.getTerm());
        Assertions.assertEquals(monthlyPayment, creditDTO.getMonthlyPayment());
        Assertions.assertEquals(rate, creditDTO.getRate());
        Assertions.assertEquals(psk, creditDTO.getPsk());
        Assertions.assertEquals(isInsuranceEnabled, creditDTO.getIsInsuranceEnabled());
        Assertions.assertEquals(isSalaryClient, creditDTO.getIsSalaryClient());
        Assertions.assertEquals(paymentSchedule, creditDTO.getPaymentSchedule());
    }

    @Test
    public void testCreditDTOToString() {
        // Arrange
        BigDecimal amount = new BigDecimal("20000");
        Integer term = 24;
        BigDecimal monthlyPayment = new BigDecimal("1500");
        BigDecimal rate = new BigDecimal("0.08");
        BigDecimal psk = new BigDecimal("200");
        Boolean isInsuranceEnabled = false;
        Boolean isSalaryClient = true;
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        CreditDTO creditDTO = new CreditDTO(amount, term, monthlyPayment, rate, psk, isInsuranceEnabled, isSalaryClient, paymentSchedule);

        // Act
        String str = creditDTO.toString();

        // Assert
        String expectedString = "CreditDTO(amount=20000, term=24, monthlyPayment=1500, rate=0.08, psk=200, isInsuranceEnabled=false, isSalaryClient=true, paymentSchedule=[])";
        Assertions.assertEquals(expectedString, str);
    }
}
