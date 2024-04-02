package ua.foxmided.foxstudent103852.cardatabaserestservice.controller;

import static org.assertj.core.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import dasniko.testcontainers.keycloak.KeycloakContainer;

@Testcontainers(disabledWithoutDocker = true)
public abstract class KeycloakTestcontainersConfigurer {
    protected final static String REALM_NAME = "cardatabase_realm";
    protected final static String CLIENT_ID = "spring_security";
    protected final static String INVALID_USER = "user";
    protected final static String INVALID_USER_PASSWORD = "user";
    protected final static String VALID_USER = "user1";
    protected final static String VALID_USER_PASSWORD = "password";
    protected final static String VALID_ADMIN = "user2";
    protected final static String VALID_ADMIN_PASSWORD = "password";
    private final static String KEYCLOAK_IMAGE = "quay.io/keycloak/keycloak:22.0.3";
    private final static String REALM_IMPORT_FILE = "/cardatabase_realm.json";

    protected static final KeycloakContainer keycloak;

    static {
        keycloak = new KeycloakContainer(KEYCLOAK_IMAGE)
                .withRealmImportFile(REALM_IMPORT_FILE);
        keycloak.start();
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri",
                () -> keycloak.getAuthServerUrl() + "/realms/" + REALM_NAME);
    }

    static protected String getBearerToken(String username, String password) {
        try (Keycloak keycloakClient = KeycloakBuilder.builder()
                .serverUrl(keycloak.getAuthServerUrl())
                .realm(REALM_NAME)
                .clientId(CLIENT_ID)
                .username(username)
                .password(password)
                .build()) {

            String access_token = keycloakClient.tokenManager().getAccessToken().getToken();
            assertNotNull(access_token);

            return "Bearer " + access_token;
        } catch (Exception e) {
            fail("Can't obtain an access token from Keycloak!", e);
        }

        return null;
    }

}
