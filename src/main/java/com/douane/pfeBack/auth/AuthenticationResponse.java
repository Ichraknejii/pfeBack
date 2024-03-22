package com.douane.pfeBack.auth;

// Importation des annotations de Lombok pour simplifier le code
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Annotation @Data de Lombok :
 * Génère automatiquement les méthodes getters, setters, equals, hashCode et toString.
 */
@Data
/**
 * Annotation @Builder de Lombok :
 * Permet l'utilisation du modèle de conception Builder pour créer des instances de cette classe.
 * Cela facilite la création d'objets avec de nombreux attributs de manière plus lisible.
 */
@Builder
/**
 * Annotation @AllArgsConstructor de Lombok :
 * Génère un constructeur avec un paramètre pour chaque champ de la classe.
 * Cela facilite l'initialisation des objets avec des valeurs pour tous les attributs.
 */
@AllArgsConstructor
/**
 * Annotation @NoArgsConstructor de Lombok :
 * Génère un constructeur par défaut sans paramètres.
 * Utile pour la création d'objets sans initialiser les champs immédiatement.
 */
@NoArgsConstructor
public class AuthenticationResponse {
    private String token; // Le token JWT (Json Web Token) utilisé pour l'authentification de l'utilisateur.
}
