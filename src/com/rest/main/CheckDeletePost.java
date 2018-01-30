package com.rest.main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class CheckDeletePost {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String string = "";
		try {
			InputStream inputStream = new FileInputStream("D:/Prog/TestFile/TestDeletePost.txt");
			InputStreamReader inputReader = new InputStreamReader(inputStream);
			BufferedReader br = new BufferedReader(inputReader);
			String line;
			while ((line = br.readLine()) != null) {
				string += line + "\n";
			}
 
			JSONObject jsonObject = new JSONObject(string);
			//System.out.println(jsonObject);
 
			try {
				URL url = new URL("http://localhost:8080/RestDemo/rest/user/deletePost");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("DELETE");
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				connection.setConnectTimeout(5000);
				connection.setReadTimeout(5000);
				OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
				out.write(jsonObject.toString());
				out.close();
 
 /*
				BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				
				String result;
				while ((result = in.readLine()) != null) {
					System.out.println(result);
				}
				*/
				System.out.println("\n REST Service Invoked Successfully..");
				/*
				in.close();*/
			} catch (Exception e) {
				System.out.println("\nError while calling REST Service");
				System.out.println(e);
			}
			} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
