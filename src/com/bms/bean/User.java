package com.bms.bean;

public class User {
	public User() {}
	
	public User(String userName, String userPsswd) {
		this.uName = userName;
		this.uPsswd = new String(userPsswd);
	}
	
	private String uID;
	private String uName;
	private String uPsswd;
	
	public String getuID() {
		return uID;
	}
	public void setuID(String uID) {
		this.uID = uID;
	}
	public String getuName() {
		return uName;
	}
	public void setuName(String uName) {
		this.uName = uName;
	}
	public String getuPsswd() {
		return uPsswd;
	}
	public void setuPsswd(String uPsswd) {
		this.uPsswd = uPsswd;
	}
	
}
