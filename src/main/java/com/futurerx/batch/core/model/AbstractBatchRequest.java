package com.futurerx.batch.core.model;

import com.futurerx.batch.core.service.IValidator;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@SuperBuilder
@EqualsAndHashCode
public abstract class AbstractBatchRequest implements IValidator {

  private LocalDateTime startTime;
  private LocalDateTime endTime;
}
