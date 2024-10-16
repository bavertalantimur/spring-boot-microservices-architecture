package com.talantimur.repository;

import com.talantimur.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository  extends MongoRepository<Customer,String> {
}
