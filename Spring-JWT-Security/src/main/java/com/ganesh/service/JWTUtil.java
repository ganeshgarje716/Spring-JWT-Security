package com.ganesh.service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTUtil {
	
	
	//Create token , Validate Token , SecretKey

		private static final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey12345";

		private SecretKey getSigningKey() {
		    return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
		}

		
		//JWT - Signature , Payload , Subject
		public String extractUsername(String token) {
		    return Jwts.parser()
		            .verifyWith(getSigningKey())
		            .build()
		            .parseSignedClaims(token)
		            .getPayload()
		            .getSubject();
		}

	    public Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }

	    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = extractAllClaims(token);
	        return claimsResolver.apply(claims);
	    }
	    private Claims extractAllClaims(String token) {
	        return Jwts.parser()
	                .verifyWith(getSigningKey())   
	                .build()                       
	                .parseSignedClaims(token)     
	                .getPayload();                
	    }

	    private Boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    public String generateToken(UserDetails userDetails) {
	        Map<String, Object> claims = new HashMap<>();
	        return createToken(claims, userDetails.getUsername());
	    }

	    private String createToken(Map<String, Object> claims, String subject) {

	        return Jwts.builder()
	                .claims(claims)                 // ✅ add custom claims
	                .subject(subject)               // ✅ use subject param
	                .issuedAt(new Date())            // ✅ token issue time
	                .expiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 1 minute
	                .signWith(getSigningKey())       // ✅ HS256 automatically
	                .compact();
	    }

	    public boolean validateToken(String token) {
	        try {
	            Jwts.parser()
	                .verifyWith(getSigningKey())
	                .build()
	                .parseSignedClaims(token);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }


}
