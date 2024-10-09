package com.scriza.in.MoneyLiveTest.BankDetails.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.MoneyLiveTest.BankDetails.Entity.TransactionRequest;

import java.util.List;


@Repository
public interface TransactionRequestRepository extends JpaRepository<TransactionRequest, Long> {
    List<TransactionRequest> findByCreatorEmailOrderByTimestampDesc(String creatorEmail);
    List<TransactionRequest> findByUserEmailOrderByTimestampDesc(String userEmail);
//    Optional<TransactionRequest> findByTransactionId(String transactionId);
TransactionRequest findByTransactionId(String transactionId);
}