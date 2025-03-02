package com.loyalty.dao;

import org.springframework.data.repository.CrudRepository;

import com.loyalty.model.LoyaltyProgram;

public interface LoyaltyProgramRepository extends CrudRepository<LoyaltyProgram, Integer>{

}
