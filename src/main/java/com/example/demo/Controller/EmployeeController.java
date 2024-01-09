package com.example.demo.Controller;

import com.example.demo.Entity.Employee;
import com.example.demo.Service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employee")
    public ResponseEntity<?> create(@RequestBody Employee employee){
        Employee emp = employeeService.save(employee);
        return ResponseEntity.ok(emp);
    }

    @PutMapping("/employee")
    public ResponseEntity<?> update(@RequestBody Employee employee){
        if(employee.getId() == null) return ResponseEntity.badRequest().build();
        Employee emp = employeeService.save(employee);
        return ResponseEntity.ok(emp);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        employeeService.delete(id);
        return ResponseEntity.ok("Eployee deleted");
    }

    @GetMapping("/employee")
    public ResponseEntity<?> getAll(){
        List<Employee> list = employeeService.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/employee/{name}")
    public ResponseEntity<?> getEmployee(@PathVariable String name){
        List<Employee> list = employeeService.findAllByName(name);
        return ResponseEntity.ok(list);
    }
}
