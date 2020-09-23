package me.remil.notes.dao;

import org.springframework.data.repository.CrudRepository;

import me.remil.notes.entity.Users;

public interface UserRepository extends CrudRepository<Users, Integer> {
	Users findByUsername(String username);
	boolean existsByUsername(String username);
}
