package com.example.gestiondents.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestiondents.entities.Role;
import com.example.gestiondents.idao.IDao;
import com.example.gestiondents.repository.RoleRepository;

@Service
public class RoleService implements IDao<Role> {
	@Autowired
	private RoleRepository repository;

	@Override
	public Role create(Role o) {
		return repository.save(o);
	}

	@Override
	public boolean delete(Role o) {
		try {
			repository.delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Role update(Role o) {
		return repository.save(o);
	}

	@Override
	public Role findById(int id) {
		return repository.findById((long)id).orElse(null);
	}
	
	public Role findByName(String name) {
		return repository.findByName(name).orElse(null);
	}

	@Override
	public List<Role> findAll() {
		return repository.findAll();
	}


}

