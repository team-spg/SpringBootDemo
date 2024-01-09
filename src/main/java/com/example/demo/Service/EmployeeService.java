package com.example.demo.Service;

import com.example.demo.Entity.Employee;
import com.example.demo.Repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee save(Employee employee){
        return employeeRepository.save(employee);
    }

    public void delete(Long id){
        Employee employee = employeeRepository.getOne(id);
        employeeRepository.delete(employee);
    }

    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }

    public List<Employee> findAllByName(String name){
        return employeeRepository.findByNameQuery(name);
    }

    @Scheduled(cron = "0 31 14 * * *")
    public Employee saveEmployee(){
        Employee employee = new Employee();
        employee.setName("abc");
        employee.setLastName("abc");
        return employeeRepository.save(employee);
    }
}
