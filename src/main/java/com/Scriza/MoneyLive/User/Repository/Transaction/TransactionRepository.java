package com.Scriza.MoneyLive.User.Repository.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Scriza.MoneyLive.User.Entity.Transaction.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(String userId);
    Optional<Transaction> findByTransactionNo(String transactionNo);
}