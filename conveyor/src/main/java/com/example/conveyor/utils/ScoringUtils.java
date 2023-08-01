package com.example.conveyor.utils;

import com.example.conveyor.dto.enums.EmploymentStatus;
import com.example.conveyor.dto.enums.Gender;
import com.example.conveyor.dto.enums.MaritalStatus;
import com.example.conveyor.dto.enums.Position;
import com.example.conveyor.exceptions.ScoreDenyedException;

import java.math.BigDecimal;

public class ScoringUtils {

    public static BigDecimal getEmploymentRate(EmploymentStatus status) throws ScoreDenyedException {
        switch (status) {
            case SELFEMPLOYED:
                return BigDecimal.ONE;
            case EMPLOYED:
                return BigDecimal.ZERO;
            case BUSINESSOWNER:
                return new BigDecimal(String.valueOf(3));
            case UNEMPLOYED:
                throw new ScoreDenyedException("Denied by employment status");
            default:
                throw new ScoreDenyedException("Unknown Employed status");
        }
    }

    public static BigDecimal getPositionRate(Position position) {
        switch (position) {
            case MID_MANAGER:
                return new BigDecimal(-2);
            case TOPMANAGER:
                return new BigDecimal(-4);
            default:
                return BigDecimal.ZERO;
        }
    }

    public static BigDecimal getMaritalStatusRate(MaritalStatus maritalStatus) {
        switch (maritalStatus) {
            case MARRIED:
                return new BigDecimal(-3);
            case DIVORCED:
                return BigDecimal.ONE;
            default:
                return BigDecimal.ZERO;
        }
    }


    public static BigDecimal getGenderAndAgeRate(Gender gender, int age) throws ScoreDenyedException {
        if (age < 20 || age > 60)
            throw new ScoreDenyedException("The applicant does not meet the age requirements. Denied");
        switch (gender) {
            case MALE:
                return (age >= 30 && age <= 55) ? BigDecimal.valueOf(-3) : BigDecimal.ZERO;
            case FEMALE:
                return (age >= 35 && age <= 60) ? BigDecimal.valueOf(-3) : BigDecimal.ZERO;
            case OTHER:
                return BigDecimal.valueOf(3);
            default:
                throw new ScoreDenyedException("Unknown Gender status");
        }
    }


}
