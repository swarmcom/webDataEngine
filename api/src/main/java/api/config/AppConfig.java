package api.config;

import org.springframework.context.annotation.ComponentScan;
import play.Configuration;

@org.springframework.context.annotation.Configuration
@ComponentScan("api.service")
public abstract class AppConfig {
    public static Configuration configuration;
}
