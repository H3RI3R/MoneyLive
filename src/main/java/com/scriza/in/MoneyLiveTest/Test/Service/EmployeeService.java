package com.scriza.in.MoneyLiveTest.Test.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.scriza.in.MoneyLiveTest.Test.Entity.Employee;

@Service
public class EmployeeService {
private List<Employee> empList = new ArrayList<>();
public EmployeeService(){
    empList.add(new Employee(UUID.randomUUID().toString(),"Riitk" , "Ritik101@gmail.com"));
    empList.add(new Employee(UUID.randomUUID().toString(),"harsh" , "Harsh101@gmail.com"));
    empList.add(new Employee(UUID.randomUUID().toString(),"Rani" , "Rani101@gmail.com"));
}
public List<Employee> getEmployeesList(){
    return empList;
}
}
