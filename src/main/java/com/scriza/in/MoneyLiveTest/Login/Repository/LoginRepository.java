package com.scriza.in.MoneyLiveTest.Login.Repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.scriza.in.MoneyLiveTest.Login.Entity.UserData;

import java.util.List;

public interface LoginRepository extends JpaRepository<UserData, Long> {
    UserData findByEmailAndPassword(String email, String password);
    UserData findByEmail(String email);
    UserData findByPhoneNumber(String phoneNumber);
    List<UserData> findByCreatorEmail(String creatorEmail);
    List<UserData> findByRole(String Role);

//    User findByName(String name);
}
