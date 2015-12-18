package security.config;

import api.config.AppConfig;
import org.pac4j.core.client.Client;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.client.indirect.FormClient;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.play.http.DefaultHttpActionAdapter;
import org.pac4j.play.http.HttpActionAdapter;
import org.pac4j.play.store.DataStore;
import org.pac4j.play.store.PlayCacheStore;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import security.providers.SecurityDaoAuthenticationProvider;
import security.providers.SecuritySessionAuthenticationProvider;
import security.service.SecuritySocialUserDetailsService;
import security.validator.SecurityUsernamePasswordAuthenticator;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@ComponentScan("security.*")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Inject
    SecurityDaoAuthenticationProvider daoProvider;

    @Inject
    private SecuritySocialUserDetailsService userDetailsService;

    @Inject
    private SecuritySessionAuthenticationProvider sessionProvider;

    @Bean
    public ClientAuthenticationProvider clientAuthenticationProvider() {
        ClientAuthenticationProvider provider = new ClientAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setClients(authClients());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        List<AuthenticationProvider> authenticationProviderList = new ArrayList<AuthenticationProvider>();
        authenticationProviderList.add(sessionProvider);
        authenticationProviderList.add(daoProvider);
        authenticationProviderList.add(clientAuthenticationProvider());
        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
        return providerManager;
    }

    @Bean
    public Client oidcClient() {
        final OidcClient oidcClient = new OidcClient();
        oidcClient.setClientID("172969963356-1134oecdv3fkc68v7k6pa4trm9o7hu8b.apps.googleusercontent.com");
        oidcClient.setSecret("4XAVEmpfMZhNhSDBbMGxz2RM");
        oidcClient.setDiscoveryURI("https://accounts.google.com/.well-known/openid-configuration");
        oidcClient.addCustomParam("prompt", "consent");

        return oidcClient;
    }

    @Bean
    public FormClient formClient() {
        FormClient formClient = new FormClient("/form", new SecurityUsernamePasswordAuthenticator());
        formClient.setCallbackUrl("/login");
        return formClient;
    }

    @Bean
    public DirectBasicAuthClient basicClient() {
        DirectBasicAuthClient basicAuthClient = new DirectBasicAuthClient(new SecurityUsernamePasswordAuthenticator());
        return basicAuthClient;
    }


    @Bean
    public Clients authClients() {
        String baseUrl = AppConfig.configuration.getString("baseUrl");
        Clients clients = new Clients(baseUrl + "/callback", oidcClient(), formClient(), basicClient());
        return clients;
    }

    @Bean
    public Config config() {
        Config config = new Config(authClients());
        return config;
    }

    @Bean
    public DataStore cacheStore() {
        return new PlayCacheStore();
    }

    @Bean
    public HttpActionAdapter httpActionAdapter() {
        return new DefaultHttpActionAdapter();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.parentAuthenticationManager(authenticationManager());
    }
}
