package com.employee.employeemanagement.service;

import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public List<Employee> filterAndSortEmployees(String firstname, String lastname, Employee.Sexe sexe,
                                                 String fonction, LocalDate hire_date_start, LocalDate hire_date_end,
                                                 Sort.Direction sortDirection, String sortField, String telephones) {

        List<Employee> filteredEmployees = new ArrayList<>();
            filteredEmployees = repository.filterEmployees(
                    firstname, lastname, sexe != null ? sexe.toString() : null, fonction);
        if (sortField != null && !sortField.isEmpty()) {
            filteredEmployees = sortEmployees(filteredEmployees, sortDirection, sortField);
        }

        return filteredEmployees;
    }
    public List<Employee> phoneFilter(String telephones){
        List<Employee> employees = repository.findEmployeeByTelephonesContainingIgnoreCase(telephones);
        return  employees;
    }
    public List<Employee> filterDate(LocalDate hire_date_start, LocalDate hire_date_end){
        List<Employee> employees = repository.findByHireDateBetween(hire_date_start, hire_date_end );
        return  employees;
    }
    public List<Employee> sortEmployees(List<Employee> employees, Sort.Direction sortDirection, String sortField) {
        Comparator<Employee> comparator = getComparator(sortDirection, sortField);
        employees.sort(comparator);
        return employees;
    }
    private Comparator<Employee> getComparator(Sort.Direction sortDirection, String sortField) {
        Comparator<Employee> comparator;
        switch (sortField) {
            case "firstname":
                comparator = Comparator.comparing(Employee::getFirstname);
                break;
            case "lastname":
                comparator = Comparator.comparing(Employee::getLastname);
                break;
            case "sexe":
                comparator = Comparator.comparing(Employee::getSexe);
                break;
            case "fonction":
                comparator = Comparator.comparing(Employee::getFonction);
                break;
            case "hireDate":
                comparator = Comparator.comparing(Employee::getHireDate);
                break;
            case "matricule":
                comparator = Comparator.comparing(Employee::getMatricule);
                break;
            default:
                comparator = Comparator.comparing(Employee::getFirstname);
        }

        if (sortDirection == Sort.Direction.DESC) {
            comparator = comparator.reversed();
        }

        return comparator;
    }
    public Employee createEmployee(Employee tocreate) throws IOException {
            return repository.save(tocreate);
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
