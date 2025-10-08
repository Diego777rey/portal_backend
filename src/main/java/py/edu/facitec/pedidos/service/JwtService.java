package py.edu.facitec.pedidos.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import py.edu.facitec.pedidos.entity.Cliente;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;

@Service
public class JwtService {

    private final String SECRET = "claveSuperSecretaMuyLargaQueTengaAlMenos32Caracteres!";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 horas

    // Generar token usando el teléfono del cliente
    public String generateToken(Cliente cliente) {
        return Jwts.builder()
                .setSubject(cliente.getTelefono())  // teléfono como subject
                .claim("id", cliente.getId())
                .claim("email", cliente.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getTelefonoFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        // Creamos un Authentication usando teléfono como principal, sin roles
        return new UsernamePasswordAuthenticationToken(
                claims.getSubject(),
                null,
                Collections.emptyList()
        );
    }
}
