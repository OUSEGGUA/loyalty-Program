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

import com.loyalty.model.LoyaltyAccount;
import com.loyalty.service.LoyaltyAccountService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class LoyaltyAccountController {

	private final LoyaltyAccountService loyaltyAccountService;

	private static final Logger log = LoggerFactory.getLogger(LoyaltyAccountController.class);

	public LoyaltyAccountController(LoyaltyAccountService loyaltyAccountService) {
		this.loyaltyAccountService = loyaltyAccountService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createLoyaltyAccount(@RequestBody LoyaltyAccount loyaltyAccount,
			HttpServletRequest request) {
		log.info("Adding LoyaltyAccount  {}", loyaltyAccount);
		final var LoyaltyAccountEntity = loyaltyAccountService.create(loyaltyAccount);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/LoyaltyAccount/findById/{id}")
				.buildAndExpand(LoyaltyAccountEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<LoyaltyAccount>> getAll() {
		log.info("Retrieving all LoyaltyAccount");

		List<LoyaltyAccount> loyaltyAccount = loyaltyAccountService.getAllLoyaltyAccount();

		if (loyaltyAccount.isEmpty()) {
			log.warn("No loyaltyAccount found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(loyaltyAccount);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<LoyaltyAccount> getLoyaltyAccountById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve LoyaltyAccount by id {}", id);

		final var loyaltyAccount = loyaltyAccountService.getLoyaltyAccountId(id);

		if (loyaltyAccount == null) {
			log.info("LoyaltyAccount with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("LoyaltyAccount found {}", loyaltyAccount);
		return ResponseEntity.ok(loyaltyAccount);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting LoyaltyAccount with id {}", id);

		boolean isDeleted = loyaltyAccountService.deleteLoyaltyAccount(id);

		if (!isDeleted) {
			log.warn("LoyaltyAccount with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("LoyaltyAccount with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody LoyaltyAccount loyaltyAccount) {
		log.info("Updating LoyaltyAccount: {}", loyaltyAccount);
		try {
			loyaltyAccountService.updateLoyaltyAccount(loyaltyAccount);
			return ResponseEntity.ok("LoyaltyAccount updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("LoyaltyAccount with id {} not found", loyaltyAccount.getId());
			return ResponseEntity.badRequest().body("LoyaltyAccount not found with id: " + loyaltyAccount.getId());
		}
	}

}
