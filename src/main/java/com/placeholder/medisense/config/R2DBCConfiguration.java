package com.placeholder.medisense.config;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

@Configuration
@EnableR2dbcRepositories
public class R2DBCConfiguration extends AbstractR2dbcConfiguration {

  private static final String dbUrl = "r2dbc:h2:file:///./testdb3;AUTO_SERVER=TRUE";

  @Bean
  public ConnectionFactory connectionFactory() {
    return ConnectionFactoryBuilder.withUrl(dbUrl).username("sa").build();
  }

  @Bean
  public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
    ConnectionFactoryInitializer connectionFactoryInitializer = new ConnectionFactoryInitializer();
    connectionFactoryInitializer.setConnectionFactory(connectionFactory);
    connectionFactoryInitializer.setDatabasePopulator(
        new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
    return connectionFactoryInitializer;
  }
}
