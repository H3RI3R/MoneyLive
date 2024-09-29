package com.Scriza.MoneyLive.User.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Scriza.MoneyLive.User.DTO.UserDTO;
import com.Scriza.MoneyLive.User.Entity.User;
import com.Scriza.MoneyLive.User.Service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(
            @RequestParam(value = "userId", required = false) String userId,
            @RequestParam(value = "mobileNumber", required = false) String mobileNumber,
            @RequestParam(value = "emailId", required = false) String emailId,
            @RequestParam(value = "walletAddress", required = false) String walletAddress
    ) {
        return userService.deleteUserByIdentifier(userId, mobileNumber, emailId, walletAddress);
    }
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        User registeredUser = userService.registerUser(userDTO);
        return ResponseEntity.ok(registeredUser);
    }
    @GetMapping("/view")
    public ResponseEntity<?> viewUser(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) String emailId,
            @RequestParam(required = false) String walletAddress) {
        return userService.viewUserByIdentifier(userId, mobileNumber, emailId, walletAddress);
    }
    @PutMapping("/modify")
    public ResponseEntity<?> modifyUserDetails(
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String mobileNumber,
            @RequestParam(required = false) String emailId,
            @RequestParam(required = false) String walletAddress,
            @RequestParam(required = false) String newUserName,
            @RequestParam(required = false) String newEmailId,
            @RequestParam(required = false) String newMobileNumber,
            @RequestParam(required = false) Integer newAge,
            @RequestParam(required = false) String newGender,
            @RequestParam(required = false) String newPassword) {
        return userService.modifyUserDetails(userId, mobileNumber, emailId, walletAddress, 
                                             newUserName, newEmailId, newMobileNumber, 
                                             newAge, newGender, newPassword);
    }
}