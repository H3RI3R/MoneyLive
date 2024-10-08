package com.scriza.in.MoneyLiveTest.CoinRate.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.scriza.in.MoneyLiveTest.CoinRate.Entity.CoinRate;
import com.scriza.in.MoneyLiveTest.CoinRate.Repository.CoinRateRepository;




@Service
public class CoinRateService {

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