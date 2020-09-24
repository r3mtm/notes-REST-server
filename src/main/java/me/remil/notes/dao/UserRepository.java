package me.remil.notes.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import me.remil.notes.entity.Users;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<Users, Integer> {
	Users findByUsername(String username);
	boolean existsByUsername(String username);

	@Query("select secretKey from Users where username=:username")
	String fetchSecretKeyByUsername(@Param("username") String username);
}
