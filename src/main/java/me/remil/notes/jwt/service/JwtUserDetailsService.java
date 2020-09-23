package me.remil.notes.jwt.service;

import java.util.ArrayList;

import me.remil.notes.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import me.remil.notes.entity.Users;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username");
		}
		
		return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
	}

	@Autowired
	public void setUserDAO(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
