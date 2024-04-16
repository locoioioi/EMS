package com.employee_management_system.EMS.service.jwt;

import com.employee_management_system.EMS.entity.Role;
import com.employee_management_system.EMS.entity.User;
import com.employee_management_system.EMS.exception.EntityNotFoundException;
import com.employee_management_system.EMS.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final UserRepository userRepository;
    private final static String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    private SecretKey getKey() {
        byte[] keyBytes = SECRET_KEY.getBytes();
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

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    @Override
    public String getUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Date getExpirationTime(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private boolean isExpireToken(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean isValidateToken(UserDetails userDetails, String token) {
        return !isExpireToken(token) && userRepository.findByUsername(userDetails.getUsername()) != null;
    }
}
