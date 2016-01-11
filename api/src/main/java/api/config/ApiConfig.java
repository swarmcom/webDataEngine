package api.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import play.Configuration;

import java.util.HashMap;

public abstract class ApiConfig {
    public static Configuration configuration;
    public static HashMap<String, AnnotationConfigApplicationContext> tenantSpringContextMap;
}
