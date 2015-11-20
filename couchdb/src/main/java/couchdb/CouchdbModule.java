package couchdb;

import config.AppModule;
import couchdb.CouchdbConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.guice.module.SpringModule;
import play.Configuration;
import play.Environment;

public class CouchdbModule extends AppModule {
    SpringModule springModule;

    public CouchdbModule(Environment env, Configuration conf) {
        super(env, conf);
        springModule = new SpringModule(new AnnotationConfigApplicationContext(CouchdbConfig.class));
    }

    @Override
    protected void configure() {
        springModule.configure(binder());
    }
}
