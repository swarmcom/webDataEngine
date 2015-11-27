package modules;

import com.google.inject.Provides;
import config.AppModule;
import controllers.Users;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.guice.module.SpringModule;
import play.Configuration;
import play.Environment;
import play.Logger;

import java.util.List;

public class SpringAppModule extends AppModule {

    private AnnotationConfigApplicationContext springContext;

    public SpringAppModule(Environment env, Configuration conf) {
        super(env, conf);
        try {
            List<String> configClassesStr = conf.getStringList("spring.configurations.enabled");
            Class[] configClasses = new Class[configClassesStr.size()];
            int i = 0;
            for (String configClassStr : configClassesStr) {
                configClasses[i++] = Class.forName(configClassStr);
            }
            springContext = new AnnotationConfigApplicationContext(configClasses);
            module = new SpringModule(springContext);
        } catch (Exception ex) {
            Logger.error("Cannot instantiate spring configurations", ex);
        }
    }

    @Provides
    Users providesUsersController() {
        return springContext.getBean(Users.class);
    }
}
