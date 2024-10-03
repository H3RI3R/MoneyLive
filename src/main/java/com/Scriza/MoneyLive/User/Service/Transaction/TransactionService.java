package com.Scriza.MoneyLive.User.Service.Transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Scriza.MoneyLive.User.Entity.Transaction.Transaction;
import com.Scriza.MoneyLive.User.Repository.Transaction.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Transaction createTransaction(Transaction transaction) {
        // For "Pay In" or "Pay Out", calculate coins based on the transaction amount
        if (transaction.getTransactionType().equals("Pay In")) {
            transaction.setCoins(calculateCoins(transaction.getAmount()));
        } else if (transaction.getTransactionType().equals("Pay Out")) {
            transaction.setCoins(-calculateCoins(transaction.getAmount())); // Subtract coins
        }

        // Calculate closing balance after the transaction
        transaction.setClosingBalance(transaction.getOpeningBalance() + transaction.getAmount());
        
        // Save the transaction
        return transactionRepository.save(transaction);
    }
    public List<Transaction> getTransactionsByUserId(String userId) {
        return transactionRepository.findByUserId(userId);
    }
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    private int calculateCoins(double amount) {
        // Example logic to convert amount to coins, could be based on a rate
        int coinRate = 10; // Assuming 1 coin equals $10
        return (int) (amount / coinRate);
    }
}