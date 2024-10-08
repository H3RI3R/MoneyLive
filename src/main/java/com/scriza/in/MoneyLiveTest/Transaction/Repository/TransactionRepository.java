package com.scriza.in.MoneyLiveTest.Transaction.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.MoneyLiveTest.Transaction.Entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserId(String userId);
    Optional<Transaction> findByTransactionNo(String transactionNo);
}