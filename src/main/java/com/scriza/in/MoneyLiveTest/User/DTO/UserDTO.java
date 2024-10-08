package com.scriza.in.MoneyLiveTest.User.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String userName;
    private String mobileNumber;
    private String emailId;
    private Integer age;
    private String gender;
    private String password;
    private String referredId;  // The referral code (if the user was referred)
}