package me.remil.notes.service.session;

import me.remil.notes.dto.send.SessionKeyDto;

import java.util.Date;

public interface SessionKeyService {
    void saveSessionKeys(String username, String encryptedKey, String uuid, Date tokenExDate);

    SessionKeyDto getSessionKeys(String username, String uuid);
}
