package com.Scriza.MoneyLive.User.Repository.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Scriza.MoneyLive.User.Entity.Admin.CoinRate;

public interface CoinRateRepository extends JpaRepository<CoinRate, Long> {
}