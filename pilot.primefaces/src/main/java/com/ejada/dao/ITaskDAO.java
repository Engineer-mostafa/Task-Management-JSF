package com.ejada.dao;

import java.util.List;

import com.ejada.model.Task;

public interface ITaskDAO {
	
	List<Task> findTaskByUserId(Long id);
	List<Task> findAll();
	Task save(Task task);
	Task update(Task task);
	void delete(Task task);
	Task findTaskById(Long id);
}
