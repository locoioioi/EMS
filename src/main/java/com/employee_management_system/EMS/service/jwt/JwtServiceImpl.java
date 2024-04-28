package com.employee_management_system.EMS.service.jwt;

import com.employee_management_system.EMS.entity.Role;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    private final static String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Map<String,?> getClaims(User user) {
        Map<String, String> claims = new HashMap<>();
        for (Role role : user.getRoles()) {
            claims.put(role.getName(), "true");
        }
        return claims;
    }

    @Override
    public String generateAccessToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "this " + username);
        }
        return Jwts
                .builder()
                .setClaims(getClaims(user))
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60*30*1000))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException(User.class, "this " + username);
        }
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getKey())
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // extract specific claims
    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // extract subject
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // check token
    public boolean isExpiredToken(String token) {
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }
    @Override
    public boolean isValidateToken(UserDetails userDetails, String token) {
        return !isExpiredToken(token) && userDetails.getUsername().equals(extractSubject(token));
    }
}
