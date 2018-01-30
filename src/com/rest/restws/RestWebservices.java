package com.rest.restws;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.opensymphony.xwork2.ActionContext;
import com.rest.servicehandler.ServiceHandler;
import com.rest.view.PostView;
import com.rest.view.UserView;


@Path("/user")
public class RestWebservices {
	@Context 
	private HttpServletRequest request;
	
	@POST
	@Path("/createUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserView	 createUser(InputStream inputStream){
		/* @FormParam("username")String userName,
		@FormParam("email")String email,@FormParam("password")String password*/
		System.out.println("Inside createUser method");		

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String userName = element.get("username").toString().replace("\"", "");
		String emailId = element.get("email_id").toString().replace("\"", "");
		String password = element.get("password").toString().replace("\"", "");
		
		UserView userView = new UserView();
		userView.setUserName(userName);
		userView.setEmail(emailId);
		userView.setPassword(password);		
				
		ServiceHandler serviceHandler = new ServiceHandler();
		UserView checkUser = serviceHandler.loginUser(userView);
		UserView viewResult = new UserView();
		if(checkUser == null){
		 viewResult = serviceHandler.createUser(userView);
		 viewResult.setOutput("user is created successfully");
		}else{
			System.out.println("User is already with the email_id");
			viewResult.setOutput("User is already with the email_id");
		}
		/*
		String result = "Create "+viewResult; 
		return Response.status(200).entity("hello".toString()).build();*/
			return viewResult;
	}
	
	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/loginUser")
	public UserView loginUser(InputStream inputStream){
		/*@FormParam("username")String userName, @FormParam("password")String password*/
		System.out.println("Inside loginUser method of RestWebservices");
		

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String emailId = element.get("email_id").toString().replace("\"", "");
		String password = element.get("password").toString().replace("\"", "");
		
		
		ServiceHandler serviceHandler = new ServiceHandler();
		UserView userView = new UserView();
		userView.setEmail(emailId);
		userView.setPassword(password);
		UserView userExist = serviceHandler.loginUser(userView);
		
		if(userExist.getEmail().equals(userView.getEmail()) && userExist.getPassword().equals(userView.getPassword())){
			System.out.println("valid user");			
		}
/*		
		if(userExist != null){
			System.out.println("inside userExist");
			HttpSession session = request.getSession();
			session.setAttribute("userDetails", userExist);
			
		try {
			 java.net.URI location = new java.net.URI("http://localhost:8080/RestDemo/userpage.jsp");
			 throw new WebApplicationException(Response.temporaryRedirect(location).build());
			
		} catch (URISyntaxException e) {			
			e.printStackTrace();
		}
		}else{
			try {
				URI uri = new URI("http://localhost:8080/RestDemo/failure.jsp");
				throw new WebApplicationException(Response.temporaryRedirect(uri).build());
			} catch (URISyntaxException e) {			
				e.printStackTrace();
			}
		}*//*
		String result = userExist.getEmail()+" is valid user";
		return Response.status(200).entity(result.toString()).build();*/
		return userExist;
		
		
	}
	
	@POST
	@Path("/createPost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostView createPost(InputStream inputStream){
		/*@FormParam("title")String postTitle,@FormParam("body")String postBody*/
		System.out.println("inside createpost");
		/*
		HttpSession session = request.getSession();		
		UserView sessionUser = (UserView) session.getAttribute("userDetails");*/		

		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String emailId = element.get("email_id").toString().replace("\"", "");
		UserView userView = new UserView();
		userView.setEmail(emailId);
		ServiceHandler serviceHandler = new ServiceHandler();
		UserView userViewResult = serviceHandler.loginUser(userView);
		
		String titleValue = element.get("title").toString().replace("\"", "");
		String bodyValue = element.get("body").toString().replace("\"", "");
		
		
		if(userViewResult != null){
		PostView postView = new PostView();
		postView.setTitle(titleValue);
		postView.setBody(bodyValue);
		postView.setUserId(userViewResult.getUserId());
		
		PostView postViewResult = serviceHandler.createPost(postView);
		return postViewResult;
		}
		return null;
	}
	
	
	@PUT
	@Path("/editUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserView editUser(InputStream inputStream){
		/*@FormParam("username")String userName, @FormParam("password")String password*/
		System.out.println("Inside editUser method of RestWebservice");
		/*HttpSession session = request.getSession();		
		UserView sessionUser = (UserView) session.getAttribute("userDetails");*/
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String emailId = element.get("email_id").toString().replace("\"", "");
		String changePassword = null;String changeUserName = null;
		if(element.has("change_username")){
			changeUserName = element.get("change_username").toString().replace("\"", "");
		}
		if(element.has("change_password")){
			changePassword = element.get("change_password").toString().replace("\"", "");
		}
		
		UserView userView1 = new UserView();
		userView1.setEmail(emailId);
		ServiceHandler serviceHandler = new ServiceHandler();
		UserView userViewResult = serviceHandler.loginUser(userView1);
		
		UserView userView = new UserView();
		userView.setUserId(userViewResult.getUserId());
		userView.setEmail(userViewResult.getEmail());
		if(changePassword != null){
			userView.setPassword(changePassword);
		}else{
			userView.setPassword(userViewResult.getPassword());
		}
		if(changeUserName != null){
			userView.setUserName(changeUserName);
		}else{
			userView.setUserName(userViewResult.getUserName());
		}
				
		UserView userResult = serviceHandler.editUser(userView);
				
		return userResult;
	}
	
