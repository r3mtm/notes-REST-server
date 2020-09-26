package me.remil.notes.service.session;

import me.remil.notes.dao.SessionKeyRepository;
import me.remil.notes.dto.send.SessionKeyDto;
import me.remil.notes.entity.SessionKey;
import me.remil.notes.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class SessionKeyServiceImpl implements SessionKeyService {

    private final SessionKeyRepository sessionKeyRepository;

    @Autowired
    public SessionKeyServiceImpl(SessionKeyRepository sessionKeyRepository) {
        this.sessionKeyRepository = sessionKeyRepository;
    }

    @Override
    public void saveSessionKeys(String username, String encryptedKey, String uuid, Date tokenExDate) {
        Timestamp expDt = new Timestamp(tokenExDate.getTime());
        sessionKeyRepository.save(new SessionKey(uuid, username, encryptedKey, expDt));
    }

    @Override
    public SessionKeyDto getSessionKeys(String username, String uuid) {
        String secretKey = sessionKeyRepository.fetchSecretKeyByUuidAndUsername(uuid, username);
        if (secretKey == null) {
            throw new NotFoundException("No key found with the given details");
        }
        return new SessionKeyDto(secretKey);
    }
}
