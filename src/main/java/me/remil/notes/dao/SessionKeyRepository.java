package me.remil.notes.dao;

import me.remil.notes.entity.SessionKey;;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface SessionKeyRepository extends CrudRepository<SessionKey, String> {

    @Query("select secretKey from SessionKey where uuid=:uuid and username=:username")
    String fetchSecretKeyByUuidAndUsername(@Param("uuid") String uuid, @Param("username") String username);
}
