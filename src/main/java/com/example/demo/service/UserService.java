package com.example.demo.service;

import com.example.demo.beans.User;
import com.example.demo.repositories.RoleRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  private BCryptPasswordEncoder passwordEncoder;

  @Autowired
  public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
  }

  public User findByEmail(String email){
    return userRepository.findByEmail(email);
  }

  public Long countByEmail(String email){
    return userRepository.countByEmail(email);
  }

  public User findByUsername(String username){
    return userRepository.findByUsername(username);
  }

  public void saveUser(User user){
    user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
    user.setEnabled(true);
    userRepository.save(user);
  }

  public void saveAdmin(User user) {
    user.setRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
    user.setEnabled(true);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  public User getUser(){

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    String currentusername = authentication.getName();

    User user = userRepository.findByUsername(currentusername);

    return user;
  }

  public String encode(String password) {
    passwordEncoder = new BCryptPasswordEncoder();
    return passwordEncoder.encode(password);
  }
}
