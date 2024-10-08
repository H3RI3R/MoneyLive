package com.scriza.in.MoneyLiveTest.Assistance.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

//9355 8260 3349
@Getter
@Setter
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String content;
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "assistance_query_id")
    @JsonBackReference  // Child side
    private AssistanceQuery assistanceQuery;

    // Getters and setters
}