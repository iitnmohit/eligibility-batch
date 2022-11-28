package com.futurerx.batch.core.model;

import com.futurerx.batch.core.constant.DataSourceType;
import com.futurerx.batch.core.constant.EnvironmentType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractBatchRequest {

  private LocalDateTime startTime;
  private LocalDateTime endTime;

  private DataSourceType dataSourceType;
  private EnvironmentType environmentType;

  @NonNull
  public abstract AtomicLong getCounter();
}
