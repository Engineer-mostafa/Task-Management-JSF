package com.ejada.dao;

import org.hibernate.query.Query;

import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ejada.model.Task;
import com.ejada.model.User;
import com.ejada.util.HibernateUtil;

public class UserDAO implements IUserDAO {

	private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
			return user;
		}catch(Exception e) {
			session.getTransaction().rollback();
			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public User update(User user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			session.update(user);
			session.getTransaction().commit();
			return user;
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			User user = (User) session.get(User.class , id);
			session.delete(user);
			session.getTransaction().commit();
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}
	
	@Override
	public void delete(User user) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			user = (User) session.get(User.class , user.getId());
			session.delete(user);
			session.getTransaction().commit();
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			User user = (User) session.get(User.class , id);
			session.getTransaction().commit();
			return user;
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public User findUserByEmail(String email) {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Query<User> query = session.createQuery("from User where email = :email", User.class);
			query.setParameter("email", email);

			List<User> users = (List<User>) query.list();
			session.getTransaction().commit();
			
			if(users.size() == 1) return users.get(0);
			else return null;
			
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			Query<User> query = session.createQuery("from User", User.class);
			session.getTransaction().commit();

			List<User> users = (List<User>) query.list();
			return users;
		}catch(Exception e) {
			session.getTransaction().rollback();

			throw e;
		}finally {
			 session.close();
		}
	}

	

}
