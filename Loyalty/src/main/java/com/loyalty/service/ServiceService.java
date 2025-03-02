package com.loyalty.service;

import java.util.List;

import com.loyalty.model.Service;

public interface ServiceService {

	
	
	Service create (Service service);
	boolean deleteService(Integer id);
	List<Service> getAllService();
	Service getServiceId(Integer id);
	void updateService (Service service);
}
