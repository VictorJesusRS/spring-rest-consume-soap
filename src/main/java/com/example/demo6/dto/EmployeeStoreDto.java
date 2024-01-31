package com.example.demo6.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Getter
@Setter
public class EmployeeStoreDto {
    private String documentNumber;
    private String name;
    private String lastName;
    private String documentType;
    private Date birthday;
    private Date linkingDate;
    private String role;
    private Double salary;
}
