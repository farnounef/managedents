package com.example.gestiondents.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestiondents.entities.Groupe;
import com.example.gestiondents.idao.IDao;
import com.example.gestiondents.repository.GroupeRepository;

@Service
public class GroupeService implements IDao<Groupe> {
	@Autowired
	private GroupeRepository repository;

	@Override
	public Groupe create(Groupe o) {
		return repository.save(o);
	}

	@Override
	public boolean delete(Groupe o) {
		try {
			repository.delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Groupe update(Groupe o) {
		return repository.save(o);
	}

	@Override
	public Groupe findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Groupe> findAll() {
		return repository.findAll();
	}


}
