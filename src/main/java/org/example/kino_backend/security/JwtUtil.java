package org.example.kino_backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    private final SecretKey key;
    private final long EXPIRATION_TIME = 1000 * 60 * 60 * 8; // 8 hours

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }


    //This method is called on login, takes the username and generates a token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username) //Who does the token belong to
                .issuedAt(new Date()) //When was it created
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //When does it expire
                .signWith(key) //Sign the token with the secret key
                .compact(); //Create the token into the final string
    }

    //This method takes a token and checks if username fits the token
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(key) //Verify the token with the secret key
                .build()
                .parseSignedClaims(token)// decode the token
                .getPayload()
                .getSubject(); // get the username
    }

    //Tries to get username, if successful, token is valid
    public boolean validateToken(String token) {
        try { getUsernameFromToken(token); return true; }
        catch (Exception e) { return false; }
    }
}
