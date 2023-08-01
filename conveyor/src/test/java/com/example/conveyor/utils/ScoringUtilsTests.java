package com.example.conveyor.utils;

import com.example.conveyor.dto.enums.EmploymentStatus;
import com.example.conveyor.dto.enums.Gender;
import com.example.conveyor.dto.enums.MaritalStatus;
import com.example.conveyor.dto.enums.Position;
import com.example.conveyor.exceptions.ScoreDenyedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ScoringUtilsTests {


    @Test
    public void testGetEmploymentRateSelfEmployed() throws ScoreDenyedException {
        EmploymentStatus status = EmploymentStatus.SELFEMPLOYED;
        BigDecimal result = ScoringUtils.getEmploymentRate(status);
        Assertions.assertEquals(BigDecimal.ONE, result);
    }

    @Test
    public void testGetEmploymentRateEmployed() throws ScoreDenyedException {
        EmploymentStatus status = EmploymentStatus.EMPLOYED;
        BigDecimal result = ScoringUtils.getEmploymentRate(status);
        Assertions.assertEquals(BigDecimal.ZERO, result);
    }

    @Test
    public void testGetEmploymentRateBusinessOwner() throws ScoreDenyedException {
        EmploymentStatus status = EmploymentStatus.BUSINESSOWNER;
        BigDecimal result = ScoringUtils.getEmploymentRate(status);
        Assertions.assertEquals(new BigDecimal("3"), result);
    }

    @Test
    public void testGetEmploymentRateUnemployed() {
        EmploymentStatus status = EmploymentStatus.UNEMPLOYED;
        Assertions.assertThrows(ScoreDenyedException.class,
                () -> ScoringUtils.getEmploymentRate(status));
    }

    @Test
    public void testGetPositionRateMidManager() {
        Position position = Position.MID_MANAGER;
        BigDecimal result = ScoringUtils.getPositionRate(position);
        Assertions.assertEquals(new BigDecimal("-2"), result);
    }

    @Test
    public void testGetPositionRateTopManager() {
        Position position = Position.TOPMANAGER;
        BigDecimal result = ScoringUtils.getPositionRate(position);
        Assertions.assertEquals(new BigDecimal("-4"), result);
    }


    @Test
    public void testGetMaritalStatusRateMarried() {
        MaritalStatus maritalStatus = MaritalStatus.MARRIED;
        BigDecimal result = ScoringUtils.getMaritalStatusRate(maritalStatus);
        Assertions.assertEquals(new BigDecimal("-3"), result);
    }

    @Test
    public void testGetMaritalStatusRateDivorced() {
        MaritalStatus maritalStatus = MaritalStatus.DIVORCED;
        BigDecimal result = ScoringUtils.getMaritalStatusRate(maritalStatus);
        Assertions.assertEquals(BigDecimal.ONE, result);
    }

    @Test
    public void testGetMaritalStatusRateDefault() {
        MaritalStatus maritalStatus = MaritalStatus.WIDOWED;
        BigDecimal result = ScoringUtils.getMaritalStatusRate(maritalStatus);
        Assertions.assertEquals(BigDecimal.ZERO, result);
    }


    @Test
    public void testGetGenderAndAgeRateMaleInRange() throws ScoreDenyedException {
        Gender gender = Gender.MALE;
        int age = 35;
        BigDecimal result = ScoringUtils.getGenderAndAgeRate(gender, age);
        Assertions.assertEquals(new BigDecimal("-3"), result);
    }

    @Test
    public void testGetGenderAndAgeRateMaleOutOfRange() {
        Gender gender = Gender.MALE;
        int age = 16;
        Assertions.assertThrows(ScoreDenyedException.class,
                () -> ScoringUtils.getGenderAndAgeRate(gender, age));
    }

    @Test
    public void testGetGenderAndAgeRateFemaleInRange() throws ScoreDenyedException {
        Gender gender = Gender.FEMALE;
        int age = 40;
        BigDecimal result = ScoringUtils.getGenderAndAgeRate(gender, age);
        Assertions.assertEquals(new BigDecimal("-3"), result);
    }

    @Test
    public void testGetGenderAndAgeRateFemaleOutOfRange() {
        Gender gender = Gender.FEMALE;
        int age = 17;
        Assertions.assertThrows(ScoreDenyedException.class,
                () -> ScoringUtils.getGenderAndAgeRate(gender, age));
    }

    @Test
    public void testGetGenderAndAgeRateOther() throws ScoreDenyedException {
        Gender gender = Gender.OTHER;
        int age = 50;
        BigDecimal result = ScoringUtils.getGenderAndAgeRate(gender, age);
        Assertions.assertEquals(BigDecimal.valueOf(3), result);
    }

}
