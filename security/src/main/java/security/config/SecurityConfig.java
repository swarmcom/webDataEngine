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
import org.pac4j.play.CallbackController;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import org.pac4j.play.store.PlayCacheStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import security.authorizer.GoogleSuperadminAuthorizer;

import security.util.EncoderUtil;
import security.validator.ProviderUsernamePasswordAuthenticator;
import security.validator.SecurityDigestAuthenticator;
import security.validator.AccountUsernamePasswordAuthenticator;

import javax.inject.Inject;


@Configuration
@ComponentScan("security.*")
public class SecurityConfig {

    @Inject
    private AccountUsernamePasswordAuthenticator usernamePasswordAuthenticator;

    @Inject
    private ProviderUsernamePasswordAuthenticator providerUsernamePasswordAuthenticator;

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
    public FormClient formClientProvider() {
        FormClient formClient = new FormClient("/loginformprovider", providerUsernamePasswordAuthenticator);
        formClient.setName("formClientProvider");
        return formClient;
    }

    @Bean
    public FormClient formClientAccount() {
        FormClient formClient = new FormClient("/loginformaccount", usernamePasswordAuthenticator);
        formClient.setName("formClientAccount");
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
        Clients clients = new Clients(baseUrl + "/callback", oidcClient(), formClientAccount(), formClientProvider(), basicClient(), digestClient());
        return clients;
    }

    @Bean
    public Config config() {
        final Config config = new Config(authClients());
        config.addAuthorizer("googleSuperadmin", new GoogleSuperadminAuthorizer());
        config.addAuthorizer("superadmin", new RequireAnyRoleAuthorizer<>("ROLE_SUPERADMIN"));
        config.addAuthorizer("admin", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN","ROLE_SUPERADMIN"));
        config.addAuthorizer("provider", new RequireAnyRoleAuthorizer<>("ROLE_PROVIDER"));
        config.addAuthorizer("user", new RequireAnyRoleAuthorizer<>("ROLE_ADMIN","ROLE_SUPERADMIN","ROLE_USER"));
        config.addAuthorizer("onlyUser", new RequireAnyRoleAuthorizer<>("ROLE_USER"));
        config.setHttpActionAdapter(new DefaultHttpActionAdapter());
        return config;
    }

    @Bean
    public AccountCallbackController accountCallbackController() {
        final AccountCallbackController callbackController = new AccountCallbackController();
        callbackController.setDefaultUrl("/owner/provider/account");
        callbackController.setMultiProfile(true);
        return callbackController;
    }

    @Bean
    public ProviderCallbackController providerCallbackController() {
        final ProviderCallbackController callbackController = new ProviderCallbackController();
        callbackController.setDefaultUrl("/owner/provider");
        callbackController.setMultiProfile(true);
        return callbackController;
    }

    @Bean
    public OwnerCallbackController ownerCallbackController() {
        final OwnerCallbackController ownerCallbackController = new OwnerCallbackController();
        ownerCallbackController.setDefaultUrl("/owner");
        ownerCallbackController.setMultiProfile(true);
        return ownerCallbackController;
    }

    @Bean
    public AccountLogoutController accountLogoutController() {
        final AccountLogoutController logoutController = new AccountLogoutController();
        logoutController.setDefaultUrl("/loginformaccount");
        return logoutController;
    }

    @Bean
    public ProviderLogoutController providerLogoutController() {
        final ProviderLogoutController logoutController = new ProviderLogoutController();
        logoutController.setDefaultUrl("/loginformprovider");
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
