package me.remil.notes.jwt.util;

import java.io.Serializable;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -4884557723962880479L;
	
	private final String token;
	private final long expiresOn;
	private final String userId;
	private final String secretKey;

	public JwtResponse(String token, long expiresOn, String userId, String secretKey) {
		this.token = token;
		this.expiresOn = expiresOn;
		this.userId = userId;
		this.secretKey = secretKey;
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

	public String getSecretKey() {
		return secretKey;
	}
}