	@PUT
	@Path("/editPost")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostView editPost(InputStream inputStream){		
		System.out.println("Inside editPost method of RestWebservice");		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String emailId = element.get("email_id").toString().replace("\"", "");
		String postId = element.get("post_id").toString().replace("\"", "");
		String titleChange = null;String bodyChange = null;
		if(element.has("title_change")){
			titleChange = element.get("title_change").toString().replace("\"", "");
		}
		if(element.has("body_change")){
			bodyChange = element.get("body_change").toString().replace("\"", "");
		}
		
		UserView userView1 = new UserView();
		userView1.setEmail(emailId);
		ServiceHandler serviceHandler = new ServiceHandler();
		UserView userViewResult = serviceHandler.loginUser(userView1);
		
		PostView postView  = serviceHandler.getPostId(Integer.parseInt(postId));
		
		if(userViewResult.getUserId() == postView.getUserId()){
			PostView postView1 = new PostView();
			postView1.setPostId(postView.getPostId());
			postView1.setUserId(postView.getUserId());
			if(titleChange != null){
				postView1.setTitle(titleChange);
			}else{
				postView1.setTitle(postView.getTitle());
			}
			if(bodyChange != null){
				postView1.setBody(bodyChange);
			}else{
				postView1.setBody(postView.getBody());
			}
					
			PostView postResult = serviceHandler.editPost(postView1);
					
			return postResult;
		}
		return null;
	}
	
	@DELETE
	@Path("/deletePost")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deletePost(InputStream inputStream){
		/*@FormParam("title")String postTitle*/
		System.out.println("inside deletepost method of restwebservices");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String postTitle = element.get("postTitle").toString().replace("\"", "");
				
		ServiceHandler serviceHandler = new ServiceHandler();
		serviceHandler.deletePost(postTitle);
		System.out.println("deleted the postTitle");		
		
		return Response.status(200).entity(postTitle +" is deleted successfully").build();
		
	}
	
	
	@GET
	@Path("/getUserId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public UserView getUserId(InputStream inputStream){
		/*@FormParam("userIdValue")int UserId*/
		System.out.println("Inside getUserId method of RestWebservice");
		/*HttpSession session = request.getSession();		
		UserView sessionUser = (UserView) session.getAttribute("userDetails");*/
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String user_id = element.get("user_id").toString().replace("\"", "");;	
		System.out.println(user_id+ "user_id value");
		int user_idVal = Integer.parseInt(user_id);
		
		ServiceHandler serviceHandler = new ServiceHandler();
		UserView userView = serviceHandler.getUserId(user_idVal);
		
		return userView;
	}
	
	@GET
	@Path("/getPostId")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostView getPostId(InputStream inputStream){
		System.out.println("Inside getPostId method of RestWebservice");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());		
		String postId = element.get("post_id").toString().replace("\"", "");		
		int post_idVal = Integer.parseInt(postId);
		
		ServiceHandler serviceHandler = new ServiceHandler();
		PostView postView = serviceHandler.getPostId(post_idVal);
		
		return postView;
	}
	
	@GET
	@Path("/getPostTitleOrBody")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PostView getPostTitleOrBody(InputStream inputStream){
		System.out.println("Inside getPostTitleOrBody method of RestWebservice");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		StringBuffer jsonStr = new StringBuffer();
		String line = null;
			try {
				while( (line = reader.readLine()) != null){
					jsonStr.append(line);
	}
			} catch (IOException e) {
				e.printStackTrace();
			}
		JsonParser parser = new JsonParser();
		JsonObject element = (JsonObject) parser.parse(jsonStr.toString());
		String titleValue = null; String bodyValue = null;
		if(element.has("search_title")){
		 titleValue = element.get("search_title").toString().replace("\"", "");
		}else if(element.has("search_body")){
			 bodyValue = element.get("search_body").toString().replace("\"", "");
		}
		
		ServiceHandler serviceHandler = new ServiceHandler();
		PostView postView = serviceHandler.getPostTitleOrBody(titleValue, bodyValue);
		return postView;
	}
	
}
