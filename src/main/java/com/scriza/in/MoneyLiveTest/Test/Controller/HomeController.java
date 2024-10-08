package com.scriza.in.MoneyLiveTest.Test.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scriza.in.MoneyLiveTest.Test.Entity.Employee;
import com.scriza.in.MoneyLiveTest.Test.Service.EmployeeService;

import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/welcome")
@AllArgsConstructor
public class HomeController {
    private EmployeeService employeeService;
@GetMapping("/home")
public List<Employee> getHome() {
    return employeeService.getEmployeesList();
}

    
}