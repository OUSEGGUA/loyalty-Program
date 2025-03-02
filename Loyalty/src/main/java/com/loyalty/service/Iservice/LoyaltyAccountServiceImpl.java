package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.LoyaltyAccountRepository;
import com.loyalty.model.LoyaltyAccount;
import com.loyalty.service.LoyaltyAccountService;

import jakarta.persistence.EntityNotFoundException;

public class LoyaltyAccountServiceImpl implements LoyaltyAccountService {

	@Autowired
	private LoyaltyAccountRepository loyaltyAccountRepository;

	@Override
	public LoyaltyAccount create(LoyaltyAccount loyaltyAccount) {
		return loyaltyAccountRepository.save(loyaltyAccount);
	}

	@Override
	public boolean deleteLoyaltyAccount(Integer id) {
		if (loyaltyAccountRepository.existsById(id)) {
			loyaltyAccountRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<LoyaltyAccount> getAllLoyaltyAccount() {
		return (List<LoyaltyAccount>) loyaltyAccountRepository.findAll();
	}

	@Override
	public LoyaltyAccount getLoyaltyAccountId(Integer id) {
		return loyaltyAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateLoyaltyAccount(LoyaltyAccount loyaltyAccount) {
		if (loyaltyAccountRepository.existsById(loyaltyAccount.getId())) {
			loyaltyAccountRepository.save(loyaltyAccount);
		} else {
			throw new EntityNotFoundException("loyaltyAccount not found with id" + loyaltyAccount.getId());

		}
	}

}
