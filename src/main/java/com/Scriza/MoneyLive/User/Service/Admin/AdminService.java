package com.Scriza.MoneyLive.User.Service.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Scriza.MoneyLive.User.Entity.Admin.CoinRate;
import com.Scriza.MoneyLive.User.Repository.Admin.CoinRateRepository;



@Service
public class AdminService {

    @Autowired
    private CoinRateRepository coinRateRepository;

    public ResponseEntity<?> setCoinRate(double coinRate) {
        CoinRate rate = new CoinRate();
        rate.setRate(coinRate);
        coinRateRepository.save(rate);
        return ResponseEntity.ok().body("Coin rate set successfully to: " + coinRate);
    }

    public double getCoinRate() {
        // Get the latest coin rate
        return coinRateRepository.findAll().stream().findFirst().map(CoinRate::getRate).orElse(0.0);
    }
}