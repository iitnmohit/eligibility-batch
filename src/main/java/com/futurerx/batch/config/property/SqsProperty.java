package com.futurerx.batch.config.property;

import com.futurerx.batch.core.sqs.ISqsProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "aws.sqs")
public class SqsProperty {

  private Property effectiveBatch;
  private Property terminationBatch;

  @Getter
  @Setter
  public static final class Property implements ISqsProperty {
    private String queueRegion;
    private String queueName;
  }
}
