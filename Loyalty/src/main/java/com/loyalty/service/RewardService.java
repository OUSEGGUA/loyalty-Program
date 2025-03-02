package com.loyalty.service;

import java.util.List;

import com.loyalty.model.Reward;

public interface RewardService {
	
	
	Reward create (Reward reward);
	boolean deleteReward(Integer id);
	List<Reward> getAllReward();
	Reward getRewardId(Integer id);
	void updateReward (Reward reward);

}
