package com.chencorp.ufit.service.Account;

import com.chencorp.ufit.model.Account;
import com.chencorp.ufit.model.User;
import com.chencorp.ufit.repository.AccountRepository;
import com.chencorp.ufit.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UpdateAccount {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    public String updateAccount(String username,String nama_depan, String nama_belakang, String gender,
                           LocalDate birthdate, String birthplace, String phone, String email) {
        // Cek apakah pengguna ada di database
        Optional<User> optionalUser = userRepository.findByUsername(username);

        if (optionalUser.isEmpty()) {
            return buildErrorResponse("User not found");
        }
        
        User user = optionalUser.get();
        Integer UserId = user.getId();
        Account account = accountRepository.findByUserId(UserId)  // Mengambil Account berdasarkan User
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.setNamaDepan(nama_depan);
        account.setNamaBelakang(nama_belakang);
        account.setGender(gender);
        account.setBirthdate(birthdate);
        account.setBirthplace(birthplace);
        account.setPhone(phone);
        account.setEmail(email);        
        accountRepository.save(account);

      // Return response as JSON
        JsonResponse response = new JsonResponse(username, nama_depan, nama_belakang, gender, birthdate, birthplace, phone, email);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return buildSuccessResponse(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse("Failed to generate response");
        }
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
      

        public JsonResponse(String username, String namaDepan, String namaBelakang,
                            String gender, LocalDate birthdate, String birthplace,
                            String phone, String email) {
            this.username = username;
            this.namaDepan = namaDepan;
            this.namaBelakang = namaBelakang;
            this.gender = gender;
            // Format birthdate to ISO_LOCAL_DATE (yyyy-MM-dd)
            this.birthdate = birthdate.format(DateTimeFormatter.ISO_LOCAL_DATE);
            this.birthplace = birthplace;
            this.phone = phone;
            this.email = email;
          
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
        
    }
}
