package com.userService.service;

import com.userService.exception.DuplicateUserException;
import com.userService.exception.InvalidCredentialsException;
import com.userService.exception.InvalidRoleException;
import com.userService.exception.UserNotFoundException;
import com.userService.model.User;
import com.userService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Value("${user.admin.key}")
    private String adminKey;

    @Value("${user.teacher.key}")
    private String teacherKey;

    @Value("${user.student.key}")
    private String studentKey;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void registerUser(String name, String email, String password, String role) {

        if (userRepository.findByEmail(email) != null) {
            throw new DuplicateUserException("Email already exists");
        }
        if (!isValidRole(role)) {
            throw new InvalidRoleException("Role can only be 'STUDENT' or 'TEACHER'");
        }

        userRepository.save(new User(name, email, password, role));
    }

    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (!Objects.equals(user.getPassword(), password)) {
            throw new InvalidCredentialsException("Invalid email or password");
        }
        return switch (user.getRole()) {
            case "ADMIN" -> adminKey + ","+user.getRole();
            case "TEACHER" -> teacherKey+ ","+user.getRole();
            case "STUDENT" -> studentKey+ ","+user.getRole();
            default -> throw new InvalidRoleException("Invalid user role");
        };
    }

    private boolean isValidRole(String role) {
        return Objects.equals(role, "ADMIN") || Objects.equals(role, "STUDENT") || Objects.equals(role, "TEACHER");
    }
}


