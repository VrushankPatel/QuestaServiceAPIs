package com.questa.blogapi.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.questa.blogapi.exception.QuestaException;
import com.questa.blogapi.model.AuthenticationRequest;
import com.questa.blogapi.model.AuthenticationResponse;
import com.questa.blogapi.model.User;
import com.questa.blogapi.repository.UserRepository;
import com.questa.blogapi.service.util.ConstantUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("userDetailsService")
public class UserService implements UserDetailsService {

	@Autowired
	private AuthenticationManager authenticationManager;

	private JwtUtil jwtUtil = new JwtUtil();

	@Autowired
	UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) {
		Optional<User> user = userRepository.findByEmail(email);

		user.orElseThrow(() -> new QuestaException(ConstantUtil.USER_NOFOUNT_ERROR_MESSAGE,ConstantUtil.USER_NOFOUNT_ERROR_CODE));

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(user.get().getRole().toString()));
		/*
		 * Use the following constructor if the locking and enabling need to be handled
		 * (String username, String password, boolean enabled, boolean
		 * accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
		 * Collection<? extends GrantedAuthority> authorities)
		 */
		return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(),
				authorities);
	}

	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws QuestaException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException | InternalAuthenticationServiceException e) {
			throw new QuestaException(ConstantUtil.INCORRECT_LOGIN_ERROR_MESSAGE,ConstantUtil.INCORRECT_LOGIN_ERROR_CODE);
		}

		final UserDetails userDetails = loadUserByUsername(authenticationRequest.getUsername());
		return new AuthenticationResponse(jwtUtil.generateToken(userDetails));
	}

	public ResponseEntity<String> createUser(@RequestBody User user) throws QuestaException {
		Optional<User> userExist = userRepository.findByEmail(user.getEmail());
		if (userExist.isPresent()) throw new QuestaException(ConstantUtil.EMAIL_ERROR_MESSAGE,ConstantUtil.EMAIL_ERROR_CODE);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		return new ResponseEntity<>(ConstantUtil.USER_CREATED_MESSAGE, HttpStatus.OK);
	}

	public String extractUsername(String token) {
		return jwtUtil.extractUsername(token);
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		return jwtUtil.validateToken(token, userDetails);
	}

}

class JwtUtil {

	private String SECRET_KEY = "secret";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
