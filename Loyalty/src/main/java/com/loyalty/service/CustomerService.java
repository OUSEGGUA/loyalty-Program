package com.loyalty.service;

import java.util.List;

import com.loyalty.model.Customer;

public interface CustomerService {

	Customer create(Customer customer);

	boolean deleteCustomer(Integer id);

	List<Customer> getAllCustomer();

	Customer getCustomerById(Integer id);

	void updateCustomer(Customer admin);

}
