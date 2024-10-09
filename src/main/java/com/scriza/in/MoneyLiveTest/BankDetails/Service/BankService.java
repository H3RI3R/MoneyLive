package com.scriza.in.MoneyLiveTest.BankDetails.Service;


import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scriza.in.MoneyLiveTest.BankDetails.Entity.Bank;
import com.scriza.in.MoneyLiveTest.BankDetails.Repository.BankRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private static final Logger log = LoggerFactory.getLogger(BankService.class);
    @Autowired
    private BankRepository bankRepository;
    @Transactional
    public void saveBank(String email,
                         String accountNumber,
                         String accountOwnerFullName,
                         String fathersName,
                         String mothersName,
                         String address,
                         String ifscCode,
                         String upiId,
                         String upiName,
                         String upiFathersName,
                         String phoneNumber,
                         String upiProvider,
                         byte[] qrCodeBytes) {

        Bank bank = new Bank();
        bank.setEmail(email);

        // Determine the type based on identifier
        String type = null;
        String identifier = null;
        if (accountNumber != null) {
            identifier = accountNumber;
            type = "Bank Account";
            bank.setIdentifier(identifier);
            bank.setName(accountOwnerFullName);
            bank.setFathersName(fathersName);
            bank.setMothersName(mothersName);
            bank.setAddress(address);
            bank.setIfscCode(ifscCode);
        } else if (upiId != null) {
            identifier = upiId;
            type = "UPI Account";
            bank.setIdentifier(identifier);
            bank.setName(upiName);
            bank.setFathersName(upiFathersName);
            bank.setPhoneNumber(phoneNumber);
            bank.setUpiProvider(upiProvider);
            bank.setQrCode(qrCodeBytes);
        }

        // Set the type and status
        if (type != null) {
            bank.setType(type);
            bank.setStatus("Deactive");  // Default status is "Deactive"
        }

        // Ensure only one active account of each type
        if ("ACTIVE".equalsIgnoreCase(bank.getStatus())) {
            deactivateOtherAccounts(email, type);
        }

        bankRepository.save(bank);
    }
    @Transactional
    public void deactivateOtherAccounts(String email, String type) {
        List<Bank> activeAccounts = bankRepository.findByEmailAndTypeAndStatus(email, type, "ACTIVE");
        if (!activeAccounts.isEmpty()) {
            for (Bank bank : activeAccounts) {
                bank.setStatus("Deactive");
                bankRepository.save(bank);
            }
        }
    }

    @Transactional
    public void modifyBank(String email, String identifier, String changeIdentifier, String changeName) {
        // Fetch the bank using both email and identifier
        Optional<Bank> optionalBank = bankRepository.findByEmailAndIdentifier(email, identifier);

        if (!optionalBank.isPresent()) {
            throw new RuntimeException("Bank not found for email: " + email + " and identifier: " + identifier);
        }

        Bank bank = optionalBank.get();

        // Initialize variables to track what was updated
        StringBuilder updateDetails = new StringBuilder();

        if (changeIdentifier != null && !changeIdentifier.isEmpty()) {
            if (changeIdentifier.contains("@")) {
                bank.setIdentifier(changeIdentifier);  // It's a UPI ID
                updateDetails.append("UPI ID");
            } else if (changeIdentifier.matches("\\d+")) {
                bank.setIdentifier(changeIdentifier);  // It's an Account Number
                updateDetails.append("Account Number");
            } else {
                throw new IllegalArgumentException("Invalid identifier format");
            }
        }

        if (changeName != null && !changeName.isEmpty()) {
            bank.setName(changeName);
            if (updateDetails.length() > 0) {
                updateDetails.append(" and ");
            }
            updateDetails.append("Name");
        }

        if (updateDetails.length() == 0) {
            throw new RuntimeException("No valid fields to update");
        }

        // Save the updated bank entity
        bankRepository.save(bank);
    }

    @Transactional
    public void deleteBank(String email, String identifier) {
        Optional<Bank> bank = bankRepository.findByEmailAndIdentifier(email, identifier);
        if (bank == null) {
            throw new RuntimeException("Bank record not found for email: " + email + " and identifier: " + identifier);
        }

        bankRepository.deleteByEmailAndIdentifier(email, identifier);
    }

    public List<Bank> getBanksByEmail(String email) {
        return bankRepository.findAllByEmail(email);
    }

    @Transactional
    public ResponseEntity<String> updateStatus(String email, String identifier, String status) {
        // Fetch the bank account by identifier and email
        Optional<Bank> bankOptional = bankRepository.findByEmailAndIdentifier(email, identifier);

        if (bankOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("Bank account not found.");
        }

        Bank bank = bankOptional.get();
        String type = bank.getType();

        // Check the status to set and ensure only one account of each type can be active
        if ("ACTIVE".equalsIgnoreCase(status)) {
            // Deactivate other accounts of the same type
            deactivateOtherAccounts(email, type);
            bank.setStatus("ACTIVE");
        } else if ("DEACTIVE".equalsIgnoreCase(status)) {
            bank.setStatus("DEACTIVE");
        } else {
            return ResponseEntity.badRequest().body("Invalid status provided.");
        }

        bankRepository.save(bank);

        // Log the activity

        return ResponseEntity.ok("Bank status updated successfully");
    }

}