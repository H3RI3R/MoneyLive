package com.Scriza.MoneyLive.User.Entity.Transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId; // User identifier
    private String userName; // User's name
    private String transactionNo; // Transaction number (could be generated or assigned)
    private String transactionType; // "Pay In" or "Pay Out"
    private double openingBalance; // Wallet balance before the transaction
    private double amount; // Amount of the transaction
    private double closingBalance; // Wallet balance after the transaction
    private String status; // Transaction status (e.g., "Pending", "Debit", "Credit")
    private LocalDateTime transactionDate; // Date and time of transaction
  @Column(nullable = false) // Ensure this is set correctly
    private Integer coins;
    // Optionally, you can add additional fields like:
    // private String walletAddress; // To associate the transaction with a wallet
}