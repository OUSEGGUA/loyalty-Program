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

import com.loyalty.model.Service;
import com.loyalty.service.ServiceService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class ServiceController {


	private final ServiceService serviceService;

	private static final Logger log = LoggerFactory.getLogger(ServiceController.class);

	public ServiceController(ServiceService serviceService) {
		this.serviceService = serviceService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewService(@RequestBody Service service, HttpServletRequest request) {
		log.info("Adding Service  {}", service);
		final var serviceEntity = serviceService.create(service);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/Service/findById/{id}")
				.buildAndExpand(serviceEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Service>> getAll() {
		log.info("Retrieving all Services");

		List<Service> services = serviceService.getAllService();

		if (services.isEmpty()) {
			log.warn("No Services found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(services);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Service> getServiceById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve Service by id {}", id);

		final var service = serviceService.getServiceId(id);

		if (service == null) {
			log.info("Service with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Service found {}", service);
		return ResponseEntity.ok(service);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting Service with id {}", id);

		boolean isDeleted = serviceService.deleteService(id);

		if (!isDeleted) {
			log.warn("Service with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Service with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Service service) {
		log.info("Updating Service: {}", service);
		try {
			serviceService.updateService(service);
			return ResponseEntity.ok("Service updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("Service with id {} not found", service.getId());
			return ResponseEntity.badRequest().body("Service not found with id: " + service.getId());
		}
	}

}
