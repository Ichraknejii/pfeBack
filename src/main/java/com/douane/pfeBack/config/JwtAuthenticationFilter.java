package com.douane.pfeBack.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// Marque la classe comme un composant Spring, permettant son injection dans le contexte de l'application.
@Component
// Génère automatiquement un constructeur avec des paramètres pour chaque champ final,
// facilitant l'injection de dépendances.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        // Extrait le header 'Authorization' de la requête HTTP.
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;

        // Vérifie si le header d'authentification est présent et bien formé.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extrait le JWT du header 'Authorization'.
        jwt = authHeader.substring(7);
        // Extrait le nom d'utilisateur (email) du JWT.
        userEmail = jwtService.extractUsername(jwt);

        // Si l'email de l'utilisateur n'est pas nul et qu'il n'existe pas déjà d'authentification pour cette session,
        // procède à l'authentification basée sur le token JWT.
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // Vérifie si le token JWT est valide.
            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Ajoute les détails de l'authentification dans le contexte de sécurité.
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Passe la requête et la réponse au filtre suivant dans la chaîne de filtres.
        filterChain.doFilter(request, response);
    }
}
