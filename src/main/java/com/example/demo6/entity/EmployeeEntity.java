package com.example.demo6.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Data
@NoArgsConstructor
public class EmployeeEntity {
    @Id
    @NotBlank(message = "documentNumber is mandatory")
    @NotNull(message = "documentNumber is mandatory")
    @Column(name = "document_number", nullable = false)
    private String documentNumber;

    @NotEmpty(message = "name is mandatory")
    @NotNull(message = "name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @NotBlank(message = "lastName is mandatory")
    @NotNull(message = "lastName is mandatory")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "documentType is mandatory")
    @NotNull(message = "documentType is mandatory")
    @Column(name = "document_type", nullable = false)
    private String documentType;

    @NotNull(message = "birthday is mandatory")
    @DateTimeFormat(pattern="yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "birthday", nullable = false)
    private Date birthday;

    @NotNull(message = "linkingDate is mandatory")
    @DateTimeFormat(pattern="yyyy-MM-dd", iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    @Column(name = "linking_date", nullable = false)
    private Date linkingDate;

    @NotBlank(message = "role is mandatory")
    @NotNull(message = "role is mandatory")
    @Column(name = "role", nullable = false)
    private String role;

    @Min(value = 1, message = "salary has to be > 0")
    @NotNull(message = "salary is mandatory")
    @Column(name = "salary", nullable = false)
    private Double salary;
}
