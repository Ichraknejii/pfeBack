package com.douane.pfeBack.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// Indique que cette classe est une source de configuration pour l'application.
@Configuration
// Active la sécurité web pour l'application.
@EnableWebSecurity
// Lombok génère automatiquement un constructeur avec un paramètre pour chaque champ final.
// Facilite l'injection de dépendances sans avoir besoin de @Autowired.
@RequiredArgsConstructor
public class SecurityConfiguration {

    // Filtre personnalisé pour la gestion de l'authentification JWT.
    private final JwtAuthenticationFilter jwtAuthFilter;
    // Fournisseur d'authentification personnalisé pour gérer l'authentification des utilisateurs.
    private final AuthenticationProvider authenticationProvider;

    // Définit la chaîne de filtres de sécurité de l'application.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Désactive CSRF (Cross-Site Request Forgery) pour les services REST
                // car ils sont généralement sans état.
                .csrf().disable()
                // Configure les autorisations d'accès aux différentes parties de l'application.
                .authorizeHttpRequests()
                // Permet l'accès non authentifié aux points de terminaison d'authentification.
                .requestMatchers("/api/v1/auth/**").permitAll()
                // Toute autre requête nécessite une authentification.
                .anyRequest().authenticated()
                .and()
                // Configure la gestion de la session pour être sans état,
                // ce qui est préférable pour les API REST.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Ajoute le fournisseur d'authentification personnalisé.
                .authenticationProvider(authenticationProvider)
                // Ajoute le filtre JWT avant le filtre d'authentification par nom d'utilisateur et mot de passe.
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        // Construit et retourne la chaîne de filtres de sécurité configurée.
        return http.build();
    }
}
