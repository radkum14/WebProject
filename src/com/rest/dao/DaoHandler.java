package com.rest.dao;



import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.rest.domain.Post;
import com.rest.domain.User;

public class DaoHandler {
	
	private static SessionFactory sessionFactory;
	
	public Session getSession(){
		Session session = null;
		try{
			System.out.println("inside getSession method");
			Configuration cfg = new Configuration().configure("hibernate.cfg.xml");			
			sessionFactory = cfg.buildSessionFactory();			
			session = sessionFactory.openSession();
			return session;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return null;
		
	}
		
	public User createUser(User user){
		System.out.println("Inside createUser method of DaoHandler");
		Session session = getSession();
		System.out.println("session is created");
		Transaction tx = session.beginTransaction();
		 session.save(user);
		tx.commit();
		
		System.out.println("user is created successfully");
		return user;
		
	}
	
	public User loginUser(User user){
		
		System.out.println("Inside loginUser of DaoHandler");		
		Session session = getSession();
				
		Query query = session.createQuery("from User where email = :emailID");
		query.setParameter("emailID", user.getEmail());
		
		List list = query.list();
		User userDetail = null;
		
		if(list.size() > 0){
			userDetail= (User) list.get(0);
			System.out.println(userDetail.getEmail());
			System.out.println(userDetail.getPassword());
			return userDetail;
		}
		session.close();
		System.out.println(list.size()+"list size");
		return userDetail;
	}

	public Post createPost(Post post){
		System.out.println("Inside createPost method of DaoHandler");
		Session session = getSession();
		System.out.println("session is created");
		Transaction tx = session.beginTransaction();
		session.save(post);
		tx.commit();
		session.close();
		System.out.println("user is created successfully");	
		
		return post;
	}	
	
	public User editUser(User user){
		System.out.println("Inside editUser method of DaoHandler");
		Session session = getSession();
		Transaction tx= session.beginTransaction();
		session.saveOrUpdate(user);
		tx.commit();
		session.close();
		System.out.println("user details edited successfully");		
		return user;
	}
	
	public Post editPost(Post post){
		System.out.println("Inside editPost method of DaoHandler");
		Session session = getSession();
		Transaction tx= session.beginTransaction();
		session.saveOrUpdate(post);
		tx.commit();
		session.close();
		System.out.println("post details edited successfully");		
		return post;
	}
	
	
	public String deletePost(String incomingTitle){
		System.out.println("Inside deletePost method of DaoHandler");
		Session session = getSession();
		Transaction tx= session.beginTransaction();
		
		Query query = session.createQuery("from Post where title = :titleValue");
		query.setParameter("titleValue", incomingTitle.toString());
		
		List list = query.list();
		System.out.println(list.size()+"list size");
		if(list.size() > 0){
			Post post = (Post) list.get(0);
			System.out.println(post.getPostId());
			session.delete(post);
			tx.commit();
			session.close();
			System.out.println("deleted the given title");
		}else{
			System.out.println("the title is not found");
		}		
		return null;
	}
	
	public User getUserId(int userId){
		System.out.println("Inside getUserId method of DaoHandler" );
		Session session = getSession();
		Query query = session.createQuery("from User where userId = :userID");		
		query.setParameter("userID", userId);
		
		List list = query.list();
		
		if(list.size() > 0){
			System.out.println("user record is available");
			User user  = (User) list.get(0);
			return user;			
		}else{
			System.out.println("no records");
		}
		return null;		
	}
	
	public Post getPostId(int postId){
		System.out.println("Inside getPostId method of DaoHandler" );
		Session session = getSession();
		Query query = session.createQuery("from Post where postId = :postID");		
		query.setParameter("postID", postId);
		
		List list = query.list();
		
		if(list.size() > 0){
			System.out.println("post is available");
			Post post  = (Post) list.get(0);
			return post;			
		}else{
			System.out.println("no post is available");
		}
		return null;		
	}
	
	public Post getPostTitleOrBody(String title, String body){
		System.out.println("Inside getPostTitleOrBody method of DaoHandler" );
		Session session = getSession();
		Query query = null;
		if(title != null){
		 query = session.createQuery("from Post where title = :titleName");
		 query.setParameter("titleName", title);
		}
		if(body != null){
			 query = session.createQuery("from Post where body = :bodyValue");
			 query.setParameter("bodyValue", body);
		}
		
		
		List list = query.list();
		
		if(list.size() > 0){
			System.out.println("post is available");
			Post post  = (Post) list.get(0);
			return post;			
		}else{
			System.out.println("no post is available");
		}
		return null;		
	}
}
