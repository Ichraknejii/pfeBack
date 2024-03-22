package com.douane.pfeBack.auth;

// Impo
// rtations des annotations Lombok pour la réduction du boilerplate code
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Annotation Lombok qui génère automatiquement les getters, setters, toString, equals et hashCode.
@Data
// Annotation Lombok qui fournit un builder pattern pour créer des instances de cette classe.
@Builder
// Annotation Lombok qui génère un constructeur avec tous les attributs comme paramètres.
@AllArgsConstructor
// Annotation Lombok qui génère un constructeur par défaut sans paramètres.
@NoArgsConstructor
public class AuthenticationRequest {
    private String email; // Attribut pour stocker l'email de l'utilisateur
    private String password; // Attribut pour stocker le mot de passe de l'utilisateur
}
