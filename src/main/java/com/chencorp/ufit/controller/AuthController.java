package com.chencorp.ufit.controller;


import com.chencorp.ufit.service.AuthService;
import com.chencorp.ufit.service.RegisterAccount;

import lombok.Data;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RegisterAccount registerAccount;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return token != null ? token : "Login gagal";
    }

    @GetMapping("/check")
    public String checkToken(@RequestParam String token) {
        return authService.isTokenValid(token) ? "Token aktif" : "Token tidak valid";
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountRequest request) {
        String response = registerAccount.register(
            request.getUsername(),
            request.getPassword(),
            request.getNama_depan(),
            request.getNama_belakang(),
            request.getGender(),
            request.getBirthdate(),
            request.getBirthplace(),
            request.getPhone(),
            request.getEmail()
        );

        if (response.contains("error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @Data
    public static class LoginRequest {
        private String username;
        private String password;
    }

    @Data
    public static class AccountRequest {
        private String username;
        private String password;
        private String nama_depan;
        private String nama_belakang;
        private String gender;
        private LocalDate birthdate;
        private String birthplace;
        private String phone;
        private String email;
        private String user;
    }
}