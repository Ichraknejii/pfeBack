package com.douane.pfeBack.config;

import com.douane.pfeBack.repository.UserRepositry;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// Marque cette classe comme source de configuration de beans pour le contexte Spring.
@Configuration
// Lombok génère automatiquement un constructeur avec 1 paramètre pour chaque champ final,
// ici pour l'injection de UserRepository.

@RequiredArgsConstructor

public class ApplicationConfig {
    private final UserRepositry repositry; // Le dépôt pour les opérations utilisateur.
    // Définit un bean pour le service de détails utilisateur.
    // Utilisé par Spring Security pour charger les données d'un utilisateur.
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> repositry.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
    // Configure le fournisseur d'authentification basé sur DAO.
    // Ici, on définie comment les utilisateurs sont chargés et comment les mots de passe sont vérifiés.
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Définit le service pour trouver les détails de l'utilisateur par son nom d'utilisateur.

        authProvider.setUserDetailsService(userDetailsService());
        // Définit l'encodeur de mot de passe pour vérifier le mot de passe de l'utilisateur.

        authProvider.setPasswordEncoder(passwordEncoder());
return authProvider;
    }


    @Bean     // Crée un gestionnaire d'authentification qui est le point central de l'authentification dans Spring Security.

    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();

    }
    // Définit un bean pour l'encodeur de mot de passe.
    // BCryptPasswordEncoder est un encodeur de mot de passe qui utilise l'algorithme BCrypt.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
