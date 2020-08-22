package me.remil.notes.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//import me.remil.notes.jwt.dao.UserDAO;
import me.remil.notes.jwt.util.JwtRequest;
import me.remil.notes.jwt.util.JwtResponse;
import me.remil.notes.jwt.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.0.199:3000"})
public class JwtAuthenticationController {

	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	private UserDetailsService userDetailsService;
	
//	@Autowired
//	private UserDAO userDAO;

	@PostMapping("/api/authenticate")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		final long expiresOn = jwtTokenUtil.getExpirationDateFromToken(token).getTime() / 1000;
//		final long userId = userDAO.findByUsername(authenticationRequest.getUsername()).getId();
		
		return ResponseEntity.ok(new JwtResponse(token, expiresOn, authenticationRequest.getUsername()));
	}
	
	private void authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch(DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		}
		/*
		 * BadCredentialsException is handled by custom exception handler
		 */
	}

	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Autowired
	public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}

	@Autowired
	@Qualifier("jwtUserDetailsService")
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
}
