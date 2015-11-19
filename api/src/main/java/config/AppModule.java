package config;

import com.google.inject.AbstractModule;
import play.Configuration;
import play.Environment;


public abstract class AppModule extends AbstractModule{
    Environment environment;
    Configuration configuration;
    public AppModule(Environment env, Configuration conf) {
        environment = env;
        configuration = conf;
        AppConfig.configuration = configuration;
    }
}
