package api.config;

import api.domain.Account;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import play.Configuration;

import java.util.List;
import java.util.Map;

public abstract class ApiConfig {

    public static Configuration configuration;

    public static Map<String, AnnotationConfigApplicationContext> tenantSpringContextMap;

    public static AnnotationConfigApplicationContext createSpringContext(List<String> configClassesStr) throws Exception {
        Class[] configClasses = new Class[configClassesStr.size()];
        int i = 0;
        for (String configClassStr : configClassesStr) {
            configClasses[i++] = Class.forName(configClassStr);
        }
        AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext(configClasses);
        return springContext;
    }

    public static AnnotationConfigApplicationContext createSpringContext(Class configClass) throws Exception {
        AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext(configClass);
        return springContext;
    }

    public static AnnotationConfigApplicationContext createSpringContext(String configClassStr, ConfigurableEnvironment env) throws Exception {
        AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext();
        springContext.setEnvironment(env);
        springContext.register(Class.forName(configClassStr));
        springContext.refresh();
        return springContext;
    }

}
