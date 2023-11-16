package com.ufba.stock_control.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufba.stock_control.entities.User;

@Repository
public interface UsersRepository extends JpaRepository<User, UUID>{
  Optional<User> findOneByUserName(String userName) ;
  Optional<User> findOneByUserNameOrEmail(String userName, String email);
  Optional<User> findOneById(UUID id);
  
}
