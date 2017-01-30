package tenancy.config;

import api.config.ApiConfig;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import play.Logger;

@Configuration
@EnableMongoRepositories("tenancy.dao")
@ComponentScan("tenancy.service")
public class TenancyConfig extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return ApiConfig.configuration.getString("tenancy.dbname");
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new MongoClient(new MongoClientURI(ApiConfig.configuration.getString("tenancy.uri")));
    }

    @Bean
    public DBCollection accountCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("account");
    }

    @Bean
    public DBCollection providerCollection() throws Exception {
        return mongo().getDB(getDatabaseName()).getCollection("provider");
    }
}

