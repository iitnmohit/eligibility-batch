package com.futurerx.batch.config.property;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@EnableConfigurationProperties
@ConfigurationProperties("data.r2dbc")
public class R2dbcProperty {

  private Property member;

  @Getter
  @Setter
  @NoArgsConstructor
  public static final class Property {
    private String host;
    private String databaseName;
    private String user;
    private String password;
  }
}
