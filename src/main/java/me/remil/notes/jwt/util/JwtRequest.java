package me.remil.notes.jwt.util;

import java.io.Serializable;

public class JwtRequest implements Serializable {

	private static final long serialVersionUID = 5255588600539001706L;
	
	private String username;
	private String password;
	private int id;
	
	public JwtRequest() {}
	
	public JwtRequest(String username, String password, int id) {
		this.username = username;
		this.password = password;
		this.id = id;
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
	
}
