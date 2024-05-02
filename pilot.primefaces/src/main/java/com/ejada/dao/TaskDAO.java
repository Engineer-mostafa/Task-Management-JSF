package com.ejada.dao;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.ejada.model.Task;
import com.ejada.model.User;
import com.ejada.util.HibernateUtil;

public class TaskDAO implements ITaskDAO{

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public List<Task> findTaskByUserId(Long id) {
		Session session = sessionFactory.openSession();
		try {
			
			session.beginTransaction();
			Query<Task> query = session.createQuery("from Task where user.id = :id", Task.class);
			query.setParameter("id", id);
			List<Task> tasks = (List<Task>) query.list();
			session.getTransaction().commit();
			return tasks;
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			session.close();
		}
	}

	@Override
	public List<Task> findAll() {
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			 Query<Task> query = session.createQuery("from Task", Task.class);
			 List<Task> tasks = (List<Task>) query.list();
			session.getTransaction().commit();
			return tasks;
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public Task save(Task task) {
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.save(task);
			session.getTransaction().commit();
			return task;
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}finally {
			 session.close();
		}
		
	}

	@Override
	public Task update(Task task) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.update(task);
			session.getTransaction().commit();
			return task;
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
			}finally {
			 session.close();
		}
	}

	@Override
	public void delete(Task task) {
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			task = (Task) session.get(Task.class, task.getId());
			session.delete(task);
			session.getTransaction().commit();
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
			}finally {
			 session.close();
		}
		
	}

	@Override
	public Task findTaskById(Long id) {
		Session session = sessionFactory.openSession();
		try {
			
			session.beginTransaction();
			Query<Task> query = session.createQuery("from Task where id = :id", Task.class);
			query.setParameter("id", id);
			List<Task> tasks = (List<Task>) query.list();
			session.getTransaction().commit();
			
			if(tasks.size() == 1) return tasks.get(0);
			else return null;
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			session.close();
		}
	}

}
