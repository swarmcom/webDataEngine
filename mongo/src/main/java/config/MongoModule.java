package config;

import config.AppModule;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.guice.module.SpringModule;
import play.Configuration;
import play.Environment;

public class MongoModule extends AppModule {
    SpringModule springModule;

    public MongoModule(Environment env, Configuration conf) {
        super(env, conf);
        springModule = new SpringModule(new AnnotationConfigApplicationContext(MongoConfig.class));
    }

    @Override
    protected void configure() {
        springModule.configure(binder());
    }
}
