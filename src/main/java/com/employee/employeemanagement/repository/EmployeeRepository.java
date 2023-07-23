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
    @Query("SELECT e FROM employee e " +
            "WHERE (:firstname IS NULL OR e.firstname ILIKE CONCAT('%', :firstname, '%')) " +
            "AND (:lastname IS NULL OR e.lastname ILIKE CONCAT('%', :lastname, '%')) " +
            "AND (:sexe IS NULL OR e.sexe = :sexe) " +
            "AND (:fonction IS NULL OR e.fonction ILIKE CONCAT('%', :fonction, '%')) " +
            "AND (:hireDateStart IS NULL OR e.hireDate >= :hireDateStart) " +
            "AND (:hireDateEnd IS NULL OR e.hireDate <= :hireDateEnd) " +
            "AND (:departureDateStart IS NULL OR e.departureDate >= :departureDateStart) " +
            "AND (:departureDateEnd IS NULL OR e.departureDate <= :departureDateEnd) " +
            "ORDER BY " +
            "   CASE WHEN :sortDirection = 'ASC' AND :sortField = 'name' THEN e.firstname END ASC, " +
            "   CASE WHEN :sortDirection = 'DESC' AND :sortField = 'name' THEN e.firstname END DESC, " +
            "   CASE WHEN :sortDirection = 'ASC' AND :sortField = 'firstname' THEN e.lastname END ASC, " +
            "   CASE WHEN :sortDirection = 'DESC' AND :sortField = 'firstname' THEN e.lastname END DESC, " +
            "   CASE WHEN :sortDirection = 'ASC' AND :sortField = 'sexe' THEN e.sexe END ASC, " +
            "   CASE WHEN :sortDirection = 'DESC' AND :sortField = 'sexe' THEN e.sexe END DESC, " +
            "   CASE WHEN :sortDirection = 'ASC' AND :sortField = 'fonction' THEN e.fonction END ASC, " +
            "   CASE WHEN :sortDirection = 'DESC' AND :sortField = 'fonction' THEN e.fonction END DESC, " +
            "   CASE WHEN :sortDirection = 'ASC' AND :sortField = 'hireDare' THEN e.hireDate END ASC, " +
            "   CASE WHEN :sortDirection = 'DESC' AND :sortField = 'hireDare' THEN e.hireDate END DESC, " +
            "   CASE WHEN :sortDirection = 'ASC' AND :sortField = 'departureDate' THEN e.departureDate END ASC, " +
            "   CASE WHEN :sortDirection = 'DESC' AND :sortField = 'departureDate' THEN e.departureDate END DESC")
    List<Employee> filterAndSortEmployees(@Param("firstname") String firstname,
                                          @Param("lastname") String lastname,
                                          @Param("sexe") Employee.Sexe sexe,
                                          @Param("fonction") String fonction,
                                          @Param("hireDateStart") LocalDate hireDateStart,
                                          @Param("hireDateEnd") LocalDate hireDateEnd,
                                          @Param("departureDateStart") LocalDate departureDateStart,
                                          @Param("departureDateEnd") LocalDate departureDateEnd,
                                          @Param("sortDirection") String sortDirection,
                                          @Param("sortField") String sortField);
}

