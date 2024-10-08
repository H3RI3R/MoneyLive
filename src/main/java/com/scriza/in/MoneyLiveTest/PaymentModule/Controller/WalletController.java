package com.scriza.in.MoneyLiveTest.PaymentModule.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.scriza.in.MoneyLiveTest.PaymentModule.Service.WalletService;




@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @PostMapping("/recharge")
    public ResponseEntity<?> rechargeWallet(@RequestParam double amount, @RequestParam String userId) {
        return walletService.rechargeWallet(amount, userId);
    }

    @GetMapping("/details")
    public ResponseEntity<?> getWalletDetails(@RequestParam String userId) {
        return walletService.getWalletDetails(userId);
    }

    @PostMapping("/requestWithdrawal")
    public ResponseEntity<?> requestWithdrawal(
            @RequestParam String userId,
            @RequestParam double amount) {
        return walletService.requestWithdrawal(userId, amount);
    }
    @PostMapping("/acceptWithdrawal")
    public ResponseEntity<?> acceptWithdrawal(
            @RequestParam String transactionNo,
            @RequestParam boolean accept) {
        return walletService.acceptWithdrawal(transactionNo, accept);
    }
    @GetMapping("/getWalletDetails")
    public ResponseEntity<?> getDetails(@RequestParam String userIdOrEmail) {
        return walletService.getDetails(userIdOrEmail);
    }
}