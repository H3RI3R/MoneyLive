package com.scriza.in.MoneyLiveTest.Assistance.Controller;

import com.scriza.in.MoneyLiveTest.Assistance.Entity.AssistanceQuery;
import com.scriza.in.MoneyLiveTest.Assistance.Entity.Message;
import com.scriza.in.MoneyLiveTest.Assistance.Service.AssistanceQueryService;
import com.scriza.in.MoneyLiveTest.GlobalSetting.Service.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/assistance")
public class AssistanceQueryController {

    @Autowired
    private AssistanceQueryService assistanceQueryService;

    // Submit a new assistance query
    @PostMapping("/submit")
    public ResponseEntity<ApiResponse> submitQuery(@RequestBody AssistanceQuery query) {
        return assistanceQueryService.submitQuery(query);
    }

    // Add a message to an existing query using QueryNo
    @PostMapping("/query/no/{queryNo}/message")
    public ResponseEntity<ApiResponse> addMessage(@PathVariable String queryNo, @RequestBody Message message) {
        return assistanceQueryService.addMessage(queryNo, message);
    }

    // Get all messages for a query using QueryNo
    @GetMapping("/query/no/{queryNo}/messages")
    public ResponseEntity<ApiResponse> getMessages(@PathVariable String queryNo) {
        return assistanceQueryService.getMessages(queryNo);
    }

    // Close a query using QueryNo
    @PutMapping("/query/no/{queryNo}/close")
    public ResponseEntity<ApiResponse> closeQuery(@PathVariable String queryNo) {
        return assistanceQueryService.closeQuery(queryNo);
    }

    // Get a query by its Query Number
    @GetMapping("/query/no/{queryNo}")
    public ResponseEntity<ApiResponse> getQueryByQueryNo(@PathVariable String queryNo) {
        return assistanceQueryService.getQueryByQueryNo(queryNo);
    }
    @PutMapping("/query/open/{queryNo}")
    public ResponseEntity<ApiResponse> openQuery(@PathVariable String queryNo) {
        return assistanceQueryService.openQuery(queryNo);
    }
}