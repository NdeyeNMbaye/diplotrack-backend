package sn.isi.diplotrack.msoffre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Droits d'accès du Service Offre.
 * Le COIP publie les offres et suit les candidatures (F10) ; le diplômé consulte
 * les offres (F05), postule et suit ses candidatures (F06).
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/health", "/error").permitAll()

                        // Offres (F10, F05)
                        .requestMatchers(HttpMethod.POST, "/api/v1/offres").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/offres/*/statut").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.GET, "/api/v1/offres/**").hasAnyRole("DIPLOME", "AGENT_COIP")

                        // Candidatures (F06) : le COIP décide, le diplômé postule et suit les siennes
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/candidatures/*/statut").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.POST, "/api/v1/candidatures").hasRole("DIPLOME")
                        .requestMatchers(HttpMethod.GET, "/api/v1/candidatures/mes-candidatures").hasRole("DIPLOME")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
        return http.build();
    }

    /** Keycloak place les rôles dans realm_access.roles ; Spring les attend préfixés par ROLE_. */
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(this::extraireRoles);
        return converter;
    }

    @SuppressWarnings("unchecked")
    private Collection<GrantedAuthority> extraireRoles(Jwt jwt) {
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");
        if (realmAccess == null || realmAccess.get("roles") == null) {
            return Collections.emptyList();
        }
        List<String> roles = (List<String>) realmAccess.get("roles");
        return roles.stream()
                .map(r -> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_" + r))
                .toList();
    }
}