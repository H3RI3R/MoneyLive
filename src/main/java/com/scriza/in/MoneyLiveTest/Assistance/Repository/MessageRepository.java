package com.scriza.in.MoneyLiveTest.Assistance.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scriza.in.MoneyLiveTest.Assistance.Entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}