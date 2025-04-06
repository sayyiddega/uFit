package com.chencorp.ufit.controller;


import com.chencorp.ufit.service.Account.AllAcoount;
import com.chencorp.ufit.service.Account.RegisterAccount;
import com.chencorp.ufit.service.Account.UpdateAccount;
import com.chencorp.ufit.service.Auth.AuthService;
import com.chencorp.ufit.service.Auth.LogoutService;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;


@RestController
@RequestMapping("/service")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RegisterAccount registerAccount;

    @Autowired
    private LogoutService logoutService;

    @Autowired
    private UpdateAccount updateAccount;

    @Autowired
    private AllAcoount allAcoount;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String token = authService.login(request.getUsername(), request.getPassword());
        return token != null ? token : "Login gagal";
    }

    @GetMapping("/check")
    public String checkToken(@RequestParam String token) {
        return authService.isTokenValid(token) ? "Token aktif" : "Token tidak valid";
    }

    @PostMapping("/Logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequest request) {
        String response = logoutService.logout(
            request.getUsername()
        );

        if (response.contains("error")) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    // ACCOUNT SECTION
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

    @PostMapping("/update_account")
    public ResponseEntity<?> updateAccount(@RequestBody AccountRequest request) {
        String response = updateAccount.updateAccount(
            request.getUsername(),
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

    @GetMapping("/getall_account")
    public ResponseEntity<?> getall_account() {
        return allAcoount.getAll(); // Directly call service's getAll() method
    }

    @GetMapping("/account/{id}")
    public ResponseEntity<Object> getAccountById(@PathVariable Integer id) {
        return allAcoount.getById(id);
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
    }

    @Data
    public static class LogoutRequest {
        private String username;
    }
}
