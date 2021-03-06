package me.remil.notes.jwt.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter{

	private UserDetailsService jwtUserDetailsService;
	private JwtTokenUtil jwtTokenUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException, ExpiredJwtException{
		final String requestTokenHeader = request.getHeader("cookie");
		String username = null, jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("token=")) {
			jwtToken = requestTokenHeader.substring(6);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch(IllegalArgumentException e) {
				throw new IllegalArgumentException("Invalid token");
			}
		} else {
			logger.warn("JWT Token doesn't begin with token string");
		}
		
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			
			UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
			
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response);
	}

	@Autowired
	public void setJwtUserDetailsService(UserDetailsService jwtUserDetailsService) {
		this.jwtUserDetailsService = jwtUserDetailsService;
	}

	@Autowired
	public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
		this.jwtTokenUtil = jwtTokenUtil;
	}
}

