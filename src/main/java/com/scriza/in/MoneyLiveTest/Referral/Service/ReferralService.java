package com.scriza.in.MoneyLiveTest.Referral.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.scriza.in.MoneyLiveTest.GlobalSetting.Entity.GlobalSettings;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Repository.GlobalSettingsRepository;
import com.scriza.in.MoneyLiveTest.Referral.Entity.Referral;
import com.scriza.in.MoneyLiveTest.Referral.Repository.ReferralRepository;



@Service
public class ReferralService {
    
    @Autowired
    private ReferralRepository referralRepository;

    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    public void createReferral(String referrerId, String referredId) {
        Referral referral = new Referral();
        referral.setReferrerId(referrerId);
        referral.setReferredId(referredId);
        referral.setTotalEarnings(0);
        referralRepository.save(referral);
    }

    public void applyCommission(String referredId, double profitAmount) {
        Referral referral = referralRepository.findByReferredId(referredId);
        if (referral != null) {
            GlobalSettings settings = globalSettingsRepository.findFirstByOrderByIdDesc();
            double commission = profitAmount * (settings.getReferralBonusPercentage() / 100);
            referral.setTotalEarnings(referral.getTotalEarnings() + commission);
            referralRepository.save(referral);
        }
    }
}