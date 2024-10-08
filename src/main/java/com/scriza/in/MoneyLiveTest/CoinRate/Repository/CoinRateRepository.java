package com.scriza.in.MoneyLiveTest.CoinRate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.MoneyLiveTest.CoinRate.Entity.CoinRate;



public interface CoinRateRepository extends JpaRepository<CoinRate, Long> {
}