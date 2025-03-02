package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.PurchaseRepository;
import com.loyalty.model.Purchase;
import com.loyalty.service.PurchaseService;

import jakarta.persistence.EntityNotFoundException;

public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseRepository purchaseRepository;

	@Override
	public Purchase create(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}

	@Override
	public boolean deletePurchase(Integer id) {
		if (purchaseRepository.existsById(id)) {
			purchaseRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Purchase> getAllPurchase() {
		return (List<Purchase>) purchaseRepository.findAll();
	}

	@Override
	public Purchase getPurchaseId(Integer id) {
		return purchaseRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updatePurchase(Purchase purchase) {
		if (purchaseRepository.existsById(purchase.getId())) {
			purchaseRepository.save(purchase);
		} else {
			throw new EntityNotFoundException("Purchase not found with id" + purchase.getId());
		}
	}

}
