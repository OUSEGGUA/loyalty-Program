package com.loyalty.service;

import java.util.List;

import com.loyalty.model.RewardRedemption;

public interface RewardRedemptionService {
	
	
	RewardRedemption create (RewardRedemption rewardRedemption);
	boolean deleteRewardRedemption(Integer id);
	List<RewardRedemption> getAllRewardRedemption();
	RewardRedemption getRewardRedemptionId(Integer id);
	void updateRewardRedemption (RewardRedemption rewardRedemption);

}
