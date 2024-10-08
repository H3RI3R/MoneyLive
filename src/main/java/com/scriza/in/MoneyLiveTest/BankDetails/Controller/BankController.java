package com.scriza.in.MoneyLiveTest.BankDetails.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.scriza.in.MoneyLiveTest.BankDetails.Entity.Bank;
import com.scriza.in.MoneyLiveTest.BankDetails.Service.BankService;
import com.scriza.in.MoneyLiveTest.BankDetails.Service.Response;

import java.util.List;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    @Autowired
    private BankService bankService;


    @PostMapping("/save")
    public ResponseEntity<String> saveBank(
            @RequestParam String email,
            @RequestParam(required = false) String accountNumber,
            @RequestParam(required = false) String reEnterAccountNumber,
            @RequestParam(required = false) String accountOwnerFullName,
            @RequestParam(required = false) String fathersName,
            @RequestParam(required = false) String mothersName,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) String ifscCode,
            @RequestParam(required = false) String upiId,
            @RequestParam(required = false) String upiName,
            @RequestParam(required = false) String upiFathersName,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String upiProvider,
            @RequestParam(required = false) MultipartFile qrCodeFile) {
        try {
            // Validate Account Number
            if (accountNumber != null && !accountNumber.equals(reEnterAccountNumber)) {
                return ResponseEntity.badRequest().body("Account numbers do not match.");
            }

            // Save the QR Code as binary data
            byte[] qrCodeBytes = null;
            if (qrCodeFile != null && !qrCodeFile.isEmpty()) {
                qrCodeBytes = qrCodeFile.getBytes();
            }

            // Call service to save bank/UPI details
            bankService.saveBank(email, accountNumber, accountOwnerFullName, fathersName, mothersName, address, ifscCode, upiId, upiName, upiFathersName, phoneNumber, upiProvider, qrCodeBytes);


            return ResponseEntity.ok("Bank saved successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/modify")
    public ResponseEntity<?> modifyBank(
            @RequestParam String email,
            @RequestParam String identifier,
            @RequestParam(required = false) String changeIdentifier, // Optional parameter
            @RequestParam(required = false) String changeName) {      // Optional parameter
        try {
            // Call the service method with the provided parameters
            bankService.modifyBank(email, identifier, changeIdentifier, changeName);
            return Response.responseSuccess("Bank Modified Successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/updateStatus")
    public ResponseEntity<String> updateStatus(
            @RequestParam String email,
            @RequestParam String identifier,
            @RequestParam String status) {

        // Call service to update the status
        return bankService.updateStatus(email, identifier, status);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBank(
            @RequestParam String email,
            @RequestParam String identifier) {
        try {
            bankService.deleteBank(email, identifier);

            // Log the activity for deleting a bank


            return ResponseEntity.ok("Bank deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/view")
    public ResponseEntity<List<Bank>> viewBanks(@RequestParam String email) {
        try {
            List<Bank> banks = bankService.getBanksByEmail(email);
            return ResponseEntity.ok(banks);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

   
}