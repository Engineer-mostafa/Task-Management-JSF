package com.ejada.bean;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.primefaces.event.DragDropEvent;

import com.ejada.enums.LanguageEnum;
import com.ejada.enums.RoleEnum;
import com.ejada.enums.TaskCategoryEnum;
import com.ejada.enums.TaskStatusEnum;
import com.ejada.model.Employees;
import com.ejada.model.Project;
import com.ejada.model.Task;
import com.ejada.model.User;
import com.ejada.service.EmpService;
import com.ejada.service.ProjectService;
import com.ejada.service.TaskService;
import com.ejada.service.UserService;
import com.ejada.util.ResourceBundleUtil;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.util.Locale;

@ManagedBean(name = "home")
@SessionScoped
public class HomeBean implements Serializable {

	@ManagedProperty(value = "#{userLogin}")
	private UserLoginBean loginBean;

	private LanguageEnum currLang;
	private List<Employees> employees;
	private EmpService empService;
	    
	private TaskCategoryEnum selectedCategory;
	private TaskCategoryEnum[] availableTaskCategories;
	private TaskStatusEnum[] availableTaskStatus;
	private Project[] availableProjects; // get all projects to view them
	private User[] availableUsers;
	
	
	

	private List<Task> toDoTasks;
	private List<Task> doneTasks;

	private Task selectedTask;
	private Task newTask;

	private TaskService taskService;
	private ProjectService projectService;
	private UserService userService;
	
	
	public HomeBean() {
		super();

		toDoTasks = new ArrayList<Task>();
		doneTasks = new ArrayList<Task>();
		availableTaskCategories = TaskCategoryEnum.values();
		availableTaskStatus = TaskStatusEnum.values();
		
		taskService = new TaskService();
		projectService = new ProjectService();
		userService = new UserService();
		empService = new EmpService();
		newTask = new Task();
		currLang = LanguageEnum.EN;
		

	}

	
	public void generateReport() throws JRException, IOException{
		
		  InputStream template = FacesContext.getCurrentInstance().getExternalContext()
	                .getResourceAsStream("pages/JSF_IReport.jrxml");
	        // Compile the report template
	        JasperReport jasperReport = JasperCompileManager.compileReport(template);

	        // Create a data source (e.g., a list of JavaBeans)
	        List<Employees> data = empService.findAll();

	        // Fill the report with data
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, new JRBeanCollectionDataSource(data));

	        // Export the report to PDF
	        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition", "inline; filename=report.pdf");

	        try (OutputStream out = response.getOutputStream()) {
	            JasperExportManager.exportReportToPdfStream(jasperPrint, out);
	        }

