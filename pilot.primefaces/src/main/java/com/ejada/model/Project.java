package com.ejada.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;


import com.ejada.enums.ProjectCategoryEnum;


@Entity
public class Project {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name", nullable = false)
	private String name;

	@Column(name="description")
	private String description;
	
	@Column(name="category")
	@Enumerated(EnumType.STRING)
	private ProjectCategoryEnum projectCategory;

	@ManyToOne // managed by
	@JoinColumn(name="manager_id", nullable = false) // project can't be created without manager
	private User manager;
	
	@OneToMany(mappedBy = "project") // has
	private Set<Task> tasks;
	
	
	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
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

	public ProjectCategoryEnum getProjectCategory() {
		return projectCategory;
	}

	public void setProjectCategory(ProjectCategoryEnum projectCategory) {
		this.projectCategory = projectCategory;
	}
	
	
}
