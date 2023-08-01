package com.example.conveyor.utils;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class PreScoringUtils {
    public static final Predicate<String> NAME_REGEXP = Pattern.compile("[a-zA-Z]{2,30}").asMatchPredicate();
    public static final Predicate<String> EMAIL_REGEXP = Pattern.compile("[\\w\\.]{2,50}@[\\w\\.]{2,20}").asMatchPredicate();
    public static final Predicate<String> PASSPORT_SERIAL_REGEXP = Pattern.compile("\\d{4}").asMatchPredicate();
    public static final Predicate<String> PASSPORT_NUMBER_REGEXP = Pattern.compile("\\d{6}").asMatchPredicate();
    public static final BigDecimal MIN_AMOUNT = BigDecimal.valueOf(10000);
    public static final Integer MIN_TERM = 6;
    public static final Long MIN_AGE = 18L;
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final  BigDecimal INSURANCE_PERCENT = BigDecimal.valueOf(.05);
    public static final BigDecimal SALARY_CLIENT_DISCOUNT =BigDecimal.valueOf(1);
    public static final BigDecimal DISCOUNT = BigDecimal.valueOf(4);
    public static final  BigDecimal INSURANCE_BASE = BigDecimal.valueOf(10000);
    public static final  BigDecimal BASE_LOAN = BigDecimal.valueOf(100000);
}
