package com.futurerx.batch.b1effective.model;

import com.futurerx.batch.core.model.AbstractBatchRequest;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EffectiveBatchRequest extends AbstractBatchRequest {}
