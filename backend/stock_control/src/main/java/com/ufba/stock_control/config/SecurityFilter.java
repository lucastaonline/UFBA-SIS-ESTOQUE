package com.ufba.stock_control.config;

import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.exceptions.UnauthorizedException;
import com.ufba.stock_control.repositories.UsersRepository;
import com.ufba.stock_control.services.JwtTokenService;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
  @Autowired
  JwtTokenService tokenService;

  @Autowired
  UsersRepository usersRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    var token = this.recoverToken(request);
    
    if (token != null) {
      var subject =  tokenService.validateToken(token);
      var userDetails = usersRepository.findOneByUserName(subject).orElseThrow(() -> new UnauthorizedException("Erro ao criar sessão do usuário")); 
      var authentication =  new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    filterChain.doFilter(request, response);
  }

  private String recoverToken(HttpServletRequest request) {
    var authHeader = request.getHeader("Authorization");
    if (authHeader == null)
      return null;
    return authHeader.replace("Bearer ", "");
  }

}
