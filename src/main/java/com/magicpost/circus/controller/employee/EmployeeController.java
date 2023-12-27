package com.magicpost.circus.controller.employee;

import com.magicpost.circus.entity.info.PackageDelivery;
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

    // Tìm kiem employee theo username hoặc email hoặc phone hoac tên
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'EMPLOYEE_TRANSACTION', 'EMPLOYEE_STORAGE', " +
            "'MANAGER_TRANSACTION', 'MANAGER_STORAGE')")
    @GetMapping("/search")
    public ResponseEntity<List<EmployeeDto>> searchEmployee(@RequestParam("keyword") String keyword) {
        return new ResponseEntity<>(this.employeeService.searchEmployee(keyword), HttpStatus.OK);
    }

    // chuyển hàng từ điểm giao dịch tới kho
    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN')")
    @PostMapping("/sendPackageFromTransToWarehouse")
    public ResponseEntity<String> sendPackageFromTransToWarehouse(@RequestParam("orderCode") String orderCode,
                                                         @RequestParam("storageId") String storageId,
                                                         @RequestParam("transactionOfficeId") String transactionOfficeId) {
        this.transactionEmployeeService.transferPackageToStorage(orderCode,
                Long.valueOf(storageId),
                Long.valueOf(transactionOfficeId));
        return new ResponseEntity<>("Package was sent to storage "  + storageId, HttpStatus.OK);
    }

    // Chuyển đơn hàng từ kho đến điểm giao dịch
    @PreAuthorize("hasAnyRole('EMPLOYEE_STORAGE','ADMIN')")
    @PostMapping("/sendPackageFromWarehouseToTransactionOffice")
    public ResponseEntity<String> sendPackageFromWarehouseToTransactionOffice(@RequestParam("orderCode") String orderCode,
                                                                 @RequestParam("storageId") String storageId,
                                                                 @RequestParam("transactionOfficeId") String transactionOfficeId) {
        this.storageEmployeeService.transferPackageToTransactionOffice(orderCode,
                Long.valueOf(storageId),
                Long.valueOf(transactionOfficeId));
        return new ResponseEntity<>("Package was sent to transaction office " + transactionOfficeId, HttpStatus.OK);
    }

    // Chuyển đơn hàng từ kho này sang kho khác
    @PreAuthorize("hasAnyRole('EMPLOYEE_STORAGE','ADMIN')")
    @PostMapping("/sendPackageFromWarehouseToWarehouse")
    public ResponseEntity<String> sendPackageFromWarehouseToWarehouse(@RequestParam("orderCode") String orderCode,
                                                                 @RequestParam("currStorageId") String currStorageId,
                                                                 @RequestParam("receivedStorageId") String receivedStorageId) {
        this.storageEmployeeService.transferPackageFromStorageToOtherStorage(orderCode,
                Long.valueOf(currStorageId),
                Long.valueOf(receivedStorageId));
        return new ResponseEntity<>("Package was sent to transaction office", HttpStatus.OK);
    }

    // Danh sách đơn hàng gửi tới điểm giao dịch
    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION', 'ADMIN')")
    @GetMapping("/getPackagesSendToTransactionOffice")
    public List<PackageTransfer> packageTransferToTransactionOffice(
                                    @RequestParam("transactionOfficeId") String transactionOfficeId) {
        return this.transactionEmployeeService.getPackageTransferToTransactionOffice(Long.valueOf(transactionOfficeId));
    }

    // Danh sách đơn hàng gửi tới kho
    @PreAuthorize("hasAnyRole('EMPLOYEE_STORAGE', 'ADMIN')")
    @GetMapping("/getPackagesSendToStorageOffice")
    public List<PackageTransfer> packageTransferToStorageOffice(@RequestParam("storageId") String storageId) {
        return this.storageEmployeeService.getPackageTransferToStorageOffice(Long.valueOf(storageId));
    }

    // Xác nhận đơn hàng về gửi tới kho từ điểm giao dịch hoặc từ kho khác
    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN')")
    @PutMapping("/confirmStoragePackageReceived")
    public ResponseEntity<String> confirmPackageReceived(@RequestParam("orderCode") String orderCode,
                                                         @RequestParam("storageId") String storageId) {
        this.storageEmployeeService.confirmPackageTransferToStorageOffice(orderCode, Long.valueOf(storageId));
        return new ResponseEntity<>("Package was received", HttpStatus.OK);
    }

    // Xác nhận đơn hàng về gửi tới điểm giao dịch từ kho
    @PreAuthorize("hasAnyRole('EMPLOYEE_STORAGE','ADMIN')")
    @PutMapping("/confirmTransactionPackageReceived")
    public ResponseEntity<String> confirmPackageReceived2(@RequestParam("orderCode") String orderCode,
                                                          @RequestParam("transactionOfficeId") String transactionOfficeId) {
        this.transactionEmployeeService.confirmPackageReceived(orderCode, Long.valueOf(transactionOfficeId));
        return new ResponseEntity<>("Package was received", HttpStatus.OK);
    }

    // Tạo đơn hàng giao cho người nhận
    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN')")
    @PostMapping("/createPackageDelivery")
    public ResponseEntity<String> createPackageDelivery(@RequestParam("orderCode") String orderCode) {
        this.transactionEmployeeService.createPackageDelivery(orderCode);
        return new ResponseEntity<>("Package was received", HttpStatus.OK);
    }

    // Danh sách đơn hàng đang giao cho người nhận
    @PreAuthorize("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN')")
    @GetMapping("/packagesDelivering")
    public ResponseEntity<List<PackageDelivery>> getPackageDelivering() {
        return new ResponseEntity<>(this.transactionEmployeeService.getPackageDelivering(), HttpStatus.OK);
    }

    // Xác nhận đơn hàng đã giao cho người nhận
    @PreAuthorize(("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN', 'ROLE_SHIPPER')"))
    @GetMapping("/confirmPackageDelivered")
    public ResponseEntity<String> confirmDelivered(@RequestParam("orderCode") String orderCode) {
        this.transactionEmployeeService.confirmPackageDelivered(orderCode);
        return new ResponseEntity<>("Package was delivered", HttpStatus.OK);
    }

    // Xác nhận đơn hàng không giao được cho người nhận và trả lại điểm giao dịch
    @PreAuthorize(("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN', 'ROLE_SHIPPER')"))
    @GetMapping("/confirmPackageNotDelivered")
    public ResponseEntity<String> confirmNotDelivered(@RequestParam("orderCode") String orderCode) {
        this.transactionEmployeeService.confirmPackageNotDelivered(orderCode);
        return new ResponseEntity<>("Package was not delivered", HttpStatus.OK);
    }

    // Thống kê các đơn hàng đã giao thành công, các đơn hàng chuyển không thành công và trả lại điểm giao dịch
    @PreAuthorize(("hasAnyRole('EMPLOYEE_TRANSACTION','ADMIN')"))
    @GetMapping("/statisticPackageDelivering")
    public ResponseEntity<List<PackageDelivery>> getAllPackageDelivering() {
        return new ResponseEntity<>(this.transactionEmployeeService.statisticPackageTransfer(), HttpStatus.OK);
    }

}
