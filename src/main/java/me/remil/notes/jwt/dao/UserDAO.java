package me.remil.notes.jwt.dao;

import org.springframework.data.repository.CrudRepository;

import me.remil.notes.jwt.entity.Users;

public interface UserDAO extends CrudRepository<Users, Integer> {
	Users findByUsername(String username);
}
