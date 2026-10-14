package sn.isi.diplotrack.utilisateur.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

/**
 * Pont entre DiploTrack et Keycloak pour l'ouverture des comptes (F17).
 *
 * Keycloak reste l'unique détenteur des mots de passe : DiploTrack ne les stocke jamais.
 * Le service s'authentifie avec son compte technique (diplotrack-admin-service).
 */
@Service
public class KeycloakAdminService {

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    private final RestClient http = RestClient.create();
    private final String baseUrl;
    private final String realm;
    private final String clientId;
    private final String clientSecret;

    public KeycloakAdminService(@Value("${keycloak.admin.base-url}") String baseUrl,
                                @Value("${keycloak.admin.realm}") String realm,
                                @Value("${keycloak.admin.client-id}") String clientId,
                                @Value("${keycloak.admin.client-secret}") String clientSecret) {
        this.baseUrl = baseUrl;
        this.realm = realm;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    /**
     * Ouvre le compte avec son rôle et le mot de passe initial saisi par l'administrateur,
     * marqué temporaire : l'utilisateur doit le changer à sa première connexion.
     * Si l'attribution du rôle échoue, le compte est supprimé (jamais d'identité sans rôle).
     *
     * @return le sub Keycloak du compte, qui devient keycloak_id en base
     */
    public String creerUtilisateur(String email, String prenom, String nom, String role, String motDePasseInitial) {
        String jeton = obtenirJeton();
        String keycloakId = creer(jeton, email, prenom, nom, motDePasseInitial);
        try {
            attribuerRole(jeton, keycloakId, role);
        } catch (RuntimeException e) {
            supprimer(keycloakId);
            throw e;
        }
        return keycloakId;
    }

    /** Annule la création d'un compte lorsque l'enregistrement en base échoue. */
    public void supprimer(String keycloakId) {
        try {
            http.delete()
                    .uri(adminUrl() + "/users/" + keycloakId)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + obtenirJeton())
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new KeycloakException("Suppression du compte Keycloak " + keycloakId
                    + " impossible : à nettoyer manuellement", e);
        }
    }

    private String obtenirJeton() {
        if (clientSecret == null || clientSecret.isBlank()) {
            throw new KeycloakException("Secret du client technique absent : définir KEYCLOAK_ADMIN_SECRET");
        }
        MultiValueMap<String, String> formulaire = new LinkedMultiValueMap<>();
        formulaire.add("grant_type", "client_credentials");
        formulaire.add("client_id", clientId);
        formulaire.add("client_secret", clientSecret);
        try {
            Map<String, Object> reponse = http.post()
                    .uri(baseUrl + "/realms/" + realm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formulaire)
                    .retrieve()
                    .body(MAP_TYPE);
            return (String) reponse.get("access_token");
        } catch (RestClientException e) {
            throw new KeycloakException("Authentification du service auprès de Keycloak impossible", e);
        }
    }

    private String creer(String jeton, String email, String prenom, String nom, String motDePasseInitial) {
        Map<String, Object> utilisateur = Map.of(
                "username", email,
                "email", email,
                "firstName", prenom,
                "lastName", nom,
                "enabled", true,
                "emailVerified", true,
                "credentials", List.of(Map.of(
                        "type", "password",
                        "value", motDePasseInitial,
                        "temporary", true)));
        try {
            ResponseEntity<Void> reponse = http.post()
                    .uri(adminUrl() + "/users")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jeton)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(utilisateur)
                    .retrieve()
                    .toBodilessEntity();
            // Keycloak renvoie l'adresse du compte créé ; son dernier segment est le sub
            String adresse = reponse.getHeaders().getFirst(HttpHeaders.LOCATION);
            return adresse.substring(adresse.lastIndexOf('/') + 1);
        } catch (HttpClientErrorException.Conflict e) {
            throw new CompteExistantException("Un compte Keycloak existe déjà pour " + email);
        } catch (RestClientException e) {
            throw new KeycloakException("Création du compte Keycloak impossible", e);
        }
    }

    private void attribuerRole(String jeton, String keycloakId, String role) {
        try {
            Map<String, Object> representation = http.get()
                    .uri(adminUrl() + "/roles/" + role)
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jeton)
                    .retrieve()
                    .body(MAP_TYPE);
            http.post()
                    .uri(adminUrl() + "/users/" + keycloakId + "/role-mappings/realm")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + jeton)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(List.of(representation))
                    .retrieve()
                    .toBodilessEntity();
        } catch (RestClientException e) {
            throw new KeycloakException("Attribution du rôle " + role + " impossible", e);
        }
    }

    private String adminUrl() {
        return baseUrl + "/admin/realms/" + realm;
    }
}