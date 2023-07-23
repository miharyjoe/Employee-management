package com.employee.employeemanagement.service;

import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public List<Employee> filterAndSortEmployees(String firstname, String lastname, Employee.Sexe sexe, String fonction,
                                                 LocalDate hireDateStart, LocalDate hireDateEnd,
                                                 LocalDate departureDateStart, LocalDate departureDateEnd,
                                                 Sort.Direction sortDirection, String sortField) {
        return repository.filterAndSortEmployees(firstname, lastname, sexe, fonction,
                hireDateStart, hireDateEnd, departureDateStart, departureDateEnd,
                sortDirection.name(), sortField);
    }

    public Employee createEmployee(Employee tocreate){
       return  repository.save(tocreate);
    }

    public Employee selectEmployee(Long id){
        Optional<Employee> employee = repository.findById(id);
        if(employee.isPresent()){
            Employee result = employee.get();
            return result;
        }
        return new Employee();
    }

    public Employee updateInfoEmployee(Employee toUpdate){
        return repository.save(toUpdate);
    }
}
