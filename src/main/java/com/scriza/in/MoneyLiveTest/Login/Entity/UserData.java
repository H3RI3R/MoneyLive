package com.scriza.in.MoneyLiveTest.Login.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "userData")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name ;
    private String email;
    private String password;
    private String role;
    private String phoneNumber;
    private String designation;
    private String company;
//    private String companyddress ;
    private String address;
    private String companyAddress;

    @Column(name = "creator_email")
    private String creatorEmail;
}
