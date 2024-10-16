package com.scriza.in.MoneyLiveTest.Assistance.Service;

import com.scriza.in.MoneyLiveTest.Assistance.Entity.AssistanceQuery;
import com.scriza.in.MoneyLiveTest.Assistance.Entity.Message;
import com.scriza.in.MoneyLiveTest.Assistance.Repository.AssistanceQueryRepository;
import com.scriza.in.MoneyLiveTest.Assistance.Repository.MessageRepository;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AssistanceQueryService {

    @Autowired
    private AssistanceQueryRepository assistanceQueryRepository;

    @Autowired
    private MessageRepository messageRepository;

    // Generate a unique Query Number
    private String generateQueryNo() {
        StringBuilder queryNo = new StringBuilder();
        Random random = new Random();
        
        // Generate three random uppercase letters
        for (int i = 0; i < 3; i++) {
            char letter = (char) (random.nextInt(26) + 'A');
            queryNo.append(letter);
        }
        
        // Generate three random digits
        for (int i = 0; i < 3; i++) {
            int digit = random.nextInt(10);
            queryNo.append(digit);
        }
        
        return queryNo.toString(); // Return without the '#' symbol
    }
    // Submit a new assistance query
    public ResponseEntity<ApiResponse> submitQuery(AssistanceQuery query) {
        query.setQueryNo(generateQueryNo()); // Set the generated query number
        query.setSubmittedAt(LocalDateTime.now());
        query.setStatus("Pending");
        assistanceQueryRepository.save(query);
        return ResponseEntity.ok(new ApiResponse("success", "Query submitted successfully with ID: " + query.getQueryNo(), null));
    }
    
    public ResponseEntity<ApiResponse> addMessage(String queryNo, Message message) {
        queryNo = queryNo.replace("#", "");  // Clean the query number if needed
        
        Optional<AssistanceQuery> optionalQuery = assistanceQueryRepository.findByQueryNo(queryNo);
        if (optionalQuery.isPresent()) {
            AssistanceQuery assistanceQuery = optionalQuery.get();
            
            // Ensure the message object is set correctly
            message.setTimestamp(LocalDateTime.now());
            message.setAssistanceQuery(assistanceQuery);
            
            // Save the message
            messageRepository.save(message);
            
            // Add the message to the query
            assistanceQuery.getMessages().add(message);
            assistanceQueryRepository.save(assistanceQuery);
            
            return ResponseEntity.ok(new ApiResponse("success", "Message added successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Query not found with the provided Query No", null));
        }
    }
    
    public ResponseEntity<ApiResponse> getMessages(String queryNo) {
        Optional<AssistanceQuery> optionalQuery = assistanceQueryRepository.findByQueryNo(queryNo);
        if (optionalQuery.isPresent()) {
            AssistanceQuery assistanceQuery = optionalQuery.get();
            List<Message> messages = assistanceQuery.getMessages();
            return ResponseEntity.ok(new ApiResponse("success", "Query retrieved successfully with " + messages.size() + " messages.", messages));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Assistance query not found", null));
        }
    }
    
    public ResponseEntity<ApiResponse> closeQuery(String queryNo) {
        Optional<AssistanceQuery> optionalQuery = assistanceQueryRepository.findByQueryNo(queryNo);
        if (optionalQuery.isPresent()) {
            AssistanceQuery assistanceQuery = optionalQuery.get();
            assistanceQuery.setStatus("Closed");
            assistanceQueryRepository.save(assistanceQuery);
            return ResponseEntity.ok(new ApiResponse("success", "Query closed successfully", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Query not found with the provided Query No", null));
        }
    }
    public ResponseEntity<ApiResponse> getQueryByQueryNo(String queryNo) {
        Optional<AssistanceQuery> optionalQuery = assistanceQueryRepository.findByQueryNo(queryNo);
        if (optionalQuery.isPresent()) {
            AssistanceQuery assistanceQuery = optionalQuery.get();
            return ResponseEntity.ok(new ApiResponse("success", "Query retrieved successfully", assistanceQuery)); // Send AssistanceQuery as data
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("failure", "Query not found with the provided Query No", null));
        }
    }
    public ResponseEntity<ApiResponse> openQuery(String queryNo) {
        Optional<AssistanceQuery> optionalQuery = assistanceQueryRepository.findByQueryNo(queryNo);

        if (optionalQuery.isPresent()) {
            AssistanceQuery assistanceQuery = optionalQuery.get();
            if ("Pending".equalsIgnoreCase(assistanceQuery.getStatus())) {
                // Update the status to "Opened"
                assistanceQuery.setStatus("Opened");
                assistanceQueryRepository.save(assistanceQuery); // Save the updated query

                // Return success message
                return ResponseEntity.ok(
                    new ApiResponse("success", queryNo + " has been opened successfully")
                );
            } else {
                // If status is not "Pending", return a relevant message
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse("failure", "Query is not in 'Pending' status"));
            }
        } else {
            // If query not found, return a failure message
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse("failure", "Query not found with the provided Query No"));
        }
    }
}