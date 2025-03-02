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

import com.loyalty.model.Transaction;
import com.loyalty.service.TransactionService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;

public class TransactionController {


	private final TransactionService transactionService;

	private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/add")
	public ResponseEntity<Void> createNewTransaction(@RequestBody Transaction transaction, HttpServletRequest request) {
		log.info("Adding Transaction  {}", transaction);
		final var transactionEntity = transactionService.create(transaction);
		final var uri = ServletUriComponentsBuilder.fromContextPath(request).path("/Transaction/findById/{id}")
				.buildAndExpand(transactionEntity.getId()).toUri();

		return ResponseEntity.noContent().location(uri).build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<List<Transaction>> getAll() {
		log.info("Retrieving all Transactions");

		List<Transaction> transactions = transactionService.getAllTransaction();

		if (transactions.isEmpty()) {
			log.warn("No Transactions found");
			return ResponseEntity.noContent().build();
		}

		return ResponseEntity.ok(transactions);
	}

	@GetMapping(value = "/findById/{id}")
	public ResponseEntity<Transaction> getTransactionById(@PathVariable(name = "id") Integer id) {

		log.info("Retrieve Transaction by id {}", id);

		final var transaction = transactionService.getTransactionId(id);

		if (transaction == null) {
			log.info("Transaction with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Transaction found {}", transaction);
		return ResponseEntity.ok(transaction);
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Void> delete(@PathVariable(name = "id") Integer id) {
		log.info("Deleting Transaction with id {}", id);

		boolean isDeleted = transactionService.deleteTransaction(id);

		if (!isDeleted) {
			log.warn("Transaction with id {} not found", id);
			return ResponseEntity.notFound().build();
		}

		log.info("Transaction with id {} deleted successfully", id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/update")
	public ResponseEntity<String> update(@RequestBody Transaction transaction) {
		log.info("Updating Transaction: {}", transaction);
		try {
			transactionService.updateTransaction(transaction);
			return ResponseEntity.ok("Transaction updated successfully");
		} catch (EntityNotFoundException e) {
			log.warn("Transaction with id {} not found", transaction.getId());
			return ResponseEntity.badRequest().body("Transaction not found with id: " + transaction.getId());
		}
	}

}
