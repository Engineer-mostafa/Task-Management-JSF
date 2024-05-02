package com.ejada.service;

import com.ejada.enums.RoleEnum;
import com.ejada.model.Role;

public interface IRoleService {
	
	Role getRoleByName(RoleEnum roleName);

}
