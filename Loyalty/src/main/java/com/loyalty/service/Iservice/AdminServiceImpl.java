package com.loyalty.service.Iservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loyalty.dao.AdminRepository;
import com.loyalty.model.Admin;
import com.loyalty.service.AdminService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminRepository adminRepository;

	@Override
	public Admin create(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
    public boolean deleteAdmin(Integer id) {
        if (adminRepository.existsById(id)) {
            adminRepository.deleteById(id);
            return true;
        }
        return false;
    }

	@Override
	public List<Admin> getAllAdmin() {
		return (List<Admin>) adminRepository.findAll();
	}

	@Override
	public Admin getAdminById(Integer id) {
		return adminRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

	@Override
    public void updateAdmin(Admin admin) {
        if (adminRepository.existsById(admin.getId())) {
            adminRepository.save(admin);
        } else {
            throw new EntityNotFoundException("Admin not found with id: " + admin.getId());
        }
    }

}
