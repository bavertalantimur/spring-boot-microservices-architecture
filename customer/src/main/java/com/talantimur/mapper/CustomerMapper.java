package com.talantimur.mapper;

import com.talantimur.model.Customer;
import com.talantimur.request.CustomerRequest;
import com.talantimur.response.CustomerResponse;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer ToCustomer(CustomerRequest request) {
        if (request == null) {
            return null;
        }
        return  Customer.builder()
                .id(request.id())
                .firstName(request.firstName())
                .email(request.email())
                .address(request.address())
                .build();
    }
    public CustomerResponse fromCustomer(Customer customer) {
        if (customer == null) {
            return null;
        }
        return new CustomerResponse(
                customer.getId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAddress()
        );
    }
}
