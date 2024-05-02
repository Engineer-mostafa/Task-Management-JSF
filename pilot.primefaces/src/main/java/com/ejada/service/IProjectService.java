package com.ejada.service;

import java.util.List;

import com.ejada.model.Project;


public interface IProjectService {

	List<Project> getAllTasks();
	Project getProjectById(Long id);

}
