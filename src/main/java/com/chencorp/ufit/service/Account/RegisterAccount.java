package com.chencorp.ufit.service.Account;

import com.chencorp.ufit.model.Account;
import com.chencorp.ufit.model.Token;
import com.chencorp.ufit.model.User;
import com.chencorp.ufit.repository.TokenRepository;
import com.chencorp.ufit.repository.UserRepository;
import com.chencorp.ufit.service.Auth.HashedPassword;
import com.chencorp.ufit.repository.AccountRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegisterAccount {

    @Autowired
    private UserRepository userRepository; // Injek Repo User

    @Autowired
    private TokenRepository tokenRepository; // Injek Repo Token

    @Autowired
    private AccountRepository accountRepository; // Injek Repo Account

    @Autowired
    private HashedPassword hashedPassword; // Injek Service Hash Password

    public String register(String username, String password, String nama_depan, String nama_belakang, String gender,
                           LocalDate birthdate, String birthplace, String phone, String email) {

        // Pengecekan Username Dan Password
        Optional<User> optionalUser = userRepository.findByUsername(username);
        Optional<Account> optionalEmail = accountRepository.findByPhone(phone);

        if (optionalUser.isPresent()) {
            return buildErrorResponse("User Sudah Terdaftar");
        }
        if (optionalEmail.isPresent()) {
            return buildErrorResponse("No Telp Sudah Terdaftar");
        }

        // Hash SHA 256 Password
        String hashedPasswordValue = hashedPassword.hashPassword(password);

        // Save User Baru
        User user = new User();
        user.setUsername(username);
        user.setPassword(hashedPasswordValue);
        user.setLevel(1); // Default level
        user.setActive(1); // Default active status
        user.setLogin(1);
        userRepository.save(user);

        // Validasi Jika User Berhasil Di Simpan
        if (!userRepository.findByUsername(username).isPresent()) {
            return buildErrorResponse("User Gagal Di Daftarkan");
        }

        // Kalo OK Save Akun
        Account account = new Account();
        account.setNamaDepan(nama_depan);
        account.setNamaBelakang(nama_belakang);
        account.setGender(gender);
        account.setBirthdate(birthdate);
        account.setBirthplace(birthplace);
        account.setPhone(phone);
        account.setEmail(email);
        account.setUser(user);
        accountRepository.save(account);

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

        // Return response as JSON
        JsonResponse response = new JsonResponse(username, nama_depan, nama_belakang, gender, birthdate, birthplace, phone, email, tokenStr);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return buildSuccessResponse(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse("Failed to generate response");
        }
    }

    public boolean isTokenValid(String tokenStr) {
        return tokenRepository.findByTokenAndInactiveIsNull(tokenStr).isPresent();
    }

    // Susscess Builder
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
        private String namaDepan;
        private String namaBelakang;
        private String gender;
        private String birthdate;
        private String birthplace;
        private String phone;
        private String email;
        private String token;

        public JsonResponse(String username, String namaDepan, String namaBelakang,
                            String gender, LocalDate birthdate, String birthplace,
                            String phone, String email, String tokenStr) {
            this.username = username;
            this.namaDepan = namaDepan;
            this.namaBelakang = namaBelakang;
            this.gender = gender;
            // Format birthdate to ISO_LOCAL_DATE (yyyy-MM-dd)
            this.birthdate = birthdate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            this.birthplace = birthplace;
            this.phone = phone;
            this.email = email;
            this.token = tokenStr;
        }

        // Getter and setter methods
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getNamaDepan() {
            return namaDepan;
        }

        public void setNamaDepan(String namaDepan) {
            this.namaDepan = namaDepan;
        }

        public String getNamaBelakang() {
            return namaBelakang;
        }

        public void setNamaBelakang(String namaBelakang) {
            this.namaBelakang = namaBelakang;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getBirthdate() {
            return birthdate;
        }

        public void setBirthdate(String birthdate) {
            this.birthdate = birthdate;
        }

        public String getBirthplace() {
            return birthplace;
        }

        public void setBirthplace(String birthplace) {
            this.birthplace = birthplace;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String tokenStr) {
            this.token = tokenStr;
        }
        
    }
}
