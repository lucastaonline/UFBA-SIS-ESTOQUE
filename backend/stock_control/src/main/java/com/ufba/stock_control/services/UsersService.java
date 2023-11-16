package com.ufba.stock_control.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ufba.stock_control.dtos.users.CreateUserRequest;
import com.ufba.stock_control.dtos.users.CreateUserResponse;
import com.ufba.stock_control.dtos.users.ValidateTokenResponse;
import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.exceptions.ConflictException;
import com.ufba.stock_control.exceptions.NotFoundException;
import com.ufba.stock_control.exceptions.UnauthorizedException;
import com.ufba.stock_control.helpers.mappers.UsersMapper;
import com.ufba.stock_control.repositories.UsersRepository;
import java.util.UUID;


@Service
public class UsersService implements UserDetailsService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final UsersMapper usersMapper;
  private final JwtTokenService tokenService;

  public UsersService(
      UsersRepository usersRepository,
      PasswordEncoder passwordEncoder,
      UsersMapper usersMapper, 
      JwtTokenService tokenService) {
    this.usersRepository = usersRepository;
    this.passwordEncoder = passwordEncoder;
    this.usersMapper = usersMapper;
    this.tokenService = tokenService;
  }

  public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
    User foundUser = this.usersRepository.findOneByUserNameOrEmail(createUserRequest.getUsername(),
        createUserRequest.getEmail());
    if (foundUser != null) {
      throw new ConflictException("Usuário já existente na base");
    }
    String hashedPassword = passwordEncoder.encode(createUserRequest.getPassword());
    createUserRequest.setPassword(hashedPassword);
    User createdUser = this.usersRepository.save(usersMapper.toUserEntity(createUserRequest));
    return CreateUserResponse.builder()
        .id(createdUser.getId())
        .email(createdUser.getEmail())
        .username(createdUser.getUsername())
        .role(createdUser.getRole())
        .build();
  }

  public ValidateTokenResponse validateToken(String authHeader) {
    String token = authHeader.substring(7);
    String username = this.tokenService.validateToken(token);
    if (username == null) { 
      throw new UnauthorizedException("Token inválido, por favor efetue login novamente"); 
    }
    return ValidateTokenResponse
    .builder()
    .message(username + "autenticado com sucesso")
    .build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var foundUser = this.usersRepository.findOneByUserName(username);
    if (foundUser == null) {
      throw new NotFoundException("Usuário não encontrado na base");
    }
    return foundUser;
  }
}