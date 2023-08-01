package com.example.conveyor.service;

import com.example.conveyor.dto.*;
import com.example.conveyor.exceptions.ScoreDenyedException;
import com.example.conveyor.utils.ScoringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import static com.example.conveyor.utils.PreScoringUtils.*;

@Slf4j
@Service
public class ConveyorService {
    @Value("${baseRate}")
    private BigDecimal baseRate;

    public List<LoanOfferDTO> getOffers(LoanApplicationRequestDTO requestDTO) {
        List<LoanOfferDTO> offersDTO = new ArrayList<>();
        BigDecimal tempMonthlyPayment, tempTotalAmount, tempRate;

        for (int i = 0 ; i < 4; i++){

            LoanOfferDTO currentOffer= new LoanOfferDTO();
            currentOffer.setTerm(requestDTO.getTerm());
            currentOffer.setRequestedAmount(requestDTO.getAmount());
            currentOffer.setApplicationId((long) i);
            offersDTO.add(currentOffer);
            log.info("add one offer to list - {}", currentOffer);
        }

        tempMonthlyPayment = this.calcMonthlyPay(requestDTO.getAmount(), requestDTO.getTerm(), baseRate);
        tempTotalAmount = tempMonthlyPayment.multiply(BigDecimal.valueOf(requestDTO.getTerm()));
        calcLoanOffer(offersDTO.get(0), false, false, baseRate, tempMonthlyPayment, tempTotalAmount);

        tempRate = baseRate.subtract(BigDecimal.valueOf(2));
        tempMonthlyPayment = INSURANCE_BASE.add(INSURANCE_PERCENT.multiply(requestDTO.getAmount()));
        tempMonthlyPayment = this.calcMonthlyPay(requestDTO.getAmount().add(tempMonthlyPayment), requestDTO.getTerm(), tempRate);
        tempTotalAmount = tempMonthlyPayment.multiply(BigDecimal.valueOf(requestDTO.getTerm()));
        calcLoanOffer(offersDTO.get(2), false, true, tempRate, tempMonthlyPayment, tempTotalAmount);

        tempRate = baseRate.subtract(BigDecimal.ONE);
        tempMonthlyPayment = this.calcMonthlyPay(requestDTO.getAmount(), requestDTO.getTerm(), tempRate);
        tempTotalAmount = tempMonthlyPayment.multiply(BigDecimal.valueOf(requestDTO.getTerm()));
        calcLoanOffer(offersDTO.get(1), true, false, tempRate, tempMonthlyPayment, tempTotalAmount);

        tempRate = baseRate.subtract(BigDecimal.valueOf(3));
        tempMonthlyPayment = INSURANCE_BASE.add(INSURANCE_PERCENT.multiply(requestDTO.getAmount()));
        tempMonthlyPayment = this.calcMonthlyPay(requestDTO.getAmount().add(tempMonthlyPayment), requestDTO.getTerm(), tempRate);
        tempTotalAmount = tempMonthlyPayment.multiply(BigDecimal.valueOf(requestDTO.getTerm()));
        calcLoanOffer(offersDTO.get(3), true, true, tempRate, tempMonthlyPayment, tempTotalAmount);

        log.info("List<LoanOfferDTO> created successfully");

        return offersDTO;
    }

    public BigDecimal calcMonthlyPay(BigDecimal amount, Integer term, BigDecimal rate) {
        log.info("Method calculating monthly pay");
        rate = rate.divide(BigDecimal.valueOf(1200), MathContext.DECIMAL64);
        return rate.add(BigDecimal.ONE)
                .pow(term)
                .multiply(rate)
                .divide(rate.add(BigDecimal.ONE).pow(term).subtract(BigDecimal.ONE), MathContext.DECIMAL64)
                .multiply(amount);

    }

