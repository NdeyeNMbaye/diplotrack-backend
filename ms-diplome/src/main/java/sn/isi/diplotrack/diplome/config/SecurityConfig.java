package sn.isi.diplotrack.diplome.config;

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

                        // Public : demande de verification par une entreprise non partenaire (F16)
                        .requestMatchers(HttpMethod.POST, "/api/v1/demandes-verification").permitAll()

                        // Traitement des demandes (F08)
                        .requestMatchers(HttpMethod.GET, "/api/v1/demandes-verification").hasRole("AGENT_SCOLARITE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/demandes-verification/statut/*").hasRole("AGENT_SCOLARITE")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/demandes-verification/*/traiter").hasRole("AGENT_SCOLARITE")

                        // Diplomes (F07, F02, F15)
                        .requestMatchers(HttpMethod.POST, "/api/v1/diplomes").hasRole("AGENT_SCOLARITE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/diplomes/verifier/*")
                        .hasAnyRole("ENTREPRISE_PARTENAIRE", "AGENT_SCOLARITE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/diplomes/utilisateur/*")
                        .hasAnyRole("DIPLOME", "AGENT_SCOLARITE")
                        .requestMatchers(HttpMethod.GET, "/api/v1/diplomes/*")
                        .hasAnyRole("DIPLOME", "AGENT_SCOLARITE")

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