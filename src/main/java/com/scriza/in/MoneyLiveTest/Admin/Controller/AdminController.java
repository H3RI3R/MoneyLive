package com.scriza.in.MoneyLiveTest.Admin.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scriza.in.MoneyLiveTest.Admin.Entity.Admin;
import com.scriza.in.MoneyLiveTest.Admin.Service.AdminService;
import com.scriza.in.MoneyLiveTest.CoinRate.Service.CoinRateService;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Entity.GlobalSettings;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Repository.GlobalSettingsRepository;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Service.ApiResponse;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService1;
    @Autowired
    private GlobalSettingsRepository globalSettingsRepository;

    @PostMapping("/setReferralBonus")
    public ResponseEntity<String> setReferralBonus(@RequestBody Map<String, Double> bonusPercentage) {
        GlobalSettings settings = new GlobalSettings();
        settings.setReferralBonusPercentage(bonusPercentage.get("referralBonusPercentage"));
        globalSettingsRepository.save(settings);
        return ResponseEntity.ok("Referral Bonus set successfully");
    }
    @GetMapping("/getReferralBonus")
    public ResponseEntity<?> getReferralBonus() {
        GlobalSettings settings = globalSettingsRepository.findFirstByOrderByIdDesc();
        if (settings != null) {
            return ResponseEntity.ok(Map.of("referralBonusPercentage", settings.getReferralBonusPercentage()));
        } else {
            return ResponseEntity.status(404).body(Map.of("message", "Referral bonus not set"));
        }
    }

    @Autowired
    private CoinRateService adminService;

    @PostMapping("/setCoinRate")
    public ResponseEntity<?> setCoinRate(@RequestParam double coinRate) {
        return adminService.setCoinRate(coinRate);
    }

    @GetMapping("/getCoinRate")
    public ResponseEntity<?> getCoinRate() {
        return ResponseEntity.ok().body(adminService.getCoinRate());
    }
    @GetMapping("/findAdminId")
    public ResponseEntity<ApiResponse> findAdminId(
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String adminName) {
        return adminService1.findAdminIdByEmailOrName(email, adminName);
    }
     @PostMapping("/create")
    public ResponseEntity<ApiResponse> createAdmin(@RequestBody Admin admin) {
        return adminService1.createAdmin(admin);
    }

    // View Admin
    @GetMapping("/view")
    public ResponseEntity<ApiResponse> viewAdmin(@RequestParam Long id) {
        return adminService1.viewAdmin(id);
    }

    // Modify Admin
    @PutMapping("/modify")
    public ResponseEntity<ApiResponse> modifyAdmin(
            @RequestParam Long id, @RequestBody Admin updatedAdmin) {
        return adminService1.modifyAdmin(id, updatedAdmin);
    }

    // Delete Admin
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponse> deleteAdmin(@RequestParam Long id) {
        return adminService1.deleteAdmin(id);
    }
}