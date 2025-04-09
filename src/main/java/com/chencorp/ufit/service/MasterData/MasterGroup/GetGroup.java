package com.chencorp.ufit.service.MasterData.MasterGroup;

import com.chencorp.ufit.model.MasterGroup;
import com.chencorp.ufit.repository.MasterGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetGroup {

    @Autowired
    private MasterGroupRepository masterGroupRepository;

    // Method to get all Group
    public ResponseEntity<Object> getAll() {
        List<MasterGroup> masterGroup = masterGroupRepository.findAll();

        if (masterGroup.isEmpty()) {
            return buildErrorResponse("No accounts found");
        }

        // Return success response with the list of accounts
        return buildSuccessResponse(masterGroup);
    }

    // Method to get a single Group by ID
    public ResponseEntity<Object> getById(Integer id) {
        Optional<MasterGroup> masterGroup = masterGroupRepository.findById(id);

        if (masterGroup.isPresent()) {
            return buildSuccessResponse(masterGroup.get());
        } else {
            return buildErrorResponse("Group not found");
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
