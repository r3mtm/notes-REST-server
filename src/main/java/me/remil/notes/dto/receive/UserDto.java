package me.remil.notes.dto.receive;

import java.sql.Timestamp;

public class UserDto {
    private String username;
    private String password;
    private String secretKey;
    private Timestamp creationTimestamp;

    public UserDto() {
    }

    public UserDto(String username, String password, String secretKey, Timestamp creationTimestamp) {
        this.username = username;
        this.password = password;
        this.secretKey = secretKey;
        this.creationTimestamp = creationTimestamp;
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
        return "UserDto{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
