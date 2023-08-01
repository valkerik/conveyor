package com.example.conveyor.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class LoanApplicationRequestDTO {

    @NonNull
    @NotEmpty(message = "should not be empty")
    @Min(value = 10000, message = "the amount mustn`t be less than 10000")
    private BigDecimal amount;

    @NonNull
    @NotEmpty(message = "should not be empty")
    @Min(value = 6, message = "the term mustn`t be less than 6")
    @Pattern(regexp = "^[1-9]\\d*$")
    private Integer term;

    @NotEmpty(message = "should not be empty")
    @Pattern(regexp = "[a-zA-Z]{2,30}")
    private String lastName;

    @NonNull
    @NotEmpty(message = "should not be empty")
    @Pattern(regexp = "[a-zA-Z]{2,30}")
    private String firstName;


    @Pattern(regexp = "[a-zA-Z]{0,30}")
    private String middleName;

    @NonNull
    @Email
    @NotEmpty(message = "should not be empty")
    @Pattern(regexp = "[\\w.]{2,50}@[\\w.]{2,20}",
            message = "Please provide a valid email address")
    private String email;

    @NonNull
    @NotEmpty(message = "should not be empty")
    @Past(message = "The birthday must be in the past")
    @DateTimeFormat(pattern = "yyy-MM-dd")
    @Pattern(regexp = "^(19|20)\\d\\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$")
    private LocalDate birthDate;

    @NonNull
    @Pattern(regexp = "\\d{4}", message = "this field must consist of 4 numbers")
    private String passportSeries;

    @NonNull
    @Pattern(regexp = "\\d{6}", message = "this field must consist of 6 numbers")
    private String passportNumber;
}
