package com.employee.employeemanagement.controller;

import com.employee.employeemanagement.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class AuthController {
    private final UserService authService;

        @GetMapping("/login")
        public String showLoginForm(Model model) {
            return "index";
        }

        @PostMapping("/login")
        public String processLoginForm(@RequestParam String username, @RequestParam String password, HttpSession session) {
            if (authService.authenticate(username, password)) {
                // Create a session attribute to store the authenticated user's username
                session.setAttribute("username", username);
                return "redirect:/employees";
            } else {
                return "index";
            }
        }

        @GetMapping("/logout")
        public String logout(HttpSession session) {
            // Invalidate the session on logout and remove
            session.invalidate();
            session.removeAttribute("username");
            return "redirect:/login";
        }

}
