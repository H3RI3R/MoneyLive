package com.scriza.in.MoneyLiveTest.Assistance.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
@Setter
@Getter
@Entity
public class AssistanceQuery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String queryNo;
    private String issueType;
    private String reason;
    private String documentUrl;
    private LocalDateTime submittedAt;
    private String status;

    @OneToMany(mappedBy = "assistanceQuery", cascade = CascadeType.ALL)
    @JsonManagedReference  // Parent side
    private List<Message> messages = new ArrayList<>();

    // Getters and setters
}