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

import com.loyalty.model.LoyaltyProgram;
import com.loyalty.service.LoyaltyProgramService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class LoyaltyProgramController {

	private final LoyaltyProgramService loyaltyProgramService;

	private static final Logger log = LoggerFactory.getLogger(LoyaltyProgramController.class);

	public LoyaltyProgramController(LoyaltyProgramService loyaltyProgramService) {
		this.loyaltyProgramService = loyaltyProgramService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createLoyaltyProgram(@RequestBody LoyaltyProgram loyaltyProgram,
			HttpServletRequest request) {
		log.info("Adding LoyaltyProgram  {}", loyaltyProgram);
		final var loyaltyProgramEntity = loyaltyProgramService.create(loyaltyProgram);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/LoyaltyProgram/findById/{id}")
				.buildAndExpand(loyaltyProgramEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<LoyaltyProgram>> getAll() {
		log.info("Retrieving all LoyaltyProgram");

		List<LoyaltyProgram> loyaltyProgram = loyaltyProgramService.getAllLoyaltyProgram();

		if (loyaltyProgram.isEmpty()) {
			log.warn("No LoyaltyProgram found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(loyaltyProgram);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<LoyaltyProgram> getLoyaltyProgramById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve LoyaltyProgram by id {}", id);

		final var loyaltyProgram = loyaltyProgramService.getLoyaltyProgramId(id);

		if (loyaltyProgram == null) {
			log.info("LoyaltyProgram with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("LoyaltyProgram found {}", loyaltyProgram);
		return ResponseEntity.ok(loyaltyProgram);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting LoyaltyProgram with id {}", id);

		boolean isDeleted = loyaltyProgramService.deleteLoyaltyProgram(id);

		if (!isDeleted) {
			log.warn("LoyaltyProgram with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("LoyaltyProgram with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody LoyaltyProgram loyaltyProgram) {
		log.info("Updating LoyaltyProgram: {}", loyaltyProgram);
		try {
			loyaltyProgramService.updateLoyaltyProgram(loyaltyProgram);
			return ResponseEntity.ok("LoyaltyProgram updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("LoyaltyProgram with id {} not found", loyaltyProgram.getId());
			return ResponseEntity.badRequest().body("LoyaltyProgram not found with id: " + loyaltyProgram.getId());
		}
	}

}
