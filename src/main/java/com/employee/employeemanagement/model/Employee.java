package com.employee.employeemanagement.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class Employee {
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthDate;
    private String matricule;
    private MultipartFile image;
    private com.employee.employeemanagement.entity.Employee.Sexe sexe;
    private List<String> telephones;
    private String address;
    private String emailPerso;
    private String emailPro;
    private Long cinNumber;
    private LocalDate cinDate;
    private String placeCin;
    private String fonction;
    private int childrenCharge;
    private LocalDate hireDate;
    private LocalDate departureDate;
    private com.employee.employeemanagement.entity.Employee.Csp csp;
    private String numCnaps;
}
