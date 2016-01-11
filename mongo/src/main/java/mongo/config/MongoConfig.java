package mongo.config;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import api.config.ApiConfig;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@EnableMongoRepositories("mongo.dao")
@ComponentScan("mongo.service")
public class MongoConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return ApiConfig.configuration.getString("mongodb.dbname");
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(ApiConfig.configuration.getString("mongodb.uri")));
    }

    @Bean
    public DBCollection userCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("user");
    }

    @Bean
    public DBCollection roleCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("role");
    }
}
