package com.loyalty.dao;

import org.springframework.data.repository.CrudRepository;

import com.loyalty.model.LoyaltyAccount;

public interface LoyaltyAccountRepository extends CrudRepository<LoyaltyAccount, Integer>{

}
