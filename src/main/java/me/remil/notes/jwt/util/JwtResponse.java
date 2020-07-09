package me.remil.notes.jwt.util;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -4884557723962880479L;
	
	private final String token;
	private final long expiresOn;
	private final String userId;

	public JwtResponse(String token, long expiresOn, String userId) {
		this.token = token;
		this.expiresOn = expiresOn;
		this.userId = userId;
	}

	public String getToken() {
		return token;
	}

	public long getExpiresOn() {
		return expiresOn;
	}

	public String getUserId() {
		return userId;
	}
}
