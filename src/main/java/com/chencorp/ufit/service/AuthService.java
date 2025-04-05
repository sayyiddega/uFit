package com.chencorp.ufit.service;

import com.chencorp.ufit.model.Token;
import com.chencorp.ufit.model.User;
import com.chencorp.ufit.service.HashedPassword;
import com.chencorp.ufit.repository.TokenRepository;
import com.chencorp.ufit.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    
    @Autowired
    private HashedPassword hashedPassword;


    public String login(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        
        if (optionalUser.isEmpty()) {
            return buildErrorResponse("User not found");
        }

        User user = optionalUser.get();

        String hashedPasswordValue = hashedPassword.hashPassword(password);       

        if (!user.getPassword().equals(hashedPasswordValue)) {
            return buildErrorResponse("Invalid password");
        }
        if (user.getActive() == null || user.getActive() == 0) {
            return buildErrorResponse("User is inactive");
        }
        if (user.getEndDt() != null) {
            return buildErrorResponse("User account expired");
        }

        // Simpan token
        String tokenStr = UUID.randomUUID().toString();
        Token token = new Token();
        token.setUser(user);
        token.setToken(tokenStr);

        // Mendapatkan timestamp saat ini
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Menambahkan 1 hari
        LocalDateTime nextDay = currentDateTime.plusDays(1);

        // Set inactive to nextDay (LocalDateTime)
        token.setInactive(nextDay);

        // Simpan token ke repository
        tokenRepository.save(token);

        // Membuat objek response untuk dikembalikan dalam format JSON
        TokenResponse response = new TokenResponse(tokenStr, nextDay);

        // Mengonversi objek menjadi JSON
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return buildSuccessResponse(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse("Failed to generate token");
        }
    }

    public boolean isTokenValid(String tokenStr) {
        return tokenRepository.findByTokenAndInactiveIsNull(tokenStr).isPresent();
    }

    // Kelas untuk response JSON
    public static class TokenResponse {
        private String token;
        private String inactive;

        public TokenResponse(String token, LocalDateTime inactive) {
            this.token = token;
            // Convert LocalDateTime to ISO-8601 string format
            this.inactive = inactive.toString();
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getInactive() {
            return inactive;
        }

        public void setInactive(String inactive) {
            this.inactive = inactive;
        }
    }

    // Helper method to build success response
    private String buildSuccessResponse(String data) {
        return "{ \"status\": \"success\", \"data\": " + data + " }";
    }

    // Helper method to build error response
    private String buildErrorResponse(String message) {
        return "{ \"status\": \"error\", \"message\": \"" + message + "\" }";
    }
}
