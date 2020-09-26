package me.remil.notes.dto.send;

public class SessionKeyDto {
    private String secretKey;

    public SessionKeyDto() {
    }

    public SessionKeyDto(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public String toString() {
        return "SessionKeyDto{" +
                "secretKey='" + secretKey + '\'' +
                '}';
    }
}
