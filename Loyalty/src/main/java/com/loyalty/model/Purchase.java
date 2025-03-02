package com.loyalty.model;

import java.util.Date;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
@Table(name ="Purchase")
public class Purchase {
	
	@jakarta.persistence.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
    
	@ManyToOne
	@JoinColumn(name = "customer")
	private Customer customer;
	
	@Column(name = "amount")
	private Double amount;
	@Column(name = "pointsEarned")
	private Double pointsEarned;
	@Column(name = "purchaseDate")
	private Date purchaseDate;

}
