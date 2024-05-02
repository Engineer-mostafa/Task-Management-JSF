package com.ejada.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ejada.enums.TaskCategoryEnum;
import com.ejada.enums.TaskStatusEnum;

@Entity
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;

	@Column(name="description")
	private String description;
	
	@Column(name="category")
	@Enumerated(EnumType.STRING)
	private TaskCategoryEnum taskCategory;
	
	@Column(name="status")
	@Enumerated(EnumType.STRING)
	private TaskStatusEnum taskStatus;
	
	@Column(name="deadline")
	@Temporal(TemporalType.DATE)
	private Date deadline;

	@ManyToOne // assigned to
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne // related to
	@JoinColumn(name = "project_id" , nullable = false) // can't be created without assigning to the project
	private Project project;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TaskCategoryEnum getTaskCategory() {
		return taskCategory;
	}

	public void setTaskCategory(TaskCategoryEnum taskCategory) {
		this.taskCategory = taskCategory;
	}

	public TaskStatusEnum getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(TaskStatusEnum taskStatus) {
		this.taskStatus = taskStatus;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	
	
	
}
