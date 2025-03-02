package com.loyalty.service;

import java.util.List;

import com.loyalty.model.LoyaltyProgram;

public interface LoyaltyProgramService {

	LoyaltyProgram create(LoyaltyProgram loyaltyProgram);

	boolean deleteLoyaltyProgram(Integer id);

	List<LoyaltyProgram> getAllLoyaltyProgram();

	LoyaltyProgram getLoyaltyProgramId(Integer id);

	void updateLoyaltyProgram(LoyaltyProgram loyaltyProgram);

}
