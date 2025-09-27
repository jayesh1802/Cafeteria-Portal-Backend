package com.dau.cafeteria_portal.controller;
import com.dau.cafeteria_portal.dto.LoginRequest;
import com.dau.cafeteria_portal.dto.SignUpRequestDTO;
import com.dau.cafeteria_portal.entity.User;
import com.dau.cafeteria_portal.enums.Role;
import com.dau.cafeteria_portal.service.impl.UserServiceImpl;
import com.dau.cafeteria_portal.util.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication API", description = "APIs for user registration and login")
public class UserAuthController{

    private final UserServiceImpl userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    @Autowired
    public UserAuthController(UserServiceImpl userService,
                              UserDetailsService userDetailsService,
                              AuthenticationManager authenticationManager,
                              JWTUtil jwtUtil) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    @Operation(
            summary = "Register a new user",
            description = "Registers a user with default role USER",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = SignUpRequestDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully"),
                    @ApiResponse(responseCode = "400", description = "Invalid request")
            }
    )
    public ResponseEntity<String> register(@RequestBody User user) {
        // default role
        user.setUserRole(Role.USER);
        userService.save(user);
        return ResponseEntity.ok("User Registered Successfully");
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login user",
            description = "Authenticate user and return JWT token",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User login data",
                    required = true,
                    content = @Content(schema = @Schema(implementation = LoginRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Login successful"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content)
            }
    )
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // here authRequest.userName = email
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getStudentId(), loginRequest.getPassword())
            );

            // load user by email
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getStudentId());
            String token = jwtUtil.generateToken(userDetails.getUsername(), 60);

            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "studentId", userDetails.getUsername(),
                    "roles", userDetails.getAuthorities()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}