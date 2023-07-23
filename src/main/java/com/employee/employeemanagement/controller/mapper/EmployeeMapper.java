package com.employee.employeemanagement.controller.mapper;

import com.employee.employeemanagement.entity.Employee;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Component
public class EmployeeMapper {
    public Employee toRest(com.employee.employeemanagement.model.Employee domain) throws IOException {
        MultipartFile file = domain.getImage();
        byte[] bytes = file.getBytes();
        String image = Base64.getEncoder().encodeToString(bytes);
        return Employee.builder()
                .firstname(domain.getFirstname())
                .lastname(domain.getLastname())
                .birthDate(domain.getBirthDate())
                .matricule(domain.getMatricule())
                .image(image)
                .address(domain.getAddress())
                .emailPerso(domain.getEmailPerso())
                .emailPro(domain.getEmailPro())
                .hireDate(domain.getHireDate())
                .departureDate(domain.getDepartureDate())
                .childrenCharge(domain.getChildrenCharge())
                .cinNumber(domain.getCinNumber())
                .cinDate(domain.getCinDate())
                .fonction(domain.getFonction())
                .csp(domain.getCsp())
                .placeCin(domain.getPlaceCin())
                .numCnaps(domain.getNumCnaps())
                .sexe(domain.getSexe())
                .telephones(domain.getTelephones())
                .build();
    }


    public Employee toUpdate(com.employee.employeemanagement.model.Employee domain) throws IOException {
        MultipartFile file = domain.getImage();
        byte[] bytes = file.getBytes();
        String image = Base64.getEncoder().encodeToString(bytes);
        String mat = domain.getMatricule().substring(4);
        return Employee.builder()
                .id(domain.getId())
                .firstname(domain.getFirstname())
                .lastname(domain.getLastname())
                .birthDate(domain.getBirthDate())
                .matricule(mat)
                .image(image)
                .address(domain.getAddress())
                .emailPerso(domain.getEmailPerso())
                .emailPro(domain.getEmailPro())
                .hireDate(domain.getHireDate())
                .departureDate(domain.getDepartureDate())
                .childrenCharge(domain.getChildrenCharge())
                .cinNumber(domain.getCinNumber())
                .cinDate(domain.getCinDate())
                .fonction(domain.getFonction())
                .csp(domain.getCsp())
                .placeCin(domain.getPlaceCin())
                .numCnaps(domain.getNumCnaps())
                .sexe(domain.getSexe())
                .telephones(domain.getTelephones())
                .build();
    }
}
