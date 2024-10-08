package com.scriza.in.MoneyLiveTest.Assistance.Repository;

import com.scriza.in.MoneyLiveTest.Assistance.Entity.AssistanceQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssistanceQueryRepository extends JpaRepository<AssistanceQuery, Long> {
    Optional<AssistanceQuery> findByQueryNo(String queryNo); // Find by QueryNo
}