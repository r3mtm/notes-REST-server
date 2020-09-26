package me.remil.notes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "users")
public class Users {
	@Id
	private final String id = UUID.randomUUID().toString();
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private int enabled = 1;

	@Column(name = "secret_key")
	private String secretKey;

	@Column(name = "creation_date")
	private Timestamp creationTimestamp;

	public Users() {
	}

	public Users(String username, String password, String secretKey, Timestamp creationTimestamp) {
		this.username = username;
		this.password = password;
		this.secretKey = secretKey;
		this.creationTimestamp = creationTimestamp;
	}

	public String getId() {
		return id;
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

	public int getEnabled() {
		return enabled;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public Timestamp getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Timestamp creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	@Override
	public String toString() {
		return "Users{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", enabled=" + enabled +
				", secretKey='" + secretKey + '\'' +
				", creationTimestamp=" + creationTimestamp +
				'}';
	}
}
