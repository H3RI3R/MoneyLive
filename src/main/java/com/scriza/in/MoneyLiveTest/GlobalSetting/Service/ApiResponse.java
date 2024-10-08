package com.scriza.in.MoneyLiveTest.GlobalSetting.Service;

import java.util.List;

import com.scriza.in.MoneyLiveTest.Assistance.Entity.Message;

public class ApiResponse {
    private String status;
    private String message;
    private List<Message> messages; // This can be null if not used

    // Constructor for message responses
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.messages = null; // Initialize to null if not used
    }

    // Constructor for message list responses
    public ApiResponse(String status, List<Message> messages) {
        this.status = status;
        this.messages = messages; // Assign the list of messages
        this.message = null; // Initialize to null if not used
    }

    // Constructor for both message and messages
    public ApiResponse(String status, String message, List<Message> messages) {
        this.status = status;
        this.message = message;
        this.messages = messages;
    }

    // Getters and setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}