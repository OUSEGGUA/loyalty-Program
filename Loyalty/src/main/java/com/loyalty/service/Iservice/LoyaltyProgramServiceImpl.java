package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.LoyaltyProgramRepository;
import com.loyalty.model.LoyaltyProgram;
import com.loyalty.service.LoyaltyProgramService;

import jakarta.persistence.EntityNotFoundException;

public class LoyaltyProgramServiceImpl implements LoyaltyProgramService {

	@Autowired
	private LoyaltyProgramRepository loyaltyProgramRepository;

	@Override
	public LoyaltyProgram create(LoyaltyProgram loyaltyProgram) {
		return loyaltyProgramRepository.save(loyaltyProgram);
	}

	@Override
	public boolean deleteLoyaltyProgram(Integer id) {
		if (loyaltyProgramRepository.existsById(id)) {
			loyaltyProgramRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<LoyaltyProgram> getAllLoyaltyProgram() {
		return (List<LoyaltyProgram>) loyaltyProgramRepository.findAll();
	}

	@Override
	public LoyaltyProgram getLoyaltyProgramId(Integer id) {
		return loyaltyProgramRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateLoyaltyProgram(LoyaltyProgram loyaltyProgram) {
		if (loyaltyProgramRepository.existsById(loyaltyProgram.getId())) {
			loyaltyProgramRepository.save(loyaltyProgram);			
		}else {
			throw new EntityNotFoundException("loyaltyProgram not found by id"+loyaltyProgram.getId());
		}
	}

}
