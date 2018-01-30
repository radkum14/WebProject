package com.rest.servicehandler;

import com.google.gson.JsonObject;
import com.rest.dao.DaoHandler;
import com.rest.domain.Post;
import com.rest.domain.User;
import com.rest.view.PostView;
import com.rest.view.UserView;

public class ServiceHandler {
	
	public UserView createUser(UserView userView){
		System.out.println("Inside createUser method of ServiceHandler");
		
		User user = new User();
		user.setUserName(userView.getUserName());
		user.setEmail(userView.getEmail());
		user.setPassword(userView.getPassword());
		
		DaoHandler dao = new DaoHandler();
		User userResult = dao.createUser(user);
		UserView viewResult = new UserView();
		
		if(userResult != null){
			viewResult.setEmail(userResult.getEmail());
			viewResult.setUserId(userResult.getUserId());
			viewResult.setUserName(userResult.getUserName());
			viewResult.setPassword(userResult.getPassword());	
			return viewResult;
		}
		return null;

	}
	
	public UserView loginUser(UserView userView){
		System.out.println("Inside loginUser method of ServiceHandler");
		 
		DaoHandler dao = new DaoHandler();
		
		User user = new User();
		user.setEmail(userView.getEmail());
		user.setPassword(userView.getPassword());
		
		User userExist = dao.loginUser(user);
		
		 if(userExist != null){
			 UserView userViewResult = new UserView();
			 userViewResult.setUserId(userExist.getUserId());
			 userViewResult.setUserName(userExist.getUserName());
			 userViewResult.setEmail(userExist.getEmail());
			 userViewResult.setPassword(userExist.getPassword());
			 
			 return userViewResult;
		 }
		
		return null;
	}

	public PostView createPost(PostView postView){
		System.out.println("Inside createPost method of ServiceHandler");
		PostView postViewResult = new PostView();
		Post post = new Post();
		post.setTitle(postView.getTitle());
		post.setBody(postView.getBody());
		post.setUserId(postView.getUserId());
		
		DaoHandler dao = new DaoHandler();
		Post postResult = dao.createPost(post);
		if(postResult != null){
			postViewResult.setBody(postResult.getBody());
			postViewResult.setCreatedDate(postResult.getCreatedDate());
			postViewResult.setPostId(postResult.getPostId());
			postViewResult.setTitle(postResult.getTitle());
			postViewResult.setUserId(postResult.getUserId());
			return postViewResult;
		}
		return null;
	}
	
	public UserView editUser(UserView userView){
		System.out.println("Inside editUser method of ServiceHandler");
		
		User user = new User();
		user.setEmail(userView.getEmail());
		user.setPassword(userView.getPassword());
		user.setUserId(userView.getUserId());
		user.setUserName(userView.getUserName());
		
		DaoHandler dao = new DaoHandler();
		User userResult = dao.editUser(user);
		if(userResult != null){
			UserView viewResult = new UserView();
			viewResult.setEmail(userResult.getEmail());
			viewResult.setUserId(userResult.getUserId());
			viewResult.setUserName(userResult.getUserName());
			viewResult.setPassword(userResult.getPassword());	
			return viewResult;
		}
		return null;
	}
	
	
	public PostView editPost(PostView postView){
		System.out.println("Inside editPost method of ServiceHandler");
		
		Post post = new Post();
		post.setBody(postView.getBody());
		post.setPostId(postView.getPostId());
		post.setTitle(postView.getTitle());
		post.setUserId(postView.getUserId());
		
		DaoHandler dao = new DaoHandler();
		Post postResult = dao.editPost(post);
		if(postResult != null){
			PostView postViewResult = new PostView();			
			postViewResult.setBody(post.getBody());
			postViewResult.setPostId(post.getPostId());
			postViewResult.setTitle(post.getTitle());
			postViewResult.setUserId(post.getUserId());
			return postViewResult;
		}
		return null;
	}

	public String deletePost(String postTitle){
		DaoHandler dao = new DaoHandler();
		dao.deletePost(postTitle);
		
		return null;
		
	}
	
	public UserView getUserId(int userId){
		DaoHandler dao = new DaoHandler();
		User user = dao.getUserId(userId);
		if(user != null){
			UserView userView = new UserView();
			userView.setEmail(user.getEmail());
			userView.setPassword(user.getPassword());
			userView.setUserId(user.getUserId());
			userView.setUserName(user.getUserName());
			return userView;
		}
		return null;		
	}
	
	public PostView getPostId(int postId){
		DaoHandler dao = new DaoHandler();
		Post post = dao.getPostId(postId);
		if(post != null){
			PostView postView = new PostView();
			postView.setBody(post.getBody());
			postView.setCreatedDate(post.getCreatedDate());
			postView.setPostId(post.getPostId());
			postView.setTitle(post.getTitle());
			postView.setUserId(post.getUserId());
			return postView;
		}
		return null;		
	}
	
	public PostView getPostTitleOrBody(String title, String body){
		DaoHandler dao = new DaoHandler();
		Post post = dao.getPostTitleOrBody(title, body);
		if(post != null){
			PostView postView = new PostView();
			postView.setBody(post.getBody());
			postView.setCreatedDate(post.getCreatedDate());
			postView.setPostId(post.getPostId());
			postView.setTitle(post.getTitle());
			postView.setUserId(post.getUserId());
			return postView;
		}
		return null;
}
}
