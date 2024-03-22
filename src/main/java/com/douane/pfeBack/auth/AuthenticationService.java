package com.douane.pfeBack.auth;

import com.douane.pfeBack.config.JwtService;
import com.douane.pfeBack.models.Role;
import com.douane.pfeBack.models.User;
import com.douane.pfeBack.repository.UserRepositry;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Builder

// Annotation Lombok pour générer un constructeur avec tous les champs finals comme paramètres
@Service
public class AuthenticationService {
    // Dépendances injectées via le constructeur généré par Lombok
    @Autowired
    private  UserRepositry repository ;
    @Autowired
    private  PasswordEncoder passwordEncoder ;
    @Autowired
    private  JwtService jwtService;
    @Autowired
    private  AuthenticationManager authenticationManager;
    // Méthode pour enregistrer un nouvel utilisateur

    public AuthenticationResponse register(RegisterRequest request) {
        // Crée un nouveau UserModel avec les informations fournies et le mot de passe chiffré

        var user= User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.User)// Attribue le rôle par défaut à l'utilisateur
                .build();
        // Sauvegarde l'utilisateur dans la base de données

        repository.save(user);
        // Génère un JWT pour l'utilisateur

        var jwtToken = jwtService.generateToken(user);
        // Retourne la réponse d'authentification avec le token

        return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();

    }
    // Méthode pour authentifier un utilisateur existant

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        // Authentifie l'utilisateur avec ses identifiants

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );        // Récupère l'utilisateur par son email

        var user =repository.findByEmail(request.getEmail())
                .orElseThrow();        // Génère un JWT pour l'utilisateur

        var jwtToken = jwtService.generateToken(user);
        // Retourne la réponse d'authentification avec le token

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
