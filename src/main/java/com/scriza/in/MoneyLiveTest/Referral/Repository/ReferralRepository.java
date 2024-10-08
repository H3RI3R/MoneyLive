package com.scriza.in.MoneyLiveTest.Referral.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.MoneyLiveTest.Referral.Entity.Referral;



public interface ReferralRepository extends JpaRepository<Referral , Long> {

    Referral findByReferredId(String referredId);

}