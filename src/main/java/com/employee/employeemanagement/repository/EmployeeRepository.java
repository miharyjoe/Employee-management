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
            "WHERE (:firstname IS NULL OR LOWER(employee.firstname) ILIKE LOWER(CONCAT('%', :firstname, '%'))) " +
            "AND (:lastname IS NULL OR LOWER(employee.lastname) ILIKE LOWER(CONCAT('%', :lastname, '%'))) " +
            "AND (:sexe IS NULL OR employee.sexe = :sexe) " +
            "AND (:fonction IS NULL OR LOWER(employee.fonction) ILIKE LOWER(CONCAT('%', :fonction, '%'))) " +
            "AND (:hire_date_start IS NULL OR :hire_date_end IS NULL OR employee.hire_date BETWEEN :hire_date_start AND :hire_date_end) ",
            nativeQuery = true)
    List<Employee> filterEmployees(
           @Param("firstname") String firstname,
           @Param("lastname")String lastname,
           String sexe,
           String fonction,
           @Param("hire_date_start") LocalDate hire_date_start,
           @Param("hire_date_end")LocalDate hire_date_end
    );

}



