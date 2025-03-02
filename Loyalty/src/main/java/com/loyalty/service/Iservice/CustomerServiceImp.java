package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.CustomerRepository;
import com.loyalty.model.Customer;
import com.loyalty.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;

public class CustomerServiceImp implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;


	@Override
	public Customer create(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public boolean deleteCustomer(Integer id) {
		if (customerRepository.existsById(id)) {
			customerRepository.deleteById(id);
			return true;
		}
		return false;}

	@Override
	public List<Customer> getAllCustomer() {
		return (List<Customer>) customerRepository.findAll();
	}
	

	@Override
	public Customer getCustomerById(Integer id) {
		return customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateCustomer(Customer customer) {
		if (customerRepository.existsById(customer.getId())) {
			customerRepository.save(customer);
		}else {
			throw new EntityNotFoundException("Admin not found with id: " + customer.getId());
		}
	}

}
