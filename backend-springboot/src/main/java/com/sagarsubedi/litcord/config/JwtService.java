package com.sagarsubedi.litcord.config;

import com.sagarsubedi.litcord.Exceptions.InvalidJwtTokenException;
import com.sagarsubedi.litcord.serviceimpl.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {
    @Value("${litcord.jwt.secret-key}")
    private String jwtSecret;

    @Value("${litcord.jwt.expiration-time}")
    private int jwtExpiration;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
        return Jwts.builder().subject((userPrincipal.getEmail()))
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpiration)).signWith(getSignInKey(), Jwts.SIG.HS256).compact();
    }


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateJwtToken(String authToken) throws InvalidJwtTokenException {
        try {
            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            throw new SignatureException("Error: Invalid JWT signature: " + e.getMessage(), e);
        } catch (MalformedJwtException e) {
            throw new InvalidJwtTokenException("Error: Invalid JWT token: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "Error: JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("Error: JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error: JWT claims string is empty: " + e.getMessage());
        }
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}