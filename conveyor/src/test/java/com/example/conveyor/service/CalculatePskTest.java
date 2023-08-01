package com.example.conveyor.service;

import com.example.conveyor.dto.PaymentScheduleElement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalculatePskTest {
    @Test
    public void testCalculatePsk() {
        // Arrange
        BigDecimal amount = BigDecimal.valueOf(10000); // Сумма кредита
        Integer term = 12; // Срок кредита в месяцах
        List<PaymentScheduleElement> paymentSchedule = new ArrayList<>();

        // Создаем элементы графика платежей и добавляем их в список
        paymentSchedule.add(new PaymentScheduleElement(1,
                LocalDate.now(),
                BigDecimal.valueOf(846.94),
                BigDecimal.valueOf(25),
                BigDecimal.valueOf(821.94),
                BigDecimal.valueOf(9178.06))); // Платеж за 1-й месяц
        paymentSchedule.add(new PaymentScheduleElement(1,
                LocalDate.now(),
                BigDecimal.valueOf(846.94),
                BigDecimal.valueOf(22.95),
                BigDecimal.valueOf(823.99),
                BigDecimal.valueOf(8354.07))); // Платеж за 2-й месяц


        ConveyorService conveyorService = new ConveyorService();

        BigDecimal psk = conveyorService.calculatePsk(amount, term, paymentSchedule);

        // Assert
        BigDecimal expectedPsk = BigDecimal.valueOf(-83.000); // Ожидаемое значение PSK
        Assertions.assertEquals(expectedPsk.setScale(1, RoundingMode.HALF_EVEN),
                psk.setScale(1, RoundingMode.HALF_EVEN), "Значение PSK неверное");
    }
}
