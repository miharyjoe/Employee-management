package com.employee.employeemanagement.repository;

import com.employee.employeemanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee " +
            "WHERE (:firstname IS NULL OR LOWER(employee.firstname) ILIKE LOWER(CONCAT('%', COALESCE(:firstname, ''), '%'))) " +
            "AND (:lastname IS NULL OR LOWER(employee.lastname) ILIKE LOWER(CONCAT('%', COALESCE(:lastname, ''), '%'))) " +
            "AND (:sexe IS NULL OR employee.sexe = :sexe) " +
            "AND (:fonction IS NULL OR LOWER(employee.fonction) ILIKE LOWER(CONCAT('%', COALESCE(:fonction, ''), '%'))) ",
            nativeQuery = true)
    List<Employee> filterEmployees(
            @Param("firstname") String firstname,
            @Param("lastname") String lastname,
            @Param("sexe") String sexe,
            @Param("fonction") String fonction
    );
    List<Employee> findByHireDateBetween(LocalDate hire_date_start, LocalDate hire_date_end);

    List<Employee> findEmployeeByTelephonesContainingIgnoreCase(String telephones);

}


