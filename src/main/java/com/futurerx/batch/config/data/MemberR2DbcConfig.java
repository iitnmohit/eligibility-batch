package com.futurerx.batch.config.data;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.lang.NonNull;

@Configuration
@EnableR2dbcRepositories(
    basePackages = {"com.futurerx.batch.member.repository.config"},
    entityOperationsRef = "memberR2dbcEntityOperations")
public class MemberR2DbcConfig extends AbstractR2DbcConfig {

  @NonNull
  @Bean("memberSqlConnectionFactory")
  public ConnectionFactory memberSqlConnectionFactory() {
    return sqlConnectionFactory(r2dbcProperty.getMember());
  }

  @NonNull
  @Bean("memberR2dbcEntityOperations")
  public R2dbcEntityOperations memberR2dbcEntityOperations(
      @NonNull @Qualifier("memberSqlConnectionFactory") ConnectionFactory connectionFactory) {
    return super.r2dbcEntityOperations(connectionFactory);
  }
}
