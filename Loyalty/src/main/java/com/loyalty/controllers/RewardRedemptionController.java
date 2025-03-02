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

import com.loyalty.model.RewardRedemption;
import com.loyalty.service.RewardRedemptionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class RewardRedemptionController {


	private final RewardRedemptionService rewardRedemptionService;

	private static final Logger log = LoggerFactory.getLogger(RewardRedemptionController.class);

	public RewardRedemptionController(RewardRedemptionService rewardRedemptionService) {
		this.rewardRedemptionService = rewardRedemptionService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewRewardRedemption(@RequestBody RewardRedemption rewardRedemption, HttpServletRequest request) {
		log.info("Adding RewardRedemption  {}", rewardRedemption);
		final var rewardRedemptionEntity = rewardRedemptionService.create(rewardRedemption);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/RewardRedemption/findById/{id}")
				.buildAndExpand(rewardRedemptionEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<RewardRedemption>> getAll() {
		log.info("Retrieving all RewardRedemptions");

		List<RewardRedemption> rewardRedemptions = rewardRedemptionService.getAllRewardRedemption();

		if (rewardRedemptions.isEmpty()) {
			log.warn("No RewardRedemptions found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(rewardRedemptions);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<RewardRedemption> getRewardRedemptionById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve RewardRedemption by id {}", id);

		final var rewardRedemption = rewardRedemptionService.getRewardRedemptionId(id);

		if (rewardRedemption == null) {
			log.info("RewardRedemption with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("RewardRedemption found {}", rewardRedemption);
		return ResponseEntity.ok(rewardRedemption);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting RewardRedemption with id {}", id);

		boolean isDeleted = rewardRedemptionService.deleteRewardRedemption(id);

		if (!isDeleted) {
			log.warn("RewardRedemption with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("RewardRedemption with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody RewardRedemption rewardRedemption) {
		log.info("Updating RewardRedemption: {}", rewardRedemption);
		try {
			rewardRedemptionService.updateRewardRedemption(rewardRedemption);
			return ResponseEntity.ok("RewardRedemption updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("RewardRedemption with id {} not found", rewardRedemption.getId());
			return ResponseEntity.badRequest().body("RewardRedemption not found with id: " + rewardRedemption.getId());
		}
	}

}
