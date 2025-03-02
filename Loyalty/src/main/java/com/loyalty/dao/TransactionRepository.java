package com.loyalty.dao;

import org.springframework.data.repository.CrudRepository;

import com.loyalty.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

}
