package me.remil.notes.controller;

import java.util.Map;
import java.util.Objects;

import me.remil.notes.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

//import me.remil.notes.dao.UserRepository;
import me.remil.notes.jwt.util.JwtRequest;
import me.remil.notes.jwt.util.JwtResponse;
import me.remil.notes.jwt.util.JwtTokenUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
@RequestMapping("/api")
public class JwtAuthenticationController {

	private AuthenticationManager authenticationManager;
	private JwtTokenUtil jwtTokenUtil;
	private UserDetailsService userDetailsService;
	private UserService userService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> generateAuthenticationToken(@RequestBody JwtRequest authenticationRequest, HttpServletResponse response) throws Exception {
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		final long expiresOn = jwtTokenUtil.getExpirationDateFromToken(token).getTime() / 1000;
		Cookie cookie = new Cookie("token", token);
		cookie.setMaxAge(60 * 60 * 10);
		cookie.setHttpOnly(true);
//		cookie.setSecure(true);
		cookie.setPath("/");
		response.addCookie(cookie);
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:3000");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		String secretKey = userService.fetchSecretKey(authenticationRequest.getUsername());
		return ResponseEntity.ok(new JwtResponse(token, expiresOn, authenticationRequest.getUsername(), secretKey));
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

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}
