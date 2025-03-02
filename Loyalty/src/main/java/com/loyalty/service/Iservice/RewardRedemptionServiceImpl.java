package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.RewardRedemptionRepository;
import com.loyalty.model.RewardRedemption;
import com.loyalty.service.RewardRedemptionService;

import jakarta.persistence.EntityNotFoundException;

public class RewardRedemptionServiceImpl implements RewardRedemptionService {

	@Autowired
	private RewardRedemptionRepository rewardRedemptionRepository;

	@Override
	public RewardRedemption create(RewardRedemption rewardRedemption) {
		return rewardRedemptionRepository.save(rewardRedemption);
	}

	@Override
	public boolean deleteRewardRedemption(Integer id) {
		if (rewardRedemptionRepository.existsById(id)) {
			rewardRedemptionRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<RewardRedemption> getAllRewardRedemption() {
		return (List<RewardRedemption>) rewardRedemptionRepository.findAll();
	}

	@Override
	public RewardRedemption getRewardRedemptionId(Integer id) {
		return rewardRedemptionRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateRewardRedemption(RewardRedemption rewardRedemption) {
		if (rewardRedemptionRepository.existsById(rewardRedemption.getId())){
			rewardRedemptionRepository.save(rewardRedemption);
		}else {
			throw new EntityNotFoundException("rewardRedemption not found by id : "+rewardRedemption.getId());
		}
	}

}
