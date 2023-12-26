package com.magicpost.circus.controller.employee;

import com.magicpost.circus.entity.info.PackageTransfer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.service.EmployeeService;
import com.magicpost.circus.service.StorageEmployeeService;
import com.magicpost.circus.service.TransactionEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private TransactionEmployeeService transactionEmployeeService;
    @Autowired
    private StorageEmployeeService storageEmployeeService;

    public EmployeeController(EmployeeService employeeService,
                              TransactionEmployeeService transactionEmployeeService,
                              StorageEmployeeService storageEmployeeService) {
        this.employeeService = employeeService;
        this.transactionEmployeeService = transactionEmployeeService;
        this.storageEmployeeService = storageEmployeeService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/role/{roleId}")
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto, @PathVariable Long roleId) {
        return new ResponseEntity<>(this.employeeService.createEmployee(employeeDto, roleId), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(@PathVariable Long id) {
        return new ResponseEntity<>(this.employeeService.getEmployee(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getEmployeeS() {
        return new ResponseEntity<>(this.employeeService.getEmployees(), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        this.employeeService.deleteEmployee(id);
        return new ResponseEntity<>("Employee id: " + id + " was deleted", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/role/{roleId}")
    public ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long roleId, @PathVariable Long id , @RequestBody EmployeeDto employeeDto) {
        return new ResponseEntity<>(this.employeeService.updateEmployee(roleId, id,employeeDto), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE_TRANSACTION','ADMIN')")
    @PostMapping("/sendPackageToStorage")
    public ResponseEntity<String> sendPackageToWarehouse(@RequestParam("orderCode") String orderCode,
                                                         @RequestParam("storageId") String storageId,
                                                         @RequestParam("transactionOfficeId") String transactionOfficeId) {
        this.transactionEmployeeService.transferPackageToStorage(orderCode,
                Long.valueOf(storageId),
                Long.valueOf(transactionOfficeId));
        return new ResponseEntity<>("Package was sent to storage", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('EMPLOYEE_STORAGE','ADMIN')")
    @PostMapping("/sendPackageToTransactionOffice")
    public ResponseEntity<String> sendPackageToTransactionOffice(@RequestParam("orderCode") String orderCode,
                                                                 @RequestParam("storageId") String storageId,
                                                                 @RequestParam("transactionOfficeId") String transactionOfficeId) {
        this.storageEmployeeService.transferPackageToTransactionOffice(orderCode,
                Long.valueOf(storageId),
                Long.valueOf(transactionOfficeId));
        return new ResponseEntity<>("Package was sent to transaction office", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION', 'ADMIN')")
    @GetMapping("/packagesSendToTransactionOffice")
    public List<PackageTransfer> packageTransferToTransactionOffice(
                                    @RequestParam("transactionOfficeId") String transactionOfficeId) {
        return this.transactionEmployeeService.getPackageTransferToTransactionOffice(Long.valueOf(transactionOfficeId));
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE_STORAGE', 'ADMIN')")
    @GetMapping("/packagesSendToStorageOffice")
    public List<PackageTransfer> packageTransferToStorageOffice(@RequestParam("storageId") String storageId) {
        return this.storageEmployeeService.getPackageTransferToStorageOffice(Long.valueOf(storageId));
    }

    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN')")
    @PutMapping("/confirmStoragePackageReceived")
    public ResponseEntity<String> confirmPackageReceived(@RequestParam("orderCode") String orderCode) {
        this.storageEmployeeService.confirmPackageTransferToStorageOffice(orderCode);
        return new ResponseEntity<>("Package was received", HttpStatus.OK);
    }
    @PreAuthorize("hasAnyRole('EMPLOYEE_STORAGE','ADMIN')")
    @PutMapping("/confirmTransactionPackageReceived")
    public ResponseEntity<String> confirmPackageReceived2(@RequestParam("orderCode") String orderCode) {
        this.storageEmployeeService.confirmPackageTransferToStorageOffice(orderCode);
        return new ResponseEntity<>("Package was received", HttpStatus.OK);
    }

}
