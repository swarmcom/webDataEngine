package modules;

import api.config.ApiConfig;
import com.google.inject.Module;
import com.google.inject.Provides;
import api.config.ApiModule;
import controllers.Application;
import controllers.Users;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.guice.module.SpringModule;
import play.Configuration;
import play.Environment;
import play.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpringAppModule extends ApiModule {

    private AnnotationConfigApplicationContext masterSpringContext;
    private HashMap<String, AnnotationConfigApplicationContext> tenantSpringContextMap =
            new HashMap<String, AnnotationConfigApplicationContext>();

    public SpringAppModule(Environment env, Configuration conf) {
        super(env, conf);
        try {
            List<String> configClassesStr = conf.getStringList("spring.configurations.enabled");
            masterSpringContext = createSpringContext(configClassesStr);
            module = new SpringModule(masterSpringContext);
            List<String> tenantIdsStr = conf.getStringList("tenant.id");
            for (String tenantId : tenantIdsStr) {
                String tenantConfigClassStr = conf.getString(StringUtils.join(tenantId, '.', "spring.configurations.enabled"));
                List listStr = new ArrayList<String>();
                listStr.add(tenantConfigClassStr);
                AnnotationConfigApplicationContext tenantSpringContext = createSpringContext(listStr);
                tenantSpringContextMap.put(tenantId, tenantSpringContext);
            }
            ApiConfig.tenantSpringContextMap = tenantSpringContextMap;
        } catch (Exception ex) {
            Logger.error("Cannot instantiate spring configurations", ex);
        }
    }

    private AnnotationConfigApplicationContext createSpringContext(List<String> configClassesStr) throws Exception {
        Class[] configClasses = new Class[configClassesStr.size()];
        int i = 0;
        for (String configClassStr : configClassesStr) {
            configClasses[i++] = Class.forName(configClassStr);
        }
        AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext(configClasses);
        return springContext;
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
