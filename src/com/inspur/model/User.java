package com.inspur.model;

public class User {
 private int id;
 private String username;
 private String password;
 private String repassword;
 private String email;
 private int state;
 private String activecode;
 private String imageValue;
 private String ip;
 private String lastlogintime;
 
public User(String username, String password, String repassword, String email,
		int state, String activecode, String imageValue) {
	this.username = username;
	this.password = password;
	this.repassword = repassword;
	this.email = email;
	this.state = state;
	this.activecode = activecode;
	this.imageValue = imageValue;
}

public User(String username, String password, String email, int state,
		String activecode) {
	this.username = username;
	this.password = password;
	this.email = email;
	this.state = state;
	this.activecode = activecode;
}

public User() {
	
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
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
public String getRepassword() {
	return repassword;
}
public void setRepassword(String repassword) {
	this.repassword = repassword;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public String getActivecode() {
	return activecode;
}
public void setActivecode(String activecode) {
	this.activecode = activecode;
}
public String getImageValue() {
	return imageValue;
}
public void setImageValue(String imageValue) {
	this.imageValue = imageValue;
}
public String getIp() {
	return ip;
}
public void setIp(String ip) {
	this.ip = ip;
}
public String getLastlogintime() {
	return lastlogintime;
}
public void setLastlogintime(String lastlogintime) {
	this.lastlogintime = lastlogintime;
}

}
