package com.loyalty.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.loyalty.model.Customer;
import com.loyalty.service.CustomerService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class CustomerController {
	private final CustomerService customerService;

	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewCustomer(@RequestBody Customer customer, HttpServletRequest request) {
		log.info("Adding Customer  {}", customer);
		final var customerEntity = customerService.create(customer);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/Customer/findById/{id}")
				.buildAndExpand(customerEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Customer>> getAll() {
		log.info("Retrieving all Customers");

		List<Customer> customers = customerService.getAllCustomer();

		if (customers.isEmpty()) {
			log.warn("No Customers found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(customers);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve Customer by id {}", id);

		final var customer = customerService.getCustomerById(id);

		if (customer == null) {
			log.info("Customer with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Customer found {}", customer);
		return ResponseEntity.ok(customer);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting Customer with id {}", id);

		boolean isDeleted = customerService.deleteCustomer(id);

		if (!isDeleted) {
			log.warn("Customer with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Customer with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Customer customer) {
		log.info("Updating Customer: {}", customer);
		try {
			customerService.updateCustomer(customer);
			return ResponseEntity.ok("Customer updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("Customer with id {} not found", customer.getId());
			return ResponseEntity.badRequest().body("Customer not found with id: " + customer.getId());
		}
	}
}