	        FacesContext.getCurrentInstance().responseComplete();
	}
	public Project[] getAvailableProjects() {
		return availableProjects;
	}

	public void setAvailableProjects(Project[] availableProjects) {
		this.availableProjects = availableProjects;
	}

	public TaskStatusEnum[] getAvailableTaskStatus() {
		return availableTaskStatus;
	}

	public void setAvailableTaskStatus(TaskStatusEnum[] availableTaskStatus) {
		this.availableTaskStatus = availableTaskStatus;
	}

	public TaskCategoryEnum[] getAvailableTaskCategories() {
		return availableTaskCategories;
	}

	public void setAvailableTaskCategories(TaskCategoryEnum[] availableTaskCategories) {
		this.availableTaskCategories = availableTaskCategories;
	}

	public TaskCategoryEnum getSelectedCategory() {
		return selectedCategory;
	}

	public void setSelectedCategory(TaskCategoryEnum selectedCategory) {
		this.selectedCategory = selectedCategory;
	}

	
	
	@PostConstruct
	public void init() {
		try {
			List<Task> tasks;
			User user = loginBean.getUser();
			if (user.getRole().getName() == RoleEnum.ADMIN) {
				tasks = taskService.getAllTasks();
			} else {
				tasks = taskService.getTasksByUserId(user.getId());

			}

			for (Task task : tasks) {
				if (task.getTaskStatus() == TaskStatusEnum.ToDo) {
					toDoTasks.add(task);
				} else {
					doneTasks.add(task);
				}
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	
	

	public void onTaskDrop(DragDropEvent<Task> event) {
		Task task = (Task) event.getData();

		try {
			task.setTaskStatus(TaskStatusEnum.DONE);
			task = taskService.updateTask(task);

			doneTasks.add(task);
			toDoTasks.remove(task);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	public boolean isAdminView() {

		RoleEnum role = this.loginBean.getUser().getRole().getName();
		if (role == RoleEnum.ADMIN)
			return true;
		return false;
	}

	public void deleteTask() {

		User user = loginBean.getUser();

		try {

			if (user.getRole().getName() == RoleEnum.ADMIN) {
				if (toDoTasks.remove(selectedTask)) {
					taskService.deleteTask(selectedTask);
				} else {
					return;
				}
			} else {
				return;
			}

		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}

	public void editTask() {
		User user = loginBean.getUser();
		try {

			if (user.getRole().getName() == RoleEnum.ADMIN && selectedTask != null) {

				
				toDoTasks.remove(selectedTask);


			selectedTask = taskService.editTask(selectedTask);
			if (selectedTask != null)
				if (selectedTask.getTaskStatus() == TaskStatusEnum.ToDo)
					toDoTasks.add(selectedTask);
				else
					doneTasks.add(selectedTask);
				
				
			} else {
				return;
			}

		} catch (Exception e) {
			String summary = "";
			String detailError = "";
			if(!e.getMessage().equals("general")) {
				summary = ResourceBundleUtil.getMessage("taskNameEmptyErrorSummary");
				detailError = e.getMessage();
			}
			FacesMessage message2 = new FacesMessage(FacesMessage.SEVERITY_WARN, summary,detailError);
	        FacesContext.getCurrentInstance().addMessage(null, message2);
	        selectedTask = taskService.getTasksById(selectedTask.getId());
			toDoTasks.add(selectedTask);
		}
	}

	public void loadObjects() {
		try {
			newTask.setProject(new Project());

			List<Project> projects = projectService.getAllTasks();
			availableProjects = (Project[]) projects.toArray(new Project[projects.size()]);
		
			
			newTask.setUser(new User());

			List<User> users = userService.getAllUsers();
			availableUsers = (User[])users.toArray(new User[users.size()]);
		
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}


	

	
	public void createNewTask() {
		User user = loginBean.getUser();
		Long projectId = newTask.getProject().getId();
		Long userId = newTask.getUser().getId();
		try {
			if (user.getRole().getName() == RoleEnum.ADMIN &&  projectId != null) {
				
				if(userId != null) {
					User u = userService.getUserById(userId);
					newTask.setUser(u);
				}
				Project p = projectService.getProjectById(projectId);
				newTask.setProject(p);
				newTask = taskService.createNewTask(newTask);
				if (newTask != null) {
					if (newTask.getTaskStatus() == TaskStatusEnum.ToDo)
						toDoTasks.add(newTask);
					else
						doneTasks.add(newTask);
					

				} else {
					return;
				}
				newTask = new Task();
				newTask.setProject(new Project());
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}

	
	public void toggleLanguage() {
		
		if(currLang == LanguageEnum.EN) {
			currLang = LanguageEnum.AR;
			ResourceBundleUtil.setLocale(Locale.GERMAN);
			}
		else {
			currLang = LanguageEnum.EN;
			FacesContext.getCurrentInstance().getViewRoot().setLocale(Locale.ENGLISH);
			ResourceBundleUtil.setLocale(Locale.ENGLISH);

		}
		
	}
	
	
	public List<Task> getToDoTasks() {
		return toDoTasks;
	}

	
	public User[] getAvailableUsers() {
		return availableUsers;
	}

	public void setAvailableUsers(User[] availableUsers) {
		this.availableUsers = availableUsers;
	}
	public Task getNewTask() {
		return newTask;
	}

	public void setNewTask(Task newTask) {
		this.newTask = newTask;
	}

	public Task getSelectedTask() {
		return selectedTask;
	}

	public void setSelectedTask(Task selectedTask) {
		this.selectedTask = selectedTask;
	}

	public void setToDoTasks(List<Task> toDoTasks) {
		this.toDoTasks = toDoTasks;
	}

	public List<Task> getDoneTasks() {
		return doneTasks;
	}

	public void setDoneTasks(List<Task> doneTasks) {
		this.doneTasks = doneTasks;
	}

	public UserLoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(UserLoginBean loginBean) {
		this.loginBean = loginBean;
	}
	
	public String logout() {
	    FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
	        .remove(UserLoginBean.AUTH_KEY);
	    HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	    if(session != null) session.invalidate();
	    return "logout";
	  }

}
