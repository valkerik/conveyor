package com.example.conveyor.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CalcMontlyPayTest {

    private static final BigDecimal BASE_RATE = BigDecimal.valueOf(5.0);

    @Test
    public void testCalcMonthlyPay() {
        ConveyorService conveyorService = new ConveyorService();

        BigDecimal amount = BigDecimal.valueOf(10000);
        int term = 12;
        BigDecimal rate = BASE_RATE;
        BigDecimal monthlyPayment = conveyorService.calcMonthlyPay(amount, term, rate);

        // Значение monthlyPayment для данного amount, term и rate заранее известно или вычислено отдельно
        // Предположим, что оно равно BigDecimal.valueOf(856.07)
        BigDecimal expectedMonthlyPayment = BigDecimal.valueOf(856.07);

        // Сравниваем значения с определенной точностью, так как значения BigDecimal могут быть неточными из-за округления
        int scale = 2;
        Assertions.assertEquals(expectedMonthlyPayment.setScale(scale, RoundingMode.HALF_EVEN),
                monthlyPayment.setScale(scale, RoundingMode.HALF_EVEN));
    }
}
