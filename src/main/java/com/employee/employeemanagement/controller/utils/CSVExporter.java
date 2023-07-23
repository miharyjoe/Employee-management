package com.employee.employeemanagement.controller.utils;

import com.employee.employeemanagement.entity.Employee;

import java.io.PrintWriter;
import java.util.List;

public class CSVExporter {
    private static final String CSV_SEPARATOR = ",";

    public static void exportEmployeesToCSV(PrintWriter writer, List<Employee> employees) {
        // Write CSV header
        writer.println("First Name,Last Name,Sexe,Fonction,Hire Date,Matricule,Phone Number");
        // Write employee data
        for (Employee employee : employees) {
            writer.println(
                    employee.getFirstname() + CSV_SEPARATOR +
                            employee.getLastname() + CSV_SEPARATOR +
                            employee.getSexe() + CSV_SEPARATOR +
                            employee.getFonction() + CSV_SEPARATOR +
                            employee.getHireDate() + CSV_SEPARATOR +
                            employee.getMatricule() + CSV_SEPARATOR +
                            employee.getTelephones()
            );
        }
    }
}
