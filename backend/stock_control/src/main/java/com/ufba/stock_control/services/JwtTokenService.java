package com.ufba.stock_control.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.ufba.stock_control.entities.User;

@Service
public class JwtTokenService {
  @Value("${jwt.secret}")
  private String jwtSecretString;

  @Value("${jwt.token.expiresAt}")
  private long expiresAt;

  public String generateToken(User user) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecretString);
      return JWT.create()
          .withIssuer("api")
          .withSubject(user.getUsername())
          .withClaim("role", user.getRole().toString())
          .withExpiresAt(this.getExpirationDate())
          .sign(algorithm);
    } catch (JWTCreationException ex) {
      throw new RuntimeException("Erro ao gerar o token", ex);
    }
  }

  public String validateToken(String token) {
    try {
      Algorithm algorithm = Algorithm.HMAC256(jwtSecretString);
      return JWT.require(algorithm)
        .withIssuer("api")
        .build()
        .verify(token)
        .getSubject();
    } catch (JWTVerificationException ex) {
        throw new TokenExpiredException(token, getExpirationDate());
    }
  }

  private Instant getExpirationDate() {
    return LocalDateTime.now().plusMinutes(expiresAt).toInstant(ZoneOffset.of("-03:00"));
  }
}
