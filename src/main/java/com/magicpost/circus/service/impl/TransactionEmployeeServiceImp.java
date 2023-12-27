package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.company.branch.TransactionOffice;
import com.magicpost.circus.entity.info.*;
import com.magicpost.circus.entity.person.Customer;
import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.MagicPostException;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.CustomerDto;
import com.magicpost.circus.payload.TransactionDto;
import com.magicpost.circus.repository.*;
import com.magicpost.circus.service.TransactionEmployeeService;
import com.magicpost.circus.ultis.PackageTransferStatus;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionEmployeeServiceImp implements TransactionEmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private TransactionOfficeRepository transactionOfficeRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private StorageOfficeRepository storageOfficeRepository;

    @Autowired
    private TrackingRepository trackingRepository;
    @Autowired
    private PackageTransferRepository packageTransferRepository;
    @Autowired
    private PackageDeliveryRepository packageDeliveryRepository;

    public TransactionEmployeeServiceImp(EmployeeRepository employeeRepository,
                                         TransactionOfficeRepository transactionOfficeRepository,
                                         TransactionRepository transactionRepository,
                                         OrderRepository orderRepository, StorageOfficeRepository storageOfficeRepository,
                                         TrackingRepository trackingRepository,
                                         PackageTransferRepository packageTransferRepository,
                                         PackageDeliveryRepository packageDeliveryRepository) {
        this.employeeRepository = employeeRepository;
        this.transactionOfficeRepository = transactionOfficeRepository;
        this.transactionRepository = transactionRepository;
        this.orderRepository = orderRepository;
        this.storageOfficeRepository = storageOfficeRepository;
        this.trackingRepository = trackingRepository;
        this.packageTransferRepository = packageTransferRepository;
        this.packageDeliveryRepository = packageDeliveryRepository;
    }

    @Override
    @Transactional
    public TransactionDto createTransaction(TransactionDto transactionDto) {
        Transaction transaction = this.mapToEntity(transactionDto);

        Long employeeId = transactionDto.getEmployeeId();
        Long transactionOfficeId = transactionDto.getTransactionOfficeId();
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));
        CustomerDto customerDto = transactionDto.getCustomerDto();
        Customer customer = this.mapToCustomerEntity(customerDto);
        // set transaction
        transaction.setCustomer(customer);
        transaction.setEmployee(employee);
        transaction.setTransactionId(transactionOffice);

        // order
        Order order = new Order();
        order.setTransactionId(transaction);
        // tracking
        Tracking tracking = new Tracking();
        tracking.setStatus("Đã nhập kho");
        tracking.setOrderId(order);


        // save to database
        this.orderRepository.save(order);
        this.trackingRepository.save(tracking);
        Transaction temp = this.transactionRepository.save(transaction);
        return this.mapToDto(temp);
    }

    @Override
    @Transactional
    public TransactionDto updateTransaction(Long transactionId ,TransactionDto transactionDto) {
        Transaction transaction = this.transactionRepository.findById(transactionId).orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", transactionId));
        Long employeeId = transactionDto.getEmployeeId();
        Long transactionOfficeId = transactionDto.getTransactionOfficeId();
        Employee employee = this.employeeRepository.findById(employeeId).orElseThrow(() -> new ResourceNotFoundException("Employee", "id", employeeId));
        TransactionOffice transactionOffice = this.transactionOfficeRepository.findById(transactionOfficeId).orElseThrow(() -> new ResourceNotFoundException("TransactionOffice", "id", transactionOfficeId));


        // set transaction
        transaction.setEmployee(employee);
        transaction.setTransactionId(transactionOffice);
        transaction.setMass(transactionDto.getMass());
        transaction.setOrderCode(transactionDto.getOrderCode());
        transaction.setPhoneNumber(transactionDto.getPhoneNumber());
        transaction.setReceiveAddress(transactionDto.getReceiveAddress());
        transaction.setReceiver_name(transactionDto.getReceiverName());
        transaction.setTotalPrice(transactionDto.getTotalPrice());
        Customer customer = this.mapToCustomerEntity(transactionDto.getCustomerDto());
        transaction.setCustomer(customer);
        transaction.setDate(transactionDto.getDate());
        transaction.setTransactionId(transactionOffice);

        // save to database
        Transaction temp = this.transactionRepository.save(transaction);
        return this.mapToDto(temp);
    }

    @Override
    @Transactional
    public TransactionDto getTransaction(Long id) {
        Transaction transaction = this.transactionRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Transaction", "id", id));

        return this.mapToDto(transaction);
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        List<Transaction> transactions = this.transactionRepository.findAll();
        List<TransactionDto> transactionDtos = new ArrayList<>();
        transactions.forEach(transaction1 -> {
            TransactionDto transactionDto = this.mapToDto(transaction1);

            // add to list
            transactionDtos.add(transactionDto);
        });
        return transactionDtos;
    }

    // tạo đơn hàng cần chuyển tới kho
    @Override
    public void transferPackageToStorage(String orderCode, Long storageId, Long transactionOfficeId) {
        List<PackageTransfer> packageTransfers = this.packageTransferRepository.findAll();
        packageTransfers.forEach(packageTransfer -> {
            if (packageTransfer.getOrderCode().equals(orderCode)) {
                throw new MagicPostException( HttpStatus.BAD_REQUEST, "Package was sent to storage");
            }
        });
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }
        PackageTransfer packageTransfer = new PackageTransfer();
        packageTransfer.setOrderCode(orderCode);
        packageTransfer.setFrom(PackageTransferStatus.StorageOfficeToTransaction);
        packageTransfer.setStartOffice(transactionOfficeId);
        packageTransfer.setEndOffice(storageId);
        this.packageTransferRepository.save(packageTransfer);
    }

    //  lấy ra danh sách các package đang được chuyển tới điểm giao dịch từ kho
    @Override
    public List<PackageTransfer> getPackageTransferToTransactionOffice(Long transactionOfficeId) {
        List<PackageTransfer> packageTransfers = this.packageTransferRepository.findPackageSendToTransactionOfficeById(transactionOfficeId);

        return packageTransfers;
    }

    // xác nhận đơn hàng đã nhận tại điểm giao dịch được chuyển tới từ kho
    @Override
    public void confirmPackageReceived(String orderCode, Long transactionOfficeId) {
        PackageTransfer packageTransfer = this.packageTransferRepository.findPackageSendFromStorageToTransactionOffice(orderCode, transactionOfficeId);
        if (packageTransfer == null) {
            throw new ResourceNotFoundException("PackageTransfer", orderCode);
        }

        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);

        transaction.setTransactionId(this.transactionOfficeRepository.findById(packageTransfer.getEndOffice()).orElseThrow(()
                -> new ResourceNotFoundException("TransactionOffice", "id", packageTransfer.getEndOffice())));
        Order order = transaction.getOrder();
        Tracking tracking = order.getTracking();
        tracking.setStatus("Đang chuyển kho");
        this.transactionRepository.save(transaction);
        this.packageTransferRepository.delete(packageTransfer);
    }


    // tạo đơn hàng cần chuyển tới tay người nhận
    @Override
    public void createPackageDelivery(String orderCode) {
        this.packageDeliveryRepository.findAll().forEach(packageDelivery -> {
            if (packageDelivery.getOrderCode().equals(orderCode)
                    && packageDelivery.getStatus().equals("Đã giao hàng")) {
                throw new MagicPostException(HttpStatus.BAD_REQUEST, "Package was created");
            }
        });
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        if (transaction == null) {
            throw new ResourceNotFoundException("Transaction", orderCode);
        }

        Order order = transaction.getOrder();
        Tracking tracking = order.getTracking();
        tracking.setStatus("Đang giao hàng");

        PackageDelivery packageDelivery = new PackageDelivery();
        packageDelivery.setOrderCode(orderCode);
        packageDelivery.setStatus("Đợi giao hàng");
        packageDeliveryRepository.save(packageDelivery);
    }

    // lấy ra danh sách các đơn hàng đang được giao tới tay người nhận
    @Override
    public List<PackageDelivery> getPackageDelivering() {
        List<PackageDelivery> packageDelivering = this.packageDeliveryRepository.findAll().stream()
                .filter(packageDelivery -> packageDelivery.getStatus().equals("Đợi giao hàng")).toList();
        return packageDelivering;
    }

    // xác nhận đơn hàng đã giao tới tay người nhận
    @Override
    public void confirmPackageDelivered(String orderCode) {
        PackageDelivery packageDelivery = this.packageDeliveryRepository.findByOrderCode(orderCode);
        if (packageDelivery == null) {
            throw new ResourceNotFoundException("PackageDelivery", orderCode);
        }
        packageDelivery.setStatus("Đã giao hàng");
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        Order order = transaction.getOrder();
        Tracking tracking = order.getTracking();
        tracking.setStatus("Đã giao hàng thành công");
        this.packageDeliveryRepository.save(packageDelivery);
    }

    // xác nhận đơn hàng không giao được tới tay người nhận và trả lại điểm giao dịch
    @Override
    public void confirmPackageNotDelivered(String orderCode) {
        PackageDelivery packageDelivery = this.packageDeliveryRepository.findByOrderCode(orderCode);
        if (packageDelivery == null) {
            throw new ResourceNotFoundException("PackageDelivery", orderCode);
        }
        packageDelivery.setStatus("Không giao được hàng");
        Transaction transaction = this.transactionRepository.findByOrderCode(orderCode);
        Order order = transaction.getOrder();
        Tracking tracking = order.getTracking();
        tracking.setStatus("Giao hàng không thành không");
        this.packageDeliveryRepository.save(packageDelivery);
    }

    // thống kê các đơn hàng đã chuyển thành công, các đơn hàng chuyển không thành công và trả lại điểm giao dịch
    @Override
    public List<PackageDelivery> statisticPackageTransfer() {
        return this.packageDeliveryRepository.findAll();
    }


    private Transaction mapToEntity(TransactionDto transactionDto) {
        Transaction transaction = new Transaction();
        transaction.setMass(transactionDto.getMass());
        transaction.setOrderCode(transactionDto.getOrderCode());
        transaction.setPhoneNumber(transactionDto.getPhoneNumber());
        transaction.setReceiveAddress(transactionDto.getReceiveAddress());
        transaction.setReceiver_name(transactionDto.getReceiverName());
        transaction.setTotalPrice(transactionDto.getTotalPrice());
        transaction.setDate(transactionDto.getDate());

        return transaction;
    }

    private TransactionDto mapToDto(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(transaction.getId());
        transactionDto.setOrderCode(transaction.getOrderCode());
        transactionDto.setReceiverName(transaction.getReceiver_name());
        transactionDto.setTotalPrice(transaction.getTotalPrice());
        transactionDto.setReceiveAddress(transaction.getReceiveAddress());
        transactionDto.setMass(transaction.getMass());
        transactionDto.setPhoneNumber(transaction.getPhoneNumber());
        transactionDto.setEmployeeId(transaction.getEmployee().getId());
        transactionDto.setTransactionOfficeId(transaction.getTransactionId().getId());
        transactionDto.setCustomerDto(this.mapToCustomerDto(transaction.getCustomer()));
        transactionDto.setDate(transaction.getDate());

        return transactionDto;
    }

    private Customer mapToCustomerEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setPhone(customerDto.getPhone());
        customer.setAddress(customerDto.getAddress());
        customer.setEmail(customerDto.getEmail());

        return customer;
    }
    private CustomerDto mapToCustomerDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setFirstName(customer.getFirstName());
        customerDto.setLastName(customer.getLastName());
        customerDto.setPhone(customer.getPhone());
        customerDto.setAddress(customer.getAddress());
        customerDto.setEmail(customer.getEmail());

        return customerDto;
    }
}
