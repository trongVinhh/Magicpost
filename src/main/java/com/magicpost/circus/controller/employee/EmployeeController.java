package com.magicpost.circus.controller.employee;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/role/{roleId}")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long roleId) {
        return new ResponseEntity<>(this.employeeService.createEmployee(employeeDto, roleId), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(this.employeeService.getEmployee(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployeeS() {
        return new ResponseEntity<>(this.employeeService.getEmployees(), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee id: " + id + " was deleted", HttpStatus.OK);
    }

    @PutMapping("/{id}/role/{roleId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long roleId, @PathVariable Long id , @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(this.employeeService.updateEmployee(roleId, id,employeeDto), HttpStatus.OK);
    }
}
