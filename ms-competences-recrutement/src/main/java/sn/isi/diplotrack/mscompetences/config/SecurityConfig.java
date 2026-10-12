package sn.isi.diplotrack.mscompetences.config;

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

                        // Compétences (F04, F11)
                        .requestMatchers(HttpMethod.POST, "/api/v1/competences").hasRole("DIPLOME")
                        .requestMatchers(HttpMethod.GET, "/api/v1/competences/statut/*").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.GET, "/api/v1/competences/profil/*").hasAnyRole("DIPLOME", "AGENT_COIP")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/competences/*/approuver").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/competences/*/refuser").hasRole("AGENT_COIP")

                        // Besoins de recrutement (F13, F14)
                        .requestMatchers(HttpMethod.POST, "/api/v1/besoins-recrutement").hasRole("ENTREPRISE_PARTENAIRE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/besoins-recrutement").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.GET, "/api/v1/besoins-recrutement/entreprise/*")
                        .hasAnyRole("ENTREPRISE_PARTENAIRE", "AGENT_COIP")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/besoins-recrutement/*/prendre-en-charge").hasRole("AGENT_COIP")

                        // Propositions de profils (F13, F14)
                        .requestMatchers(HttpMethod.POST, "/api/v1/propositions-profils").hasRole("AGENT_COIP")
                        .requestMatchers(HttpMethod.GET, "/api/v1/propositions-profils/besoin/*")
                        .hasAnyRole("AGENT_COIP", "ENTREPRISE_PARTENAIRE")

                        // Indicateurs d'insertion (F12)
                        .requestMatchers("/api/v1/indicateurs/**").hasRole("AGENT_COIP")

                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                );
        return http.build();
    }

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