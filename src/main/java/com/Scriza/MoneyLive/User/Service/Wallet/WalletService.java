package com.Scriza.MoneyLive.User.Service.Wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Scriza.MoneyLive.User.Entity.User;
import com.Scriza.MoneyLive.User.Entity.Transaction.Transaction;
import com.Scriza.MoneyLive.User.Entity.Wallet.Wallet;
import com.Scriza.MoneyLive.User.Repository.UserRepository;
import com.Scriza.MoneyLive.User.Repository.Transaction.TransactionRepository;
import com.Scriza.MoneyLive.User.Repository.Wallet.WalletRepository;
import com.Scriza.MoneyLive.User.Service.Admin.AdminService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> rechargeWallet(double amount, String userId) {
        // Get the current coin rate
        double coinRate = adminService.getCoinRate();
        if (coinRate <= 0.0) {
            return ResponseEntity.badRequest()
                    .body(Map.of("status", "error", "message", "Coin rate is not set. Please contact admin."));
        }

        // Calculate how many coins to credit
        int coins = (int) Math.round(amount / coinRate); // Explicitly cast to int

        // Find the wallet or create a new one
        Optional<Wallet> walletOpt = walletRepository.findByUserId(userId);
        Wallet wallet = walletOpt.orElse(new Wallet());
        wallet.setUserId(userId);
        wallet.setBalance(wallet.getBalance() + coins); // Update balance

        walletRepository.save(wallet);

        // Save transaction record
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        transaction.setUserName("User Name"); // Fetch user name from database or context
        transaction.setTransactionNo("SCZ" + System.currentTimeMillis()); // Generate a unique transaction number
        transaction.setTransactionType("Pay In");
        transaction.setOpeningBalance(wallet.getBalance() - coins); // Before adding
        transaction.setAmount(amount);
        transaction.setCoins(coins);
        transaction.setClosingBalance(wallet.getBalance()); // After adding
        transaction.setStatus("Credit"); // Credit transaction status
        transaction.setTransactionDate(LocalDateTime.now());

        transactionRepository.save(transaction);

        return ResponseEntity.ok().body(Map.of("status", "success", "message", coins + " coins credited."));
    }

    public ResponseEntity<?> getWalletDetails(String userId) {
        Optional<Wallet> walletOpt = walletRepository.findByUserId(userId);
        if (walletOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("No wallet found for userId: " + userId);
        }

        Wallet wallet = walletOpt.get();
        List<Transaction> transactions = transactionRepository.findByUserId(userId);

        return ResponseEntity.ok().body(
                Map.of(
                        "balance", wallet.getBalance(),
                        "transactions", transactions));
    }

    public ResponseEntity<?> requestWithdrawal(String userId, double amount) {
        Optional<Wallet> walletOpt = walletRepository.findByUserId(userId);
        if (walletOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "unsuccess", "message", "No wallet found for the provided userId."));
        }

        Wallet wallet = walletOpt.get();
        if (wallet.getBalance() < amount) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "unsuccess", "message", "Insufficient balance for withdrawal."));
        }

        // Fetch user name from the database or context
        // String userName = getUserNameById(userId); // Implement this method as needed

        // Create a pending withdrawal request (transaction)
        Transaction transaction = new Transaction();
        transaction.setUserId(userId);
        // transaction.setUserName(userName);
        String transectionId = generateTransactionNo();
        transaction.setTransactionNo(transectionId); // Consider using UUID
        transaction.setTransactionType("Pay Out");
        transaction.setOpeningBalance(wallet.getBalance());
        transaction.setAmount(amount);
        transaction.setClosingBalance(wallet.getBalance() - amount);
        transaction.setStatus("Pending");
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setCoins(0); // Set coins based on your logic if needed

        try {
            transactionRepository.save(transaction);

            // Update wallet balance
            wallet.setBalance(wallet.getBalance() - amount);
            walletRepository.save(wallet);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("status", "unsuccess", "message", "Failed to process withdrawal request."));
        }

        return ResponseEntity.ok().body(
                Map.of("status", "success", "message", "Withdrawal request submitted successfully.",
                        "transaction", transaction) // Include transaction details in response
        );
    }
    private String generateTransactionNo() {
    String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    SecureRandom random = new SecureRandom();
    StringBuilder transactionNo = new StringBuilder(6);
    for (int i = 0; i < 6; i++) {
        transactionNo.append(characters.charAt(random.nextInt(characters.length())));
    }
    return transactionNo.toString();
}

    public String getUserNameById(String userId) {
        return userRepository.findByUserId(userId)
                .map(User::getUserName) // Retrieve the userName if the user exists
                .orElse("Unknown User"); // Default value if the user is not found
    }
    public ResponseEntity<?> acceptWithdrawal(String transactionNo, boolean accept) {
        // No need to parse transactionNo to Long, since it's alphanumeric
        Optional<Transaction> transactionOpt = transactionRepository.findByTransactionNo(transactionNo);
        
        if (transactionOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "unsuccess", "message", "Transaction not found."));
        }
        
        Transaction transaction = transactionOpt.get();
        
        if (!transaction.getStatus().equals("Pending")) {
            return ResponseEntity.badRequest().body(
                    Map.of("status", "unsuccess", "message", "Transaction is not pending."));
        }
    
        // Handle acceptance or rejection
        if (accept) {
            transaction.setStatus("Accepted");
            transactionRepository.save(transaction);
    
            // Update wallet balance
            Optional<Wallet> walletOpt = walletRepository.findByUserId(transaction.getUserId());
            if (walletOpt.isPresent()) {
                Wallet wallet = walletOpt.get();
                wallet.setBalance(wallet.getBalance() - transaction.getAmount());
                walletRepository.save(wallet);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                        Map.of("status", "unsuccess", "message", "Wallet not found."));
            }
    
            return ResponseEntity.ok().body(
                    Map.of("status", "success", "message", "Withdrawal request accepted successfully."));
        } else {
            transaction.setStatus("Rejected");
            transactionRepository.save(transaction);
    
            return ResponseEntity.ok().body(
                    Map.of("status", "success", "message", "Withdrawal request rejected successfully."));
        }
    }

    public ResponseEntity<?> getDetails(String userIdOrEmail) {
        // First, try to find the user by userId
        Optional<User> userOpt = userRepository.findByUserId(userIdOrEmail);

        // If not found, try to find the user by emailId
        if (userOpt.isEmpty()) {
            userOpt = userRepository.findByEmailId(userIdOrEmail);
        }

        // If still not found, return an error response
        if (userOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                Map.of("status", "unsuccess", "message", "User not found."));
        }

        User user = userOpt.get();

        // Fetch wallet details by userId
        Optional<Wallet> walletOpt = walletRepository.findByUserId(user.getUserId());
        if (walletOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(
                Map.of("status", "unsuccess", "message", "No wallet found for this user."));
        }

        Wallet wallet = walletOpt.get();

        // Return walletAddress and balance in response
        return ResponseEntity.ok().body(
            Map.of(
                "status", "success",
                "walletAddress", user.getWalletAddress(),
                "balance", wallet.getBalance()
            )
        );
    }
    
}