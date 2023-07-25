package com.employee.employeemanagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "employee")
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_sequence")
    @SequenceGenerator(name = "employee_sequence", sequenceName = "hibernate_sequence", allocationSize = 1)
    @Column(name = "id")
    private  Long id;

    @Column(nullable = false)
    private String firstname;

    @Column(nullable = false)
    private String lastname;

    @Column(name = "birthdate")
    private LocalDate birthDate;

    @Column(unique = true, nullable = false)
    private String matricule;

    @Column(nullable = true, columnDefinition = "Text")
    private String image;

    @Enumerated(EnumType.STRING)
    private Sexe sexe;


    @ElementCollection(targetClass = String.class)
    @CollectionTable(
            name = "employee_telephones",
            joinColumns = @JoinColumn(name = "employee_id")
    )
    @Column(name = "telephones", unique = true)
    private List<String> telephones;

    @Column(nullable = false)
    private String address;

    @Column(name = "email_perso")
    private String emailPerso;

    @Column(name = "email_pro")
    private String emailPro;

    @Column(nullable = false, name = "cin_number")
    private Long cinNumber;

    @Column(nullable = false, name = "cin_date")
    private LocalDate cinDate;

    @Column(nullable = false,name = "place_cin")
    private String placeCin;

    @Column(nullable = false)
    private String fonction;

    @Column(name = "children_charge")
    private int childrenCharge;

    @Column(nullable = false, name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "departure_date")
    private LocalDate departureDate;

    private Csp csp;

    @Column(name = "num_cnaps")
    private String numCnaps;

    public enum Sexe {
        H,
        F
    }

    public enum  Csp {
        M1, M2, OS1, OS2, OS3, OP1
    }

    public String getMatricule() {
        return "EMPL" + matricule;
    }
    public void setMatricule(String matricule) {
        // Remove the "EMPL" prefix from the matricule value
        this.matricule = matricule.substring(4) ;
    }
}
