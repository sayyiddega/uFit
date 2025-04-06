package com.chencorp.ufit.service.Account;

import com.chencorp.ufit.model.Account;
import com.chencorp.ufit.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AllAcoount {

    @Autowired
    private AccountRepository accountRepository;

    // Method to get all accounts
    public ResponseEntity<Object> getAll() {
        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            return buildErrorResponse("No accounts found");
        }

        // Return success response with the list of accounts
        return buildSuccessResponse(accounts);
    }

    // Method to get a single account by ID
    public ResponseEntity<Object> getById(Integer id) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            return buildSuccessResponse(account.get());
        } else {
            return buildErrorResponse("Account not found");
        }
    }

    // Build a success response
    private ResponseEntity<Object> buildSuccessResponse(Object data) {
        return ResponseEntity.ok(new ApiResponse("success", data));
    }

    // Build an error response
    private ResponseEntity<Object> buildErrorResponse(String message) {
        return ResponseEntity.status(204).body(new ApiResponse("error", message)); // Use 204 for empty data
    }

    // ApiResponse class for consistent API response format
    public static class ApiResponse {
        private String status;
        private Object data;

        public ApiResponse(String status, Object data) {
            this.status = status;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }
    }
}
