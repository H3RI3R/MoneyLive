package com.scriza.in.MoneyLiveTest.User.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.scriza.in.MoneyLiveTest.GlobalSetting.Service.ApiResponse;
import com.scriza.in.MoneyLiveTest.User.DTO.UserDTO;
import com.scriza.in.MoneyLiveTest.User.Entity.User;
import com.scriza.in.MoneyLiveTest.User.Repository.UserRepository;



@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> deleteUserByIdentifier(String userId, String mobileNumber, String emailId,
            String walletAddress) {
        Optional<User> user = Optional.empty();

        if (userId != null) {
            user = userRepository.findByUserId(userId);
        } else if (mobileNumber != null) {
            user = userRepository.findByMobileNumber(mobileNumber);
        } else if (emailId != null) {
            user = userRepository.findByEmailId(emailId);
        } else if (walletAddress != null) {
            user = userRepository.findByWalletAddress(walletAddress);
        }

        if (user.isPresent()) {
            userRepository.delete(user.get());
            return ResponseEntity.ok().body(
                    Map.of("status", "success", "message",
                            "The user with " + getIdentifierString(user.get()) + " has been deleted."));
        } else {
            return ResponseEntity.status(404).body(
                    Map.of("status", "Failed", "message", "There is no user with the provided identifier!"));
        }
    }

    private String getIdentifierString(User user) {
        if (user.getUserId() != null)
            return "userID " + user.getUserId();
        if (user.getMobileNumber() != null)
            return "mobileNumber " + user.getMobileNumber();
        if (user.getEmailId() != null)
            return "emailID " + user.getEmailId();
        if (user.getWalletAddress() != null)
            return "walletAddress " + user.getWalletAddress();
        return "the identifier";
    }

    
public ResponseEntity<ApiResponse> registerUser(UserDTO userDTO) {
    User user = new User();
    user.setUserName(userDTO.getUserName());
    user.setMobileNumber(userDTO.getMobileNumber());
    user.setEmailId(userDTO.getEmailId());
    user.setAge(userDTO.getAge());
    user.setGender(userDTO.getGender());
    user.setPassword(userDTO.getPassword());

    user.generateFields(); // Generate referralCode, walletAddress, and userId

    // Check for referredId and link the accounts if provided
    if (userDTO.getReferredId() != null) {
        Optional<User> referrerUser = userRepository.findByReferralCode(userDTO.getReferredId());

        // If the referral code is not found, return an error response
        if (referrerUser.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse("failure", "Invalid referral code: " + userDTO.getReferredId()));
        }

        // Set the referrer if the referral code is valid
        user.setReferredBy(referrerUser.get());
    }

    // Save the registered user to the database
    User registeredUser = userRepository.save(user);

    return ResponseEntity.ok(new ApiResponse("success", "User registered successfully."+ registeredUser.getUserId())); // Return success response
}

    public ResponseEntity<?> viewUserByIdentifier(String userId, String mobileNumber, String emailId, String walletAddress) {
        if (userId == null && mobileNumber == null && emailId == null && walletAddress == null) {
            // If no parameters are provided, return all users
            List<User> allUsers = userRepository.findAll(); // Assuming you have a findAll() method in UserRepository
            return ResponseEntity.ok().body(allUsers);
        }
    
        Optional<User> user = Optional.empty();
    
        if (userId != null) {
            user = userRepository.findByUserId(userId);
        } else if (mobileNumber != null) {
            user = userRepository.findByMobileNumber(mobileNumber);
        } else if (emailId != null) {
            user = userRepository.findByEmailId(emailId);
        } else if (walletAddress != null) {
            user = userRepository.findByWalletAddress(walletAddress);
        }
    
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.status(404).body(
                    Map.of("status", "Failed", "message", "No user found with the provided identifier!")
            );
        }
    }

    public ResponseEntity<?> modifyUserDetails(String userId, String mobileNumber, String emailId, String walletAddress,
            String newUserName, String newEmailId, String newMobileNumber,
            Integer newAge, String newGender, String newPassword) {
        Optional<User> user = Optional.empty();

        // Find user by any unique identifier
        if (userId != null) {
            user = userRepository.findByUserId(userId);
        } else if (mobileNumber != null) {
            user = userRepository.findByMobileNumber(mobileNumber);
        } else if (emailId != null) {
            user = userRepository.findByEmailId(emailId);
        } else if (walletAddress != null) {
            user = userRepository.findByWalletAddress(walletAddress);
        }

        // If no user is found, return a failure message
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(
                    Map.of("status", "Failed", "message", "The provided identifier doesn't belong to any account."));
        }

        User existingUser = user.get();

        // Update the provided fields (only the fields that are not null)
        if (newUserName != null) {
            existingUser.setUserName(newUserName);
        }
        if (newEmailId != null) {
            existingUser.setEmailId(newEmailId);
        }
        if (newMobileNumber != null) {
            existingUser.setMobileNumber(newMobileNumber);
        }
        if (newAge != null) {
            existingUser.setAge(newAge);
        }
        if (newGender != null) {
            existingUser.setGender(newGender);
        }
        if (newPassword != null) {
            existingUser.setPassword(newPassword); // Ensure to hash the password in real-world apps
        }

        // Save the updated user details
        userRepository.save(existingUser);

        // Return success response
        return ResponseEntity.ok().body(
                Map.of("status", "success", "message", "User details have been updated successfully."));
    }
    public ResponseEntity<ApiResponse> submitQuery(
            String issueType, String reason, MultipartFile document, String userEmail) {
        // Generate a unique QueryNo (e.g., #XYZ123)
        String queryNo = generateQueryNumber();

        // Set the initial status to "Pending"
        String status = "Pending";

        // Capture the current date and time for the submission
        LocalDateTime currentDate = LocalDateTime.now();

        // Here you can save the query to the database or perform any logic
        // e.g., saveQueryToDatabase(queryNo, issueType, reason, document, status, currentDate, userEmail);

        // Send the email to the admin (logic for sending email with attachments)
        sendEmailToAdmin(queryNo, issueType, reason, document, status, userEmail);

        // Return success response
        return ResponseEntity.ok(new ApiResponse("success", "Query submitted successfully with QueryNo: " + queryNo));
    }

    // Method to generate a random query number
    private String generateQueryNumber() {
        return "#XYZ" + (int)(Math.random() * 9000 + 1000); // Random QueryNo like #XYZ1234
    }

    // Dummy method to simulate sending an email to admin
    private void sendEmailToAdmin(String queryNo, String issueType, String reason, MultipartFile document, String status, String userEmail) {
        // Logic for sending email to admin with the query details and attached document
        // This could include JavaMailSender or any SMTP library integration
    }
    
}
