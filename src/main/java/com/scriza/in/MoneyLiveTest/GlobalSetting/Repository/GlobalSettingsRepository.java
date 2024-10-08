package com.scriza.in.MoneyLiveTest.GlobalSetting.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.MoneyLiveTest.GlobalSetting.Entity.GlobalSettings;



public interface GlobalSettingsRepository extends JpaRepository<GlobalSettings , Long>{

    GlobalSettings findFirstByOrderByIdDesc();
    
}
