package com.questa.blogapi.requestfilter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.questa.blogapi.service.UserService;
import com.questa.blogapi.util.ConstantUtil;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserService userDetailsService;


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		System.out.println(request.getRequestURI());
		if ( !ConstantUtil.AUTH_IGNORE_ENDPOINT.contains(request.getRequestURI())) {
				//!request.getRequestURI().equalsIgnoreCase("/signup") && !request.getRequestURI().equalsIgnoreCase("/login")) {
			final String authorizationHeader = request.getHeader("Authorization");
			String username = null;
			String token = null;
			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				return;
			}else {
			//if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
				token = authorizationHeader.substring(7);
				username = userDetailsService.extractUsername(token);
				if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
					if (userDetailsService.validateToken(token, userDetails)) {
						UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
								userDetails, null, userDetails.getAuthorities());
						usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
						SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
					}
				}
			}
			
		}
		filterChain.doFilter(request, response);

	}

}
