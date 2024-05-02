package com.ejada.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ejada.enums.RoleEnum;
import com.ejada.model.Role;
import com.ejada.util.HibernateUtil;

public class RoleDAO implements IRoleDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public Role findByName(RoleEnum roleName) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();
		try {
			
			session.beginTransaction();
			Query<Role> query = session.createQuery("from Role where name = :roleName", Role.class);
			query.setParameter("roleName", roleName);
			List<Role> roles = (List<Role>) query.list();
			session.getTransaction().commit();
			
			if(roles.size() == 1) return roles.get(0);
			else return null;
		}catch(Exception e) {
			throw e;
		}finally {
			session.close();
		}
	}

	
}
