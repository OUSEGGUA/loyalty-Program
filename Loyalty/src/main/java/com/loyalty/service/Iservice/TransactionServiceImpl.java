package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.TransactionRepository;
import com.loyalty.model.Transaction;
import com.loyalty.service.TransactionService;

import jakarta.persistence.EntityNotFoundException;

public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public Transaction create(Transaction transaction) {
		return transactionRepository.save(transaction);
	}

	@Override
	public boolean deleteTransaction(Integer id) {
		if(transactionRepository.existsById(id)) {
			transactionRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Transaction> getAllTransaction() {
		return (List<Transaction>) transactionRepository.findAll();
	}

	@Override
	public Transaction getTransactionId(Integer id) {
		return transactionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateTransaction(Transaction transaction) {
		if(transactionRepository.existsById(transaction.getId())) {
			transactionRepository.save(transaction);
		}else {
			throw new EntityNotFoundException("Transaction not found with id : "+transaction.getId());
		}
	}

}
