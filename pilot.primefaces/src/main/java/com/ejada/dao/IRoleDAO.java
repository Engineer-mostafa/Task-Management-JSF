package com.ejada.dao;

import com.ejada.enums.RoleEnum;
import com.ejada.model.Role;

public interface IRoleDAO {

	Role findByName(RoleEnum roleName);
}
