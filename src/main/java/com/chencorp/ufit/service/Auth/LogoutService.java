package com.chencorp.ufit.service.Auth;

import com.chencorp.ufit.model.Token;
import com.chencorp.ufit.model.User;
import com.chencorp.ufit.repository.TokenRepository;
import com.chencorp.ufit.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogoutService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    public String logout(String username) {
        // Cek apakah pengguna ada di database
        Optional<User> optionalUser = userRepository.findByUsername(username);
        
        if (optionalUser.isEmpty()) {
            return buildErrorResponse("User not found");
        }

        User user = optionalUser.get();
        
        // Set status login pengguna menjadi 0
        user.setLogin(0);
        int userID = user.getId();
        userRepository.save(user);

        if (user.getLogin() != 0) {
            return buildErrorResponse("Status Login Gagal Di Reset");
        }

        // Ambil semua token yang terkait dengan user
        List<Token> tokens = tokenRepository.findAllByUser(user);
        
        if (!tokens.isEmpty()) {
            // Jika ada token, hapus semua token untuk user tersebut
            tokenRepository.deleteAll(tokens);
            System.out.println("Semua token telah dihapus untuk userId: " + userID);
        } else {
            // Jika tidak ada token yang ditemukan
            System.out.println("Tidak ada token yang ditemukan untuk userId: " + userID);
        }

        // Return response as JSON
        JsonResponse response = new JsonResponse(username);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return buildSuccessResponse(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse("Failed to generate response");
        }
    }

    // Success Builder
    private String buildSuccessResponse(String data) {
        return "{ \"status\": \"success\", \"data\": " + data + " }";
    }

    // Error Builder
    private String buildErrorResponse(String message) {
        return "{ \"status\": \"error\", \"message\": \"" + message + "\" }";
    }

    // JsonResponse class to return formatted response
    public static class JsonResponse {
        private String username;

        public JsonResponse(String username) {
            this.username = username;
        }

        // Getter and setter methods
        public String getUsername() {
            return username;
        }
    }
}
