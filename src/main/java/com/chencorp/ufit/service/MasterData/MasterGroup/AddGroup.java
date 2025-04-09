package com.chencorp.ufit.service.MasterData.MasterGroup;

import com.chencorp.ufit.model.Account;
import com.chencorp.ufit.model.MasterGroup;
import com.chencorp.ufit.model.User;
import com.chencorp.ufit.repository.MasterGroupRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AddGroup {

    @Autowired
    private MasterGroupRepository masterGroupRepository; // Injek Repo User

    public String register(String name) {

        MasterGroup masterGroup = new MasterGroup();
        masterGroup.setName(name);
        masterGroup.setActive(1);
        masterGroupRepository.save(masterGroup);

        Optional<MasterGroup> optionalUser = masterGroupRepository.findByName(name);
        // Validasi Jika group  Berhasil Di Simpan
        if (!masterGroupRepository.findByName(name).isPresent()) {
            return buildErrorResponse("User Gagal Di Daftarkan");
        }
        
        Optional<MasterGroup> optionalMaster = masterGroupRepository.findByName(name);
        MasterGroup masterG = optionalMaster.get();
        Integer id = masterG.getId();
        Integer active = 1;
        // Return response as JSON
        JsonResponse response = new JsonResponse(id,name,active);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return buildSuccessResponse(objectMapper.writeValueAsString(response));
        } catch (Exception e) {
            e.printStackTrace();
            return buildErrorResponse("Failed to generate response");
        }
    }

    public String update(Integer id, String name, Integer active) {   

        MasterGroup masterGroup = masterGroupRepository.findById(id).orElseThrow(() -> new RuntimeException("Account not found"));
        masterGroup.setName(name);
        masterGroup.setActive(active);
        masterGroupRepository.save(masterGroup);

        // Return response as JSON
        JsonResponse response = new JsonResponse(id,name,active);

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
        private String name;
        private Integer id;
        private Integer active;
        

        public JsonResponse(Integer id, String name, Integer active) {
            this.name = name;
            this.id = id;
            this.active = active;
        }

        // Getter and setter methods
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
            
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getActive() {
            return active;
        }

        public void setActive(Integer active) {
            this.active = active;
        }
    }
}
