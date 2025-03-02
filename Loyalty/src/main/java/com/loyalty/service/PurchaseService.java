package com.loyalty.service;

import java.util.List;

import com.loyalty.model.Purchase;

public interface PurchaseService {

	Purchase create(Purchase purchase);

	boolean deletePurchase(Integer id);

	List<Purchase> getAllPurchase();

	Purchase getPurchaseId(Integer id);

	void updatePurchase(Purchase purchase);

}
