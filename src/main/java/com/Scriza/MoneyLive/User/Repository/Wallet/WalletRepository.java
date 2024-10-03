package com.Scriza.MoneyLive.User.Repository.Wallet;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Scriza.MoneyLive.User.Entity.Wallet.Wallet;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(String userId);
}