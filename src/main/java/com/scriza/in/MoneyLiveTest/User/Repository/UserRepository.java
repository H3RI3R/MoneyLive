package com.scriza.in.MoneyLiveTest.User.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.MoneyLiveTest.User.Entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Automatically implemented by Spring Data JPA
    Optional<User> findByUserId(String userId);

    Optional<User> findByMobileNumber(String mobileNumber);

    Optional<User> findByEmailId(String emailId);

    Optional<User> findByWalletAddress(String walletAddress);
    Optional<User> findByReferralCode(String referralCode);
}