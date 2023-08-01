package com.example.conveyor.dto;

import com.example.conveyor.dto.enums.EmploymentStatus;
import com.example.conveyor.dto.enums.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class EmploymentDTOTest {

    @Test
    public void testEmploymentDTOGetterAndSetter() {
        EmploymentStatus employmentStatus = EmploymentStatus.EMPLOYED;
        String employerINN = "123456789";
        BigDecimal salary = new BigDecimal("50000");
        Position position = Position.OWNER;
        Integer workExperienceTotal = 5;
        Integer workExperienceCurrent = 3;


        EmploymentDTO employmentDTO = new EmploymentDTO();
        employmentDTO.setEmploymentStatus(employmentStatus);
        employmentDTO.setEmployerINN(employerINN);
        employmentDTO.setSalary(salary);
        employmentDTO.setPosition(position);
        employmentDTO.setWorkExperienceTotal(workExperienceTotal);
        employmentDTO.setWorkExperienceCurrent(workExperienceCurrent);

        // Assert
        Assertions.assertEquals(employmentStatus, employmentDTO.getEmploymentStatus());
        Assertions.assertEquals(employerINN, employmentDTO.getEmployerINN());
        Assertions.assertEquals(salary, employmentDTO.getSalary());
        Assertions.assertEquals(position, employmentDTO.getPosition());
        Assertions.assertEquals(workExperienceTotal, employmentDTO.getWorkExperienceTotal());
        Assertions.assertEquals(workExperienceCurrent, employmentDTO.getWorkExperienceCurrent());
    }

    @Test
    public void testEmploymentDTOToString() {
        EmploymentStatus employmentStatus = EmploymentStatus.EMPLOYED;
        String employerINN = "987654321";
        BigDecimal salary = new BigDecimal("75000");
        Position position = Position.OWNER;
        Integer workExperienceTotal = 10;
        Integer workExperienceCurrent = 8;

        EmploymentDTO employmentDTO = new EmploymentDTO();
        employmentDTO.setEmploymentStatus(employmentStatus);
        employmentDTO.setEmployerINN(employerINN);
        employmentDTO.setSalary(salary);
        employmentDTO.setPosition(position);
        employmentDTO.setWorkExperienceTotal(workExperienceTotal);
        employmentDTO.setWorkExperienceCurrent(workExperienceCurrent);

        String str = employmentDTO.toString();

        // Assert
        String expectedString = "EmploymentDTO(employmentStatus=EMPLOYED, employerINN=987654321, salary=75000, position=OWNER, workExperienceTotal=10, workExperienceCurrent=8)";
        Assertions.assertEquals(expectedString, str);
    }
}

