package com.scriza.in.MoneyLiveTest.PaymentModule.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.MoneyLiveTest.PaymentModule.Entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(String userId);
}