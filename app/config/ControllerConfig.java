package config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import controllers.Users;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"controllers", "managers"})
public class ControllerConfig {

}
