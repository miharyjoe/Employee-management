package com.employee.employeemanagement.service;

import com.employee.employeemanagement.entity.User;
import com.employee.employeemanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }
}
