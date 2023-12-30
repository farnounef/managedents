package com.example.gestiondents.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gestiondents.entities.Student;
import com.example.gestiondents.idao.IDao;
import com.example.gestiondents.repository.StudentRepository;

@Service
public class StudentService implements IDao<Student> {
	@Autowired
	private StudentRepository repository;

	@Override
	public Student create(Student o) {
		return repository.save(o);
	}

	@Override
	public boolean delete(Student o) {
		try {
			repository.delete(o);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Student update(Student o) {
		return repository.save(o);
	}

	@Override
	public Student findById(int id) {
		return repository.findById(id).orElse(null);
	}

	@Override
	public List<Student> findAll() {
		return repository.findAll();
	}


}
