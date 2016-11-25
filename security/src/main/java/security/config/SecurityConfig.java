package security.config;

import api.config.ApiConfig;
import com.nimbusds.jose.JWSAlgorithm;
import org.pac4j.core.authorization.authorizer.RequireAnyRoleAuthorizer;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.direct.DirectDigestAuthClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.oidc.client.GoogleOidcClient;
import org.pac4j.oidc.config.OidcConfiguration;
import org.pac4j.play.ApplicationLogoutController;
import org.pac4j.play.CallbackController;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import org.pac4j.play.store.PlayCacheStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import security.authorizer.GoogleSuperadminAuthorizer;

import security.util.EncoderUtil;
import security.validator.SecurityDigestAuthenticator;
import security.validator.SecurityUsernamePasswordAuthenticator;

import javax.inject.Inject;


@Configuration
@ComponentScan("security.*")
public class SecurityConfig {

    @Inject
    private SecurityUsernamePasswordAuthenticator usernamePasswordAuthenticator;

    @Inject
    private SecurityDigestAuthenticator digestAuthenticator;

    @Bean
    public GoogleOidcClient oidcClient() {
        final OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setClientId(ApiConfig.configuration.getString("oidc.clientId"));
        oidcConfiguration.setSecret(ApiConfig.configuration.getString("oidc.secret"));
        oidcConfiguration.setPreferredJwsAlgorithm(JWSAlgorithm.PS384);
        oidcConfiguration.addCustomParam("prompt", "consent");
        final GoogleOidcClient oidcClient = new GoogleOidcClient(oidcConfiguration);
        oidcClient.setName("OidcClient");
        oidcClient.setCallbackUrl(ApiConfig.configuration.getString("oidc.callback.url"));

        return oidcClient;
    }

    @Bean
    public FormClient formClient() {
        FormClient formClient = new FormClient("/loginform", usernamePasswordAuthenticator);
        return formClient;
    }

    @Bean
    public DirectBasicAuthClient basicClient() {
        DirectBasicAuthClient basicAuthClient = new DirectBasicAuthClient(usernamePasswordAuthenticator);
        return basicAuthClient;
    }

    @Bean
    public DirectDigestAuthClient digestClient() {
        DirectDigestAuthClient digestAuthClient = new DirectDigestAuthClient(digestAuthenticator);
        digestAuthClient.setRealm(EncoderUtil.DIGEST_REALM);
        return digestAuthClient;
    }

    @Bean
    public Clients authClients() {
        String baseUrl = ApiConfig.configuration.getString("baseUrl");
        Clients clients = new Clients(baseUrl + "/callback", oidcClient(), formClient(), basicClient(), digestClient());
        return clients;
    }

    @Bean
    public Config config() {
        final Config config = new Config(authClients());
        config.addAuthorizer("googleSuperadmin", new GoogleSuperadminAuthorizer());
        config.addAuthorizer("superadmin", new RequireAnyRoleAuthorizer<>("ROLE_SUPERADMIN"));
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN","ROLE_SUPERADMIN"));
        config.addAuthorizer("user", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN","ROLE_SUPERADMIN","ROLE_USER"));
        config.addAuthorizer("onlyUser", new RequireAnyRoleAuthorizer<>("ROLE_USER"));
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        return config;
    }

    @Bean
    public CallbackController callbackController() {
        final CallbackController callbackController = new CallbackController();
        callbackController.setDefaultUrl("/main/account");
        callbackController.setMultiProfile(true);
        return callbackController;
    }

    @Bean
    public ApplicationLogoutController applicationLogoutController() {
        final ApplicationLogoutController logoutController = new ApplicationLogoutController();
        logoutController.setDefaultUrl("/loginform");
        return logoutController;
    }

    @Bean
    public PlayCacheStore cacheStore() {
        return new PlayCacheStore();
    }

    @Bean
    public DefaultHttpActionAdapter httpActionAdapter() {
        return new DefaultHttpActionAdapter();
    }
}
