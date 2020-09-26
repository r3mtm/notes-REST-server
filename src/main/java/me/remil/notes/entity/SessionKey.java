package me.remil.notes.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "session")
public class SessionKey {

    @Id
    private String uuid;

    private String username;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "expiry_date")
    private Timestamp expiryDate;

    public SessionKey() {
    }

    public SessionKey(String uuid, String username, String secretKey, Timestamp expiryDate) {
        this.uuid = uuid;
        this.username = username;
        this.secretKey = secretKey;
        this.expiryDate = expiryDate;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public String toString() {
        return "SessionKey{" +
                "uuid='" + uuid + '\'' +
                ", username='" + username + '\'' +
                ", secretKey='" + secretKey + '\'' +
                ", expiryDate=" + expiryDate +
                '}';
    }
}
