package com.loyalty.dao;

import org.springframework.data.repository.CrudRepository;

import com.loyalty.model.Purchase;

public interface PurchaseRepository extends CrudRepository<Purchase, Integer> {

}
