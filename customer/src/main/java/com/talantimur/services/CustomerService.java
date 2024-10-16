package com.talantimur.services;

import com.talantimur.exception.CustomerNotFoundException;
import com.talantimur.mapper.CustomerMapper;
import com.talantimur.model.Customer;
import com.talantimur.request.CustomerRequest;
import com.talantimur.repository.CustomerRepository;
import com.talantimur.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        var customer = customerRepository.save(mapper.ToCustomer(request));
        return customer.getId();
    }

    public void updateCustomer(CustomerRequest request) {
        var customer = this.customerRepository.findById(request.id())
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Cannot update customer:: No customer found with the provided ID: %s", request.id())
                ));
        mergeCustomer(customer, request);
        this.customerRepository.save(customer);
    }

    private void mergeCustomer(Customer customer, CustomerRequest request) {
        if (StringUtils.isNotBlank(request.firstName())) {
            customer.setFirstName(request.firstName());
        }
        if (StringUtils.isNotBlank(request.email())) {
            customer.setEmail(request.email());
        }
        if (request.address() != null) {
            customer.setAddress(request.address());
        }
    }

    public List<CustomerResponse> findAllCustomers() {
        return  customerRepository.findAll()
                .stream()
                .map(this.mapper::fromCustomer)
                .collect(Collectors.toList());
    }


    public CustomerResponse findById(String id) {
        return this.customerRepository.findById(id)
                .map(mapper::fromCustomer)
                .orElseThrow(() -> new CustomerNotFoundException(String.format("No customer found with the provided ID: %s", id)));
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);

    }
}
