package com.ufba.stock_control.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.ufba.stock_control.dtos.users.CreateUserRequest;
import com.ufba.stock_control.dtos.users.CreateUserResponse;
import com.ufba.stock_control.dtos.users.LoginResponse;
import com.ufba.stock_control.dtos.users.LoginUserRequest;
import com.ufba.stock_control.entities.User;
import com.ufba.stock_control.exceptions.ConflictException;
import com.ufba.stock_control.exceptions.NotFoundException;
import com.ufba.stock_control.exceptions.UnauthorizedException;
import com.ufba.stock_control.helpers.mappers.UsersMapper;
import com.ufba.stock_control.repositories.UsersRepository;


@Service
public class UsersService implements UserDetailsService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final UsersMapper usersMapper;

  public UsersService(
      UsersRepository usersRepository,
      PasswordEncoder passwordEncoder,
      UsersMapper usersMapper) {
    this.usersRepository = usersRepository;
    this.passwordEncoder = passwordEncoder;
    this.usersMapper = usersMapper;
  }

  public LoginResponse login(LoginUserRequest userDTO) {
    User foundUser = this.usersRepository.findOneByUserName(userDTO.getUsername());

    if (foundUser == null) {
      throw new NotFoundException("Usuário não encontrado");
    }

    if (!passwordEncoder.matches(userDTO.getPassword(), foundUser.getPassword())) {
      throw new UnauthorizedException("Usuário não autorizado");
    }

    String token = "";

    return LoginResponse.builder()
        .userId(foundUser.getId())
        .username(foundUser.getUsername())
        .role(foundUser.getRole())
        .token(token)
        .email(foundUser.getEmail())
        .build();
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

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.usersRepository.findOneByUserName(username);
  }
}