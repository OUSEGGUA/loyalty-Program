package com.loyalty.model;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
@Table(name ="RewardRedemption")
public class RewardRedemption {
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "customer")
	private Customer customer;
	@Column(name = "rewardId")
	private Reward rewardId;
	@Column(name = "redemptionDate")
	private Date redemptionDate;

}
