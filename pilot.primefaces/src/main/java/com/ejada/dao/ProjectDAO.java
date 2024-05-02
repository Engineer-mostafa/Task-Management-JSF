package com.ejada.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ejada.model.Project;
import com.ejada.model.Task;
import com.ejada.util.HibernateUtil;

public class ProjectDAO implements IProjectDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public List<Project> findAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			 Query<Project> query = session.createQuery("from Project", Project.class);
			 List<Project> projects = (List<Project>) query.list();
			session.getTransaction().commit();
			return projects;
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public Project findById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Query<Project> query = session.createQuery("from Project where id = :id", Project.class);
			query.setParameter("id", id);
			List<Project> projects = (List<Project>) query.list();
			session.getTransaction().commit();
			if(projects.size() == 1) return projects.get(0);

			return null;
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}finally {
			 session.close();
		}
	}

}
