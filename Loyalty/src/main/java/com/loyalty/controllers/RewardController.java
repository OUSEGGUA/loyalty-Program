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

import com.loyalty.model.Reward;
import com.loyalty.service.RewardService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class RewardController {


	private final RewardService rewardService;

	private static final Logger log = LoggerFactory.getLogger(RewardController.class);

	public RewardController(RewardService rewardService) {
		this.rewardService = rewardService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewReward(@RequestBody Reward reward, HttpServletRequest request) {
		log.info("Adding Reward  {}", reward);
		final var rewardEntity = rewardService.create(reward);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/Reward/findById/{id}")
				.buildAndExpand(rewardEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Reward>> getAll() {
		log.info("Retrieving all Rewards");

		List<Reward> rewards = rewardService.getAllReward();

		if (rewards.isEmpty()) {
			log.warn("No Rewards found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(rewards);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Reward> getRewardById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve Reward by id {}", id);

		final var reward = rewardService.getRewardId(id);

		if (reward == null) {
			log.info("Reward with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Reward found {}", reward);
		return ResponseEntity.ok(reward);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting Reward with id {}", id);

		boolean isDeleted = rewardService.deleteReward(id);

		if (!isDeleted) {
			log.warn("Reward with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Reward with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Reward reward) {
		log.info("Updating Reward: {}", reward);
		try {
			rewardService.updateReward(reward);
			return ResponseEntity.ok("Reward updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("Reward with id {} not found", reward.getId());
			return ResponseEntity.badRequest().body("Reward not found with id: " + reward.getId());
		}
	}

}
