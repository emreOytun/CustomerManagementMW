package com.emreoytun.customermanagementmw.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {


    // RUN IT WHEN YOU ADD ROW TO THE USER TABLE MANUALLY.
    /*
    @Autowired
    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;

        List<User> users = userRepository.findAll();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        for (User u : users) {
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            userRepository.save(u);
        }
    }
     */

    // It's generated using the website; and this key is secret and unique for my application.
    private static final String SECRET_KEY = "66556A586E3272357538782F4125442A472D4B6150645367566B597033733676";

    // Generates JWT Token using extra claims map and username subject from our UserDetails class.
    public String generateToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails
    ) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Generates JWT Token using username subject from our UserDetails class.
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public boolean isTokenValid(String jwtToken) {
        if (extractUsername(jwtToken) != null && !isTokenExpired(jwtToken)) {
            return true;
        }
        return false;
    }

    // Checks if the JWT Token is expired or not.
    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    // Extracts the expiration date from the JWT Token.
    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    // Extracts username from the JWT Token.
    public String extractUsername(String jwtToken) { return extractClaim(jwtToken, Claims::getSubject); }

    // Generic function to extract different claims from JTW Token.
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    // This function extracts all the claims from the JWT Token.
    private Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey()) // It should check the sign of the JWT Token.
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    // It creates Key from my private SECRET_KEY.
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
