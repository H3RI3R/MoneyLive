package com.scriza.in.MoneyLiveTest.Login.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scriza.in.MoneyLiveTest.Login.Entity.UserData;
import com.scriza.in.MoneyLiveTest.Login.Repository.LoginRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class loginService {

    private static final Logger logger = LoggerFactory.getLogger(loginService.class);
    private final Map<String, String> otpStore = new HashMap<>();
    @Autowired
    private LoginRepository userRepository;
    @Autowired
    private EmailService emailService;

    public UserData login(String email, String password) {
        logger.info("Login attempt with email: {}", email);

        UserData user = userRepository.findByEmailAndPassword(email, password);
        if (user != null) {
            logger.info("Successful login for email: {}", email);
            user.setPassword(UUID.randomUUID().toString().substring(0, 8)); // Generate a token and set it as password
            return user;
        }

        logger.error("Invalid email or password for email: {}", email);
        throw new RuntimeException("Invalid email or password");
    }
    public String sendOtp(String email) {
        UserData user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        String otp = generateOtp();
        otpStore.put(email, otp); // Store OTP for verification
        String subject = "Your OTP Code";
        String body = "Your OTP code is: " + otp;

        emailService.sendEmail(email, subject, body); // Send OTP email
        return otp; // Optionally return OTP for testing purposes
    }

    public boolean verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);
        return storedOtp != null && storedOtp.equals(otp);
    }

    private String generateOtp() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(1000000)); // Generate a 6-digit OTP
    }
    public void resetPassword(String email, String newPassword) {
        UserData user = userRepository.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        user.setPassword(newPassword); // Make sure to hash the password before saving
        userRepository.save(user);
    }
}
