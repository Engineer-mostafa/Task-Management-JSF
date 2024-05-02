package com.ejada.service;

import java.util.List;
import java.util.Optional;

import com.ejada.dao.ProjectDAO;
import com.ejada.model.Project;

public class ProjectService implements IProjectService{

	private ProjectDAO projectRepo;
	
	@Override
	public List<Project> getAllTasks() {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
            return projectRepo.findAll();
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	@Override
	public Project getProjectById(Long id) {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			Optional<Project> project = projectRepo.findById(id);
			return project.get();
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

}
