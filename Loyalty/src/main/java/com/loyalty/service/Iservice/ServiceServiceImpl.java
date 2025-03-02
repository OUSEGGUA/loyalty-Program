package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.loyalty.dao.ServiceRepository;
import com.loyalty.model.Service;
import com.loyalty.service.ServiceService;

import jakarta.persistence.EntityNotFoundException;

public class ServiceServiceImpl implements ServiceService {
	
	@Autowired
	private ServiceRepository serviceRepository;

	@Override
	public Service create(Service service) {
		return serviceRepository.save(service);
	}

	@Override
	public boolean deleteService(Integer id) {
		if(serviceRepository.existsById(id)) {
			serviceRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public List<Service> getAllService() {
		return (List<Service>) serviceRepository.findAll();
	}

	@Override
	public Service getServiceId(Integer id) {
		return serviceRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
	public void updateService(Service service) {
		if(serviceRepository.existsById(service.getId())) {
			serviceRepository.save(service);
		}else {
			throw new EntityNotFoundException("service not found with id : "+service.getId());
		}
	}

}
