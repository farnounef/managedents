package com.example.gestiondents.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.example.gestiondents.entities.Admin;
import com.example.gestiondents.idao.IDao;
import com.example.gestiondents.repository.AdminRepository;


@Service
public class AdminService implements IDao<Admin> {
	@Autowired
	private AdminRepository repository;

	@Override
	public Admin create(Admin o) {
		return repository.save(o);
	}

	@Override
	public boolean delete(Admin o) {
		try {
			repository.delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Admin update(Admin o) {
		return repository.save(o);
	}

	@Override
	public Admin findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Admin> findAll() {
		return repository.findAll();
	}


}
