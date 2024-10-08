package com.scriza.in.MoneyLiveTest.GlobalSetting.Service;

import java.util.List;

import com.scriza.in.MoneyLiveTest.Assistance.Entity.Message;
// Import AssistanceQuery class

public class ApiResponse {
    private String status;
    private String message;
    private Object data; // Use Object for flexible data types (e.g., AssistanceQuery, Message)

    // Constructor for standard message responses
    public ApiResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.data = null; // No data for this response
    }

    // Constructor for message list responses
    public ApiResponse(String status, List<Message> messages) {
        this.status = status;
        this.data = messages; // Store list of messages in data
        this.message = null; // No specific message
    }

    // Constructor for assistance query responses (AssistanceQuery or other types can be stored as data)
    public ApiResponse(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data; // Can hold any data object, including AssistanceQuery
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}