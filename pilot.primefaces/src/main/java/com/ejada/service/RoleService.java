package com.ejada.service;

import java.util.List;

import com.ejada.dao.RoleDAO;
import com.ejada.enums.RoleEnum;
import com.ejada.model.Role;
import com.ejada.model.Task;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public class RoleService implements IRoleService {

	private RoleRepository roleRepo;

	
	@Override
	public Role getRoleByName(RoleEnum roleName) {
		// TODO Auto-generated method stub
		try {
			// put your validations before calling DAO
			Role role = roleRepo.findByName(roleName);
			return role;
		}catch(Exception e) {
			System.err.println(e.getMessage());
			return null;

		}
	}

}
