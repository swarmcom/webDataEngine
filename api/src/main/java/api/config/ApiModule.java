package api.config;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import play.Configuration;
import play.Environment;

public abstract class ApiModule extends AbstractModule {

    private Environment environment;

    private Configuration configuration;

    protected Module module;

    public ApiModule(Environment env, Configuration conf) {
        environment = env;
        configuration = conf;
        ApiConfig.configuration = configuration;
    }

    @Override
    protected void configure() {
        module.configure(binder());
    }
}
