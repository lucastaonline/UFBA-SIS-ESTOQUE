package com.ufba.stock_control.helpers;

import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.entities.UserRole;
import com.ufba.stock_control.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserData {
  
  @Autowired
  private UsersRepository usersRepository;
  BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
  @EventListener
  public void appReady(ApplicationReadyEvent event) {
    User user = User.builder()
      .userName("admin")
      .password(encoder.encode("admin"))
      .email("admin@vitasaude.com")
      .phoneNumber("71988888888")
      .role(UserRole.ADMIN)
      .enabled(true)
      .build();
    
    usersRepository.save(user);
    
  }
  
}
