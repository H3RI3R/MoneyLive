package com.scriza.in.MoneyLiveTest.Admin.Service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scriza.in.MoneyLiveTest.Admin.Entity.Admin;
import com.scriza.in.MoneyLiveTest.Admin.Repository.AdminRepository;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Service.ApiResponse;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    // Create Admin
    public ResponseEntity<ApiResponse> createAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("failure", "Admin with this email already exists"));
        }
        adminRepository.save(admin);
        return ResponseEntity.ok(new ApiResponse("success", "Admin created successfully"));
    }

    // View Admin
    public ResponseEntity<ApiResponse> viewAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            Admin adminDetails = admin.get();
        // Format the admin details in a user-friendly way
        String message = "Admin ID: " + adminDetails.getId() +
                         ", Name: " + adminDetails.getAdminName() +
                         ", Email: " + adminDetails.getEmail();
        return ResponseEntity.ok(new ApiResponse("success", message));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(new ApiResponse("failure", "Can't find the admin account. Please enter the correct ID again."));
}
    }

    // Modify Admin
    public ResponseEntity<ApiResponse> modifyAdmin(Long id, Admin updatedAdmin) {
        Optional<Admin> existingAdmin = adminRepository.findById(id);
        if (existingAdmin.isPresent()) {
            Admin admin = existingAdmin.get();
            admin.setAdminName(updatedAdmin.getAdminName());
            admin.setEmail(updatedAdmin.getEmail());
            admin.setPassword(updatedAdmin.getPassword()); // Password should be encrypted
            adminRepository.save(admin);
            return ResponseEntity.ok(new ApiResponse("success", "Admin updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Admin not found"));
        }
    }

    // Delete Admin
    public ResponseEntity<ApiResponse> deleteAdmin(Long id) {
        Optional<Admin> admin = adminRepository.findById(id);
        if (admin.isPresent()) {
            adminRepository.delete(admin.get());
            return ResponseEntity.ok(new ApiResponse("success", "Admin deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Admin not found"));
        }
    }
    public ResponseEntity<ApiResponse> findAdminIdByEmailOrName(String email, String adminName) {
        Optional<Admin> admin = Optional.empty();

        // Find by email if provided
        if (email != null) {
            admin = adminRepository.findByEmail(email);
        }

        // If admin not found by email, try to find by admin name
        if (admin.isEmpty() && adminName != null) {
            admin = adminRepository.findByAdminName(adminName);
        }

        // Return the admin ID if found
        if (admin.isPresent()) {
            String message = "adminId"+admin.get().getId();
            return ResponseEntity.ok(new ApiResponse("success", message));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Admin not found with the provided email or name"));
        }
    }
}