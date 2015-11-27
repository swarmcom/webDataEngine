package config;

import com.google.inject.AbstractModule;
import com.google.inject.Module;
import play.Configuration;
import play.Environment;


public abstract class AppModule extends AbstractModule{

    private Environment environment;

    private Configuration configuration;

    protected Module module;

    public AppModule(Environment env, Configuration conf) {
        environment = env;
        configuration = conf;
        AppConfig.configuration = configuration;
    }

    @Override
    protected void configure() {
        module.configure(binder());
    }
}
