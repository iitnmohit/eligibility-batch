package com.futurerx.batch.config.data;

import com.futurerx.batch.config.property.R2dbcProperty;
import dev.miku.r2dbc.mysql.MySqlConnectionConfiguration;
import dev.miku.r2dbc.mysql.MySqlConnectionFactory;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.dialect.MySqlDialect;
import org.springframework.lang.NonNull;
import org.springframework.r2dbc.core.DatabaseClient;

public abstract class AbstractR2DbcConfig {

  @Autowired protected R2dbcProperty r2dbcProperty;

  @NonNull
  protected ConnectionFactory sqlConnectionFactory(@NonNull R2dbcProperty.Property property) {
    return MySqlConnectionFactory.from(
        MySqlConnectionConfiguration.builder()
            .host(property.getHost())
            .database(property.getDatabaseName())
            .user(property.getUser())
            .password(property.getPassword())
            .tcpKeepAlive(true)
            .tlsVersion("TLSv1.2")
            .build());
  }

  @NonNull
  protected R2dbcEntityOperations r2dbcEntityOperations(
      @NonNull ConnectionFactory connectionFactory) {
    return new R2dbcEntityTemplate(
        DatabaseClient.builder()
            .connectionFactory(connectionFactory)
            .bindMarkers(MySqlDialect.INSTANCE.getBindMarkersFactory())
            .build(),
        MySqlDialect.INSTANCE);
  }
}
