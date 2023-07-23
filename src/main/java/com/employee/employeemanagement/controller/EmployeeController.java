package com.employee.employeemanagement.controller;

import com.employee.employeemanagement.controller.mapper.EmployeeMapper;
import com.employee.employeemanagement.controller.utils.CSVExporter;
import com.employee.employeemanagement.entity.Employee;
import com.employee.employeemanagement.service.EmployeeService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/")
@CrossOrigin
public class EmployeeController {
    private final EmployeeService employeeService;
    private final EmployeeMapper mapper;

    @GetMapping("employees")
    public String showEmployees(Model model) {
        List<Employee> allEmployees = employeeService.getAllEmployees();
        model.addAttribute("employees", allEmployees);
        return "employees";
    }

    // Mapping to handle the filter form submission
    @GetMapping("employees/filter")
    public String filterAndSortEmployees(@RequestParam(required = false) String firstname,
                                         @RequestParam(required = false) String lastname,
                                         @RequestParam(required = false) Employee.Sexe sexe,
                                         @RequestParam(required = false) String fonction,
                                         @RequestParam(required = false) LocalDate hireDateStart,
                                         @RequestParam(required = false) LocalDate hireDateEnd,
                                         @RequestParam(required = false) LocalDate departureStart,
                                         @RequestParam(required = false) LocalDate departureEnd,
                                         @RequestParam(required = false) Sort.Direction sortDirection,
                                         @RequestParam(defaultValue = "id") String sortField,
                                         Model model) {
        List<Employee> filteredEmployees = employeeService.filterAndSortEmployees(firstname, lastname, sexe, fonction,
                hireDateStart, hireDateEnd, departureStart, departureEnd, sortDirection, sortField);
        model.addAttribute("employees", filteredEmployees);
        return "employees";
    }

    @GetMapping("employee/new")
    public String addEmployee(Model model){
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
    public String moreInfo(Model model, @RequestParam("id") Long id){
        Employee employee = employeeService.selectEmployee(id);
        model.addAttribute("employee",employee);
        return "info_employee";
    }

    @GetMapping("employee/update")
    public String upEmployee(Model model, @RequestParam("id") Long id){
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
}
