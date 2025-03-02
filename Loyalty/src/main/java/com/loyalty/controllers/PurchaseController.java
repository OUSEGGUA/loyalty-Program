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

import com.loyalty.model.Purchase;
import com.loyalty.service.PurchaseService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class PurchaseController {


	private final PurchaseService purchaseService;

	private static final Logger log = LoggerFactory.getLogger(PurchaseController.class);

	public PurchaseController(PurchaseService purchaseService) {
		this.purchaseService = purchaseService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewPurchase(@RequestBody Purchase purchase, HttpServletRequest request) {
		log.info("Adding Purchase  {}", purchase);
		final var purchaseEntity = purchaseService.create(purchase);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/Purchase/findById/{id}")
				.buildAndExpand(purchaseEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Purchase>> getAll() {
		log.info("Retrieving all Purchases");

		List<Purchase> purchases = purchaseService.getAllPurchase();

		if (purchases.isEmpty()) {
			log.warn("No Purchases found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(purchases);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Purchase> getPurchaseById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve Purchase by id {}", id);

		final var purchase = purchaseService.getPurchaseId(id);

		if (purchase == null) {
			log.info("Purchase with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Purchase found {}", purchase);
		return ResponseEntity.ok(purchase);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting Purchase with id {}", id);

		boolean isDeleted = purchaseService.deletePurchase(id);

		if (!isDeleted) {
			log.warn("Purchase with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Purchase with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Purchase purchase) {
		log.info("Updating Purchase: {}", purchase);
		try {
			purchaseService.updatePurchase(purchase);
			return ResponseEntity.ok("Purchase updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("Purchase with id {} not found", purchase.getId());
			return ResponseEntity.badRequest().body("Purchase not found with id: " + purchase.getId());
		}
	}

}
