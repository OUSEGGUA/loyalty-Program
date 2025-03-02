package com.loyalty.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.loyalty.model.Admin;
import com.loyalty.service.AdminService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	private final AdminService adminService;

	private static final Logger log = LoggerFactory.getLogger(AdminController.class);

	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewAdmin(@RequestBody Admin admin, HttpServletRequest request) {
		log.info("Adding admin  {}", admin);
		final var adminEntity = adminService.create(admin);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/admin/findById/{id}")
				.buildAndExpand(adminEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Admin>> getAll() {
		log.info("Retrieving all admins");

		List<Admin> admins = adminService.getAllAdmin();

		if (admins.isEmpty()) {
			log.warn("No admins found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(admins);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Admin> getAdminById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve admin by id {}", id);

		final var admin = adminService.getAdminById(id);

		if (admin == null) {
			log.info("Admin with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Admin found {}", admin);
		return ResponseEntity.ok(admin);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting admin with id {}", id);

		boolean isDeleted = adminService.deleteAdmin(id);

		if (!isDeleted) {
			log.warn("Admin with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Admin with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Admin admin) {
		log.info("Updating admin: {}", admin);
		try {
			adminService.updateAdmin(admin);
			return ResponseEntity.ok("Admin updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("Admin with id {} not found", admin.getId());
			return ResponseEntity.badRequest().body("Admin not found with id: " + admin.getId());
		}
	}
}
