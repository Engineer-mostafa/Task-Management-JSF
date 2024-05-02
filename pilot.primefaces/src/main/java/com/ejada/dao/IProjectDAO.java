package com.ejada.dao;

import java.util.List;

import com.ejada.model.Project;

public interface IProjectDAO {
	
	List<Project> findAll();
	Project findById(Long id);
}