    public CreditDTO calculationCredit(ScoringDataDTO scoringDataDTO) throws ScoreDenyedException {
        CreditDTO creditDTO = new CreditDTO();
        try {
            BigDecimal rate = createScore(scoringDataDTO);
            creditDTO.setAmount(scoringDataDTO.getAmount());
            creditDTO.setTerm(scoringDataDTO.getTerm());
            creditDTO.setIsSalaryClient(scoringDataDTO.getIsSalaryClient());
            creditDTO.setIsInsuranceEnabled(scoringDataDTO.getIsInsuranceEnabled());
            creditDTO.setRate(rate);
            creditDTO.setMonthlyPayment(calcMonthlyPay(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(), rate));
            creditDTO.setPaymentSchedule(calcPaymentSchedule(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(),
                                                rate, creditDTO.getMonthlyPayment()));
            creditDTO.setPsk(calculatePsk(scoringDataDTO.getAmount(), scoringDataDTO.getTerm(), rate,
                                               creditDTO.getMonthlyPayment(), creditDTO.getPaymentSchedule()));
            System.out.println(creditDTO);
        } catch (ScoreDenyedException ex) {
            log.info("The scoring was disrupted, which led to the interruption of the calculation. {} ", scoringDataDTO);
        }
        return creditDTO;
    }

    private BigDecimal calculatePsk(BigDecimal amount, Integer term,
                                    BigDecimal rate, BigDecimal monthlyPayment,
                                    List<PaymentScheduleElement> paymentSchedule) {
        log.info("Method Calculate PSK run");

        BigDecimal payAmo = paymentSchedule.stream()
                .map(PaymentScheduleElement::getTotalPayment)
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

        BigDecimal termInYears = BigDecimal.valueOf(term).divide(BigDecimal.valueOf(12), 2, RoundingMode.CEILING);

        BigDecimal intermediateCoefficient = payAmo.divide(amount, 2, RoundingMode.CEILING)
                .subtract(BigDecimal.ONE);
        BigDecimal psk = intermediateCoefficient.divide(termInYears, 3, RoundingMode.CEILING)
                .multiply(BigDecimal.valueOf(100));
        return psk;

    }

    public List<PaymentScheduleElement> calcPaymentSchedule(BigDecimal loanAmount,
                                                               Integer term,
                                                               BigDecimal rate,
                                                               BigDecimal monthlyPayment) {
        log.info("calculating payment schedule");
        BigDecimal monthRate = rate.divide(BigDecimal.valueOf(1200));
        BigDecimal currLoanAmount = loanAmount;
        List<PaymentScheduleElement> result = new ArrayList<>();
        LocalDate dateBegin = LocalDate.now();

        PaymentScheduleElement psElement = new PaymentScheduleElement();
        psElement.setDate(dateBegin);
        psElement.setNumber(0);
        psElement.setTotalPayment(BigDecimal.ZERO);
        psElement.setInterestPayment(BigDecimal.ZERO);
        psElement.setDebtPayment(BigDecimal.ZERO);
        psElement.setRemainingDebt(currLoanAmount.negate());
        result.add(psElement);
        for (int i = 0; i < term; i++) {
            BigDecimal temp;
            psElement = new PaymentScheduleElement();
            psElement.setDate(dateBegin.plusMonths(i + 1));
            psElement.setNumber(i + 1);
            psElement.setTotalPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
            psElement.setInterestPayment(currLoanAmount.multiply(monthRate).setScale(2, RoundingMode.HALF_UP));
            temp = monthlyPayment.subtract(psElement.getInterestPayment()).setScale(2, RoundingMode.HALF_UP);
            psElement.setDebtPayment(temp);
            currLoanAmount = currLoanAmount.subtract(psElement.getDebtPayment());
            psElement.setRemainingDebt(currLoanAmount.setScale(2, RoundingMode.HALF_UP));
            result.add(psElement);
        }

        if (currLoanAmount.setScale(2, RoundingMode.HALF_UP).compareTo(BigDecimal.ZERO) != 0) {
            log.info("Payment schedule calculation malfunction. Remain loan amount is not zero: %s"
                    .formatted(currLoanAmount.setScale(2, RoundingMode.HALF_UP).toString()));
        }
        return result;
    }



