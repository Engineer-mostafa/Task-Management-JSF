package com.ejada.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.ejada.enums.RoleEnum;
import com.ejada.model.Role;
import com.ejada.model.User;
import com.ejada.service.RoleService;
import com.ejada.service.UserService;
import com.ejada.util.ResourceBundleUtil;

@ManagedBean(name="userLogin")
@SessionScoped
public class UserLoginBean implements Serializable{

	// calling user service
	public static final String AUTH_KEY = "app.user.id";

	 

	  public boolean isLoggedIn() {
	    return FacesContext.getCurrentInstance().getExternalContext()
	        .getSessionMap().get(AUTH_KEY) != null;
	  }


	  
	  
	private UserService userService;
	private RoleService roleService;
	
	private User user;
	
	public UserLoginBean() {
		super();
		ResourceBundleUtil resourceBundle = new ResourceBundleUtil();
		ResourceBundleUtil.loadResourceBundle();
		this.userService = new UserService();
		roleService = new RoleService();
		user = new User();
	}

	
	public String login() {

        try {
        	user = userService.login(user.getEmail(), user.getPassword());

        	if(user.getId() != null) {
        		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
        		        AUTH_KEY, user.getId());
        		return "home";
        	}
        	else {
        		
        		
        		return "failure";
        	}
        }
        catch (Exception e) {
        	System.err.println(e.getMessage());
    		user = new User();
    		 FacesContext.getCurrentInstance().
          	addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error Message","Check Email and Password" ));
    		 return "failure";
        }
       
       
         
        
    }
	
	public String signup() {
		
		
		 try {
			 	Role normalUserRole = roleService.getRoleByName(RoleEnum.NORMAL);
			 	user.setRole(normalUserRole);
	        	user = userService.signup(user);
	        	
	        	if(user.getId() != null) {
	        		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(
	        		        AUTH_KEY, user.getId());
	        		return "home";
	        	}
	        	else {

	        		return "singup";
	        	}
	        }
	        catch (Exception e) {
	        	System.err.println(e.getMessage());
	        }
	        finally {
	        }
			return null;
		
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}   
}
