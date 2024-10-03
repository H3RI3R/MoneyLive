package com.Scriza.MoneyLive.User.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Scriza.MoneyLive.User.DTO.UserDTO;
import com.Scriza.MoneyLive.User.Entity.User;
import com.Scriza.MoneyLive.User.Repository.UserRepository;

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

    public User registerUser(UserDTO userDTO) {
        User user = new User();
        user.setUserName(userDTO.getUserName());
        user.setMobileNumber(userDTO.getMobileNumber());
        user.setEmailId(userDTO.getEmailId());
        user.setAge(userDTO.getAge());
        user.setGender(userDTO.getGender());
        user.setPassword(userDTO.getPassword()); // Ideally, hash this password
        user.generateFields(); // Generate referralCode, walletAddress, and userId
        return userRepository.save(user); // Save user to the database
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
    
}
