package com.example.conveyor.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PaymentScheduleElement {

    private Integer number;
    private LocalDate date;
    private BigDecimal totalPayment;
    private BigDecimal interestPayment;     // платеж по процентам
    private BigDecimal debtPayment;         // платеж по основному долгу
    private BigDecimal remainingDebt;       //  остаток долга
}
