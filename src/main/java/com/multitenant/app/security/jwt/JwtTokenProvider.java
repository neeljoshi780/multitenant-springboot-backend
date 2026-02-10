package com.multitenant.app.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JwtTokenProvider
 *
 * Utility component responsible for JWT token operations.
 *
 * Provides functionality to:
 * - Generate signed JWT tokens
 * - Validate token authenticity and expiration
 * - Extract user and tenant identity from token claims
 *
 * Supports stateless, multi-tenant authentication flow.
 */
@Component
public class JwtTokenProvider {

	/* Secret key loaded from application configuration */
	@Value("${jwt.secret}")
	private String jwtSecret;

	/* Token validity duration in milliseconds */
	@Value("${jwt.expiration}")
	private long jwtExpiration;

	/* HMAC signing key used for JWT signature */
	private SecretKey secretKey;

	/**
	 * Initializes cryptographic signing key after application startup.
	 */
	@PostConstruct
	public void init() {
		this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Generates a signed JWT token containing user and tenant information.
	 *
	 * Stores:
	 * - Username (subject)
	 * - Tenant code (routing identifier)
	 * - User role (authorization)
	 */
	public String generateToken(String username, String tenantCode, String role) {
		return Jwts.builder()
			.setSubject(username)
			.claim("tenant", tenantCode)
			.claim("role", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
			.signWith(secretKey, SignatureAlgorithm.HS256)
			.compact();
	}

	/**
	 * Validates token signature and expiration.
	 */
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (JwtException e) {
			return false;
		}
	}

	/**
	 * Extracts authenticated username from token.
	 */
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}

	/**
	 * Extracts tenant code used for routing datasource.
	 */
	public String getTenantCode(String token) {
		return getClaims(token).get("tenant", String.class);
	}

	/**
	 * Parses JWT and returns claims payload.
	 */
	private Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

}