    public void preScoring (LoanApplicationRequestDTO requestDTO){
        log.info("PreScoring start {}", requestDTO);
        if (!NAME_REGEXP.test(requestDTO.getFirstName())) {  // валидация имени
            throw new IllegalArgumentException("First name is illegal, should contain 2-30 latin letters, current: %s"
                    .formatted(requestDTO.getFirstName()));
        }
        if (!NAME_REGEXP.test(requestDTO.getLastName())) {   // валидация фамилии
            throw new IllegalArgumentException("Last name is illegal, should contain 2-30 latin letters, current: %s"
                    .formatted(requestDTO.getLastName()));
        }
        if (requestDTO.getMiddleName() != null && !NAME_REGEXP.test(requestDTO.getMiddleName())) {  // валидация отчества
            throw new IllegalArgumentException("Middle name is illegal, should contain 2-30 latin letters, current: %s"
                    .formatted(requestDTO.getMiddleName()));
        }
        if (requestDTO.getAmount().compareTo(MIN_AMOUNT) == -1) {  // валидация суммы заема
            throw new IllegalArgumentException("Amount is illegal, should be greater or equals 10000, current: %s"
                    .formatted(requestDTO.getAmount()));
        }
        if (requestDTO.getTerm().compareTo(MIN_TERM) == -1) {    // валидация срока заема
            throw new IllegalArgumentException("Term is illegal, should be greater or equals 6, current: %s"
                    .formatted(requestDTO.getTerm()));
        }
        if (requestDTO.getBirthDate().isAfter(LocalDate.now().minusYears(MIN_AGE))) {   // валидация возраста
            throw new IllegalArgumentException("Birth date is illegal, should be not later then 18 years before, current: %s"
                    .formatted(requestDTO.getBirthDate().format(DATE_FORMATTER)));
        }
        if (!EMAIL_REGEXP.test(requestDTO.getEmail())) {     // валидация эл почты
            throw new IllegalArgumentException("Email is illegal, should be [\\w\\.]{2,50}@[\\w\\.]{2,20}, current: %s"
                    .formatted(requestDTO.getBirthDate().format(DATE_FORMATTER)));
        }
        if (!PASSPORT_SERIAL_REGEXP.test(requestDTO.getPassportSeries())) {  // валидация серии паспорта
            throw new IllegalArgumentException("Passport serial is illegal, should be 4 length, current: %s"
                    .formatted(requestDTO.getPassportSeries()));
        }
        if (!PASSPORT_NUMBER_REGEXP.test(requestDTO.getPassportNumber())) {   // валидация номера паспорта
            throw new IllegalArgumentException("Passport number is illegal, should be 6 length, current: %s"
                    .formatted(requestDTO.getPassportNumber()));
        }
        log.info("Process preScoring finished successfully");
    }

    private void calcLoanOffer (LoanOfferDTO curr, boolean isSalary, boolean isInsurance,
                                BigDecimal rate, BigDecimal monthlyPayment, BigDecimal totalAmount ){
        curr.setIsSalaryClient(isSalary);
        curr.setIsInsuranceEnabled(isInsurance);
        curr.setRate(rate);
        curr.setMonthlyPayment(monthlyPayment.setScale(2, RoundingMode.HALF_UP));
        curr.setTotalAmount(totalAmount.setScale(2, RoundingMode.HALF_UP));
    }

    public BigDecimal createScore (ScoringDataDTO scoringDataDTO) throws ScoreDenyedException {
            log.info("scoring started");
            try {
                BigDecimal tempRate = baseRate;
                tempRate = tempRate.add(ScoringUtils.getEmploymentRate(scoringDataDTO.getEmployment().getEmploymentStatus()));
                tempRate = tempRate.add(ScoringUtils.getPositionRate(scoringDataDTO.getEmployment().getPosition()));
                if (scoringDataDTO.getEmployment().getSalary().multiply(BigDecimal.valueOf(20)).compareTo(scoringDataDTO.getAmount()) != 1) {
                    throw new ScoreDenyedException("Denied by salary amount");
                }
                tempRate = tempRate.add(ScoringUtils.getMaritalStatusRate(scoringDataDTO.getMaritalStatus()));

                if (scoringDataDTO.getDependentAmount() > 1) {
                    tempRate = tempRate.add(BigDecimal.ONE);
                }

                int age = Period.between(scoringDataDTO.getBirthDate(), LocalDate.now()).getYears();
                tempRate = tempRate.add(ScoringUtils.getGenderAndAgeRate(scoringDataDTO.getGender(), age));

                if (scoringDataDTO.getEmployment().getWorkExperienceTotal() < 12) {
                    throw new ScoreDenyedException("Denied by total experience");
                }
                if (scoringDataDTO.getEmployment().getWorkExperienceCurrent() < 3) {
                    throw new ScoreDenyedException("Denied by current experience");
                }
                log.info("scoring finished successfully");
                return tempRate;
            } catch (ScoreDenyedException ex){
                log.info("scoring interrupted");
                log.info(ex.getMessage());
                throw ex;
            }
    }
}
