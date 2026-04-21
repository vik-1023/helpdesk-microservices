package com.helpdesk.auth.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private static final String SECRET = "helpdesk-secret-key-helpdesk-secret-key";

	private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours

	// Token generate with role
	public String generateToken(String username, String role) {

		return Jwts.builder().subject(username).claim("role", role).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + EXPIRATION))
				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes())).compact();
	}

//	// Extract username from token
//	public String extractUsername(String token) {
//
//		return getClaims(token).getSubject();
//	}
//
//	// Extract role from token
//	public String extractRole(String token) {
//
//		return getClaims(token).get("role", String.class);
//	}
//
//	// Validate token
//	public boolean validateToken(String token) {
//
//		try {
//
//			getClaims(token);
//
//			return true;
//
//		} catch (Exception e) {
//
//			return false;
//		}
//	}
//
//	// Internal claims reader
//	private Claims getClaims(String token) {
//
//		return Jwts.parser().verifyWith(Keys.hmacShaKeyFor(SECRET.getBytes())).build().parseSignedClaims(token)
//				.getPayload();
//	}
}