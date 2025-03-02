package com.loyalty.model;


import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;


@Entity
@ToString
@Data
@Table(name ="Admin")
public class Admin implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "email")
	private String email;
	@Column(name = "password")
	private String password;
	@Column(name = "role")
	private String role;

}
