package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.RewardRepository;
import com.loyalty.model.Reward;
import com.loyalty.service.RewardService;

import jakarta.persistence.EntityNotFoundException;

public class RewardServiceImpl implements RewardService {

	@Autowired
	private RewardRepository rewardRepository;

	@Override
	public Reward create(Reward reward) {
		return rewardRepository.save(reward);
	}

	@Override
	public boolean deleteReward(Integer id) {
		if (rewardRepository.existsById(id)) {
			rewardRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Reward> getAllReward() {
		return (List<Reward>) rewardRepository.findAll();
	}

	@Override
	public Reward getRewardId(Integer id) {
		return rewardRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateReward(Reward reward) {
		if (rewardRepository.existsById(reward.getId())) {
			rewardRepository.save(reward);
		} else {
			throw new EntityNotFoundException("Reward not found with id : " + reward.getId());
		}
	}

}
