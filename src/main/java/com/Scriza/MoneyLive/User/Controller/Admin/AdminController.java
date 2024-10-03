package com.Scriza.MoneyLive.User.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Scriza.MoneyLive.User.Service.Admin.AdminService;



@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/setCoinRate")
    public ResponseEntity<?> setCoinRate(@RequestParam double coinRate) {
        return adminService.setCoinRate(coinRate);
    }

    @GetMapping("/getCoinRate")
    public ResponseEntity<?> getCoinRate() {
        return ResponseEntity.ok().body(adminService.getCoinRate());
    }
}