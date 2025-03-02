package com.loyalty.service;

import java.util.List;

import com.loyalty.model.LoyaltyAccount;

public interface LoyaltyAccountService {

	LoyaltyAccount create(LoyaltyAccount loyaltyAccount);

	boolean deleteLoyaltyAccount(Integer id);

	List<LoyaltyAccount> getAllLoyaltyAccount();

	LoyaltyAccount getLoyaltyAccountId(Integer id);

	void updateLoyaltyAccount(LoyaltyAccount loyaltyAccount);

}
