package mongo.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import config.AppConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("mongo.dao")
@ComponentScan("mongo.service")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return AppConfig.configuration.getString("mongodb.dbname");
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(AppConfig.configuration.getString("mongodb.uri")));
    }
}
