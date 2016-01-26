package mongo.config;

import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories("mongo.dao")
@ComponentScan("mongo.service")
public class MongoConfig extends AbstractMongoConfiguration {

    @Autowired
    ConfigurableEnvironment env;

    @Override
    protected String getDatabaseName() {
        return env.getProperty("dbname");
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(env.getProperty("uri")));
    }

    @Bean
    public DBCollection userCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("user");
    }

    @Bean
    public DBCollection roleCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("role");
    }

    @Bean
    public DBCollection accountCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("account");
    }

    @Bean
    public DBCollection phoneCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("phone");
    }
}
