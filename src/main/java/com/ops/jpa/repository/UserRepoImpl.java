package com.ops.jpa.repository;
/*package com.jpa.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.jpa.entities.User;

@Repository
public class UserRepoImpl implements UserRepo {

	
	private SessionFactory sessionFactory;
	

	public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	@Override
	@SuppressWarnings("unchecked")
	public User findByEmail(String username) {

		List<User> users = new ArrayList<User>();
		Session session = null;
		try{
		 session = this.sessionFactory.getCurrentSession();
		session.beginTransaction();
		users = session.createQuery("from User where emailId = '"+username+"'").list();
		
		}catch(HibernateException e){
			if(session!=null){
				session.close();
			}
		}
		return users.get(0);

	}
	

}
*/