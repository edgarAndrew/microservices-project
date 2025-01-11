package com.userService.controller;

import com.userService.dto.GetUserResponse;
import com.userService.dto.UserLoginResponse;
import com.userService.dto.UserRegisterationResponse;
import com.userService.dto.UserRegistrationRequest;
import com.userService.exception.UserNotFoundException;
import com.userService.model.User;
import com.userService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/role/{role}")
    public List<User> getUsersByRole(@PathVariable String role) {
        return userService.getUsersByRole(role);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }


    @PostMapping("/register")
    public ResponseEntity<UserRegisterationResponse> registerUser(@RequestBody UserRegistrationRequest request) {
        String name = request.getName();
        String email = request.getEmail();
        String password = request.getPassword();
        String role = request.getRole();

        userService.registerUser(name, email, password, role);
        logger.info("user with email:" + email+" , role: "+role+" has been registered");
        return ResponseEntity.ok(new UserRegisterationResponse("user registered")) ;
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> loginUser(@RequestParam String email, @RequestParam String password) {
        String[] res =  userService.loginUser(email, password).split(",");
        logger.info("user with email:" + email+" has logged in");
        return ResponseEntity.ok(new UserLoginResponse(res[0],res[1],userService.getUserByEmail(email).getId())) ;
    }

    @GetMapping
    public ResponseEntity<GetUserResponse> loginUser(@RequestParam Long userId) {
        Optional<User> res =  userService.getUserById(userId);
        if(res.isPresent()){
            return ResponseEntity.ok(new GetUserResponse(res.get().getRole(),res.get().getName(),res.get().getEmail())) ;
        } else{
            throw new UserNotFoundException("No User with provided Id");
        }
    }
}
