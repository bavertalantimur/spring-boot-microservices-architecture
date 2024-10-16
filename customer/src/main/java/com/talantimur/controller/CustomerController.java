package com.talantimur.controller;

import com.talantimur.request.CustomerRequest;
import com.talantimur.response.CustomerResponse;
import com.talantimur.services.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    @PostMapping
    ResponseEntity<String> createCustomer(@RequestBody @Valid CustomerRequest request) {
        System.out.println("merhaba");
        return  ResponseEntity.ok(customerService.createCustomer(request));

    }

    @PutMapping
    public ResponseEntity<Void> updateCustomer(@RequestBody @Valid CustomerRequest request) {
    customerService.updateCustomer(request);
    return ResponseEntity.accepted().build();
    }


    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(this.customerService.findAllCustomers());
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> existsById(@PathVariable("customer-id") String customerId) {
        return ResponseEntity.ok(customerService.findById(customerId));
    }
    @DeleteMapping("/{customer-id}")
    public ResponseEntity<Void> delete(@PathVariable("customer-id") String customerId) {
        this.customerService.deleteCustomer(customerId);
        return ResponseEntity.accepted().build();
    }
}
