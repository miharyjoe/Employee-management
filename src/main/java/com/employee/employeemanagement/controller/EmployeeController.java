package com.employee.employeemanagement.controller;

import com.employee.employeemanagement.controller.mapper.EmployeeMapper;
import com.employee.employeemanagement.controller.utils.CSVExporter;
import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    @GetMapping("employees")
    public String showEmployees(Model model, HttpSession session, HttpServletResponse response) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // If the user is not authenticated, redirect to the login page
            return "redirect:/login";
        }
        // Set cache-control headers to prevent caching the page
        response.setHeader("Cache-Control", "no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("employees", allEmployees);
        return "employees";
    }

    @GetMapping("employees/filter")
    public String filterAndSortEmployees(@RequestParam(required = false) String firstname,
    @RequestParam(required = false) String lastname,
    @RequestParam(required = false) Employee.Sexe sexe,
    @RequestParam(required = false) String fonction,
    @RequestParam(required = false) LocalDate hire_date_start,
    @RequestParam(required = false) LocalDate hire_date_end,
    @RequestParam(required = false, defaultValue = "ASC") Sort.Direction sortDirection,
    @RequestParam(required = false, defaultValue = "firstname") String sortField,
    @RequestParam(required = false) String telephones,
    Model model, HttpSession session) {
    session.setAttribute("firstname", firstname);
    session.setAttribute("lastname", lastname);
    session.setAttribute("sexe", sexe);
    session.setAttribute("fonction", fonction);
    session.setAttribute("sortDirection", sortDirection);
    session.setAttribute("sortField", sortField);

        List<Employee> filteredEmployees =  employeeService.filterAndSortEmployees(
                firstname, lastname, sexe, fonction, hire_date_start, hire_date_end,
                sortDirection, sortField, telephones);

        model.addAttribute("employees", filteredEmployees);
        return "employee_filter";
    }

    @GetMapping("employees/filter/date")
    public String filterAndSortEmployees(
                                         @RequestParam(required = false) LocalDate hire_date_start,
                                         @RequestParam(required = false) LocalDate hire_date_end,Model model,HttpSession session){
        session.setAttribute("hire_date_start", hire_date_start);
        session.setAttribute("hire_date_end", hire_date_end);
        List<Employee> filteredEmployees =  employeeService.filterDate(hire_date_start,hire_date_end);
        model.addAttribute("employees", filteredEmployees);
        return "employeefilterdate";
    }
    @GetMapping("employees/filter/phone")
    public String filterAndSortEmployees(
                                         @RequestParam(required = false) String telephones ,Model model,HttpSession session){
        session.setAttribute("telephones", telephones);
        List<Employee> filteredEmployees =  employeeService.phoneFilter(telephones);
        model.addAttribute("employees", filteredEmployees);
        return "employeefilterphone";
    }

    @GetMapping("employee/new")
    public String addEmployee(Model model, HttpSession session, HttpServletResponse response){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // If the user is not authenticated, redirect to the login page
            return "redirect:/login";
        }
        // Set cache-control headers to prevent caching the page
        response.setHeader("Cache-Control", "no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        com.employee.employeemanagement.model.Employee employeeModel = new com.employee.employeemanagement.model.Employee();
        model.addAttribute("employee", employeeModel);
        return "form_employee";
    }
    @PostMapping("employee/add")
    public String saveEmployee(@RequestParam("image") MultipartFile imageFile, com.employee.employeemanagement.model.Employee employeeModel) throws IOException {

            employeeService.createEmployee(mapper.toRest(employeeModel));
        return "redirect:/employees";
    }

    @GetMapping("employee/about")
    public String moreInfo(Model model, @RequestParam("id") Long id, HttpSession session, HttpServletResponse response){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // If the user is not authenticated, redirect to the login page
            return "redirect:/login";
        }
        // Set cache-control headers to prevent caching the page
        response.setHeader("Cache-Control", "no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        Employee employee = employeeService.selectEmployee(id);
        model.addAttribute("employee",employee);
        return "info_employee";
    }

    @GetMapping("employee/update")
    public String upEmployee(Model model, @RequestParam("id") Long id, HttpSession session, HttpServletResponse response){
        String username = (String) session.getAttribute("username");
        if (username == null) {
            // If the user is not authenticated, redirect to the login page
            return "redirect:/login";
        }
        // Set cache-control headers to prevent caching the page
        response.setHeader("Cache-Control", "no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        Employee employee = employeeService.selectEmployee(id);
        model.addAttribute("employee",employee);
        return "update_employee";
    }
    @PostMapping("employee/save")
    public String updateEmployee(@RequestParam("image") MultipartFile imageFile, com.employee.employeemanagement.model.Employee employee) throws IOException {
            employeeService.updateInfoEmployee(mapper.toUpdate(employee));
        Long id = employeeService.selectEmployee(employee.getId()).getId();
        return "redirect:/employee/about?id="+id;
    }

    @GetMapping("/employees/export/csv")
    @ResponseBody
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeService.getAllEmployees();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employees.csv\"");

        CSVExporter.exportEmployeesToCSV(response.getWriter(), employees);
    }
    @GetMapping("/employees/filter/export/csv")
    public void exportFilterToCSV(
                                  HttpServletResponse response, HttpSession session) throws IOException {
        String firstname = (String) session.getAttribute("firstname");
        String lastname = (String)session.getAttribute("lastname");
        String telephones = (String)session.getAttribute("telephones");
        Employee.Sexe sexe = (Employee.Sexe)session.getAttribute("sexe");
        String fonction = (String)session.getAttribute("fonction");
        LocalDate hire_date_start = (LocalDate) session.getAttribute("hire_date_start");
        LocalDate hire_date_end = (LocalDate) session.getAttribute("hire_date_end");
        Sort.Direction sortDirection = (Sort.Direction) session.getAttribute("sortDirection");
        String sortField = (String) session.getAttribute("sortField");

        List<Employee> filteredEmployees = employeeService.filterAndSortEmployees(
                firstname, lastname, sexe, fonction, hire_date_start, hire_date_end,
                sortDirection, sortField, telephones);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employeesfilter.csv\"");

       CSVExporter.exportEmployeesToCSV(response.getWriter(), filteredEmployees);
    }

    @GetMapping("/employees/filter/phone/export/csv")
    public void exportFilterPhoneToCSV(
            HttpServletResponse response, HttpSession session) throws IOException {
        String firstname = (String) session.getAttribute("firstname");
        String lastname = (String)session.getAttribute("lastname");
        String telephones = (String)session.getAttribute("telephones");
        Employee.Sexe sexe = (Employee.Sexe)session.getAttribute("sexe");
        String fonction = (String)session.getAttribute("fonction");
        LocalDate hire_date_start = (LocalDate) session.getAttribute("hire_date_start");
        LocalDate hire_date_end = (LocalDate) session.getAttribute("hire_date_end");
        Sort.Direction sortDirection = (Sort.Direction) session.getAttribute("sortDirection");
        String sortField = (String) session.getAttribute("sortField");

            List<Employee> filteredEmployees =  employeeService.phoneFilter(telephones);


        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employeesfilter.csv\"");

        CSVExporter.exportEmployeesToCSV(response.getWriter(), filteredEmployees);
    }
  @GetMapping("/employees/filter/date/import/csv")
    public void exportFilterdateToCSV(
            HttpServletResponse response, HttpSession session) throws IOException {
        String firstname = (String) session.getAttribute("firstname");
        String lastname = (String)session.getAttribute("lastname");
        String telephones = (String)session.getAttribute("telephones");
        Employee.Sexe sexe = (Employee.Sexe)session.getAttribute("sexe");
        String fonction = (String)session.getAttribute("fonction");
        LocalDate hire_date_start = (LocalDate) session.getAttribute("hire_date_start");
        LocalDate hire_date_end = (LocalDate) session.getAttribute("hire_date_end");
        Sort.Direction sortDirection = (Sort.Direction) session.getAttribute("sortDirection");
        String sortField = (String) session.getAttribute("sortField");


      List<Employee> filteredEmployees =  employeeService.filterDate(hire_date_start,hire_date_end);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"employeesfilter.csv\"");

        CSVExporter.exportEmployeesToCSV(response.getWriter(), filteredEmployees);
    }
}
