package com.example.gestiondents.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestiondents.entities.User;
import com.example.gestiondents.idao.IDao;
import com.example.gestiondents.repository.UserRepository;

@Service
public class UserService implements IDao<User> {
	@Autowired
	private UserRepository repository;

	@Override
	public User create(User o) {
		return repository.save(o);
	}

	@Override
	public boolean delete(User o) {
		try {
			repository.delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public User update(User o) {
		return repository.save(o);
	}

	@Override
	public User findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}


}

