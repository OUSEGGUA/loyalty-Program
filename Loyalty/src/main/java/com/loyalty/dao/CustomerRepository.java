package com.loyalty.dao;

import org.springframework.data.repository.CrudRepository;

import com.loyalty.model.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer>{

}
