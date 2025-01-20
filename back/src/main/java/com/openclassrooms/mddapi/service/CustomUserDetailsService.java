package com.openclassrooms.mddapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import com.openclassrooms.mddapi.repository.UserRepository;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    com.openclassrooms.mddapi.entity.User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
    }
    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }
}