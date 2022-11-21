package com.futurerx.batch.config.property;

import com.futurerx.batch.core.cache.ICacheProperty;
import com.futurerx.batch.core.cache.IRetryProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cache")
public class CacheProperty {

  private Property memberEligibilityEffectiveDate;
  private Property memberEligibilityTerminationDate;

  @Getter
  @Setter
  public static final class Property implements IRetryProperty, ICacheProperty {
    private Boolean enabled;
    private Long maxEntry;
    private Long expireSecond;
    private Long retryMaxAttempts;
    private Long retryMinBackoffMillis;
    private Long retryMaxBackoffMillis;
  }
}
