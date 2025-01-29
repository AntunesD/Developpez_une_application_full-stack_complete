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

  /**
   * Charge les détails de l'utilisateur par son nom d'utilisateur.
   * 
   * @param username Le nom d'utilisateur
   * @return UserDetails Détails de l'utilisateur
   * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Recherche de l'utilisateur dans la base de données
    com.openclassrooms.mddapi.entity.User user = userRepository.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
    }
    // Retourne les détails de l'utilisateur sous forme d'un objet User de Spring
    // Security
    return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
  }
}
