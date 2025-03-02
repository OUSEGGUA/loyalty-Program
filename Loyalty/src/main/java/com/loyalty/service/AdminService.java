package com.loyalty.service;

import java.util.List;

import com.loyalty.model.Admin;

public interface AdminService {

	Admin create(Admin admin);

	List<Admin> getAllAdmin();

	Admin getAdminById(Integer id);

	void updateAdmin(Admin admin);

	boolean deleteAdmin(Integer id);

}
