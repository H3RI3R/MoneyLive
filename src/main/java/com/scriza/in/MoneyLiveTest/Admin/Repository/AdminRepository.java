package com.scriza.in.MoneyLiveTest.Admin.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.MoneyLiveTest.Admin.Entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findByEmail(String email);
    Optional<Admin> findByAdminName(String adminName);
}