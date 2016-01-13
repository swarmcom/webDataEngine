package modules;

import api.config.ApiConfig;
import com.google.inject.Provides;
import api.config.ApiModule;
import controllers.Application;
import controllers.Users;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.guice.module.SpringModule;
import play.Configuration;
import play.Environment;
import play.Logger;

import java.util.List;

public class SpringAppModule extends ApiModule {

    private AnnotationConfigApplicationContext masterSpringContext;

    public SpringAppModule(Environment env, Configuration conf) {
        super(env, conf);
        try {
            List<String> configClassesStr = conf.getStringList("spring.configurations.enabled");
            masterSpringContext = ApiConfig.createSpringContext(configClassesStr);
            module = new SpringModule(masterSpringContext);
        } catch (Exception ex) {
            Logger.error("Cannot instantiate spring configurations", ex);
        }
    }

    @Provides
    Users providesUsersController() {
        return masterSpringContext.getBean(Users.class);
    }

    @Provides
    Application providesApplicationController() {
        return masterSpringContext.getBean(Application.class);
    }
}
