package com.ejada.service;

import java.util.List;
import java.util.Optional;

import com.ejada.exception.BusinessException;
import com.ejada.model.Task;
import com.ejada.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@AllArgsConstructor
@Service
public class TaskService implements ITaskService {

	// you must add validations
	private final TaskRepository taskRepo;


	@Override
	public List<Task> getTasksByUserId(Long userId) {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			List<Task> tasks = taskRepo.findByUserId(userId);
			return tasks;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}
	@Override
	public Task updateTask(Task task) {
		try {
			// put your validations before calling DAO
			task = taskRepo.save(task);
			return task;
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	@Override
	public List<Task> getAllTasks() {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			List<Task> tasks = taskRepo.findAll();
			return tasks;
		}catch(Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	@Override
	public boolean deleteTask(Task task) {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			taskRepo.delete(task);
			return true;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	@Override
	public Task editTask(Task task) throws BusinessException {
		try {
			// put your validations before calling DAO
			if(task.getName().isEmpty()) throw new BusinessException("");
			return taskRepo.save(task);
			
		}catch(Exception e) {
			throw (BusinessException) e;
		}
		
	}
	@Override
	public Task createNewTask(Task task) {
		// TODO Auto-generated method stub
		
		// team of business give us the validation
		// then testing team test the validations
		try {
			// put your validations before calling DAO
			return taskRepo.save(task);
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;
		}
	}
	@Override
	public Task getTasksById(Long id) {
		try {
			// put your validations before calling DAO
			Optional<Task> task = taskRepo.findById(id);
			return task.get();
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

}
