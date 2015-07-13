package com.example.tools;

public class User {
	private String username = null;
	private String password = null;
	
	public User(String usn, String pwd){
		username = usn;
		password = pwd;
	}
	
	public User(String usn, String pwd,String path) {
		// TODO Auto-generated constructor stub
		username = usn;
		password = pwd;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
