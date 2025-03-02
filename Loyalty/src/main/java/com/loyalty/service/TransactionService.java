package com.loyalty.service;

import java.util.List;

import com.loyalty.model.Transaction;

public interface TransactionService {

	
	
	Transaction create (Transaction transaction);
	boolean deleteTransaction(Integer id);
	List<Transaction> getAllTransaction();
	Transaction getTransactionId(Integer id);
	void updateTransaction (Transaction transaction);
}
