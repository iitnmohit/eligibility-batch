package com.futurerx.batch.b1effective;

import com.futurerx.batch.b1effective.model.EffectiveBatchRequest;
import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.service.AbstractBatch;
import com.futurerx.batch.member.cache.CacheMemberEligibilityEffectiveDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

import static com.futurerx.batch.core.constant.BatchType.BATCH_ELIGIBILITY_EFFECTIVE;
import static com.futurerx.batch.core.constant.DataSourceType.SQL_DB;
import static com.futurerx.batch.core.constant.EnvironmentType.LOCAL;
import static java.time.LocalDate.now;
import static java.util.Objects.isNull;

@Component
@Profile("local")
public class BatchEffective extends AbstractBatch<EffectiveBatchRequest> {

  @Autowired
  private CacheMemberEligibilityEffectiveDateRepository
      cacheMemberEligibilityEffectiveDateRepository;

  @Override
  @EventListener(ApplicationReadyEvent.class)
  public void trigger() {
    this.accept(
        EffectiveBatchRequest.builder()
            .dataSourceType(SQL_DB)
            .environmentType(LOCAL)
            .effectiveDate(now())
            .counter(new AtomicLong(0L))
            .build());
  }

  @NonNull
  @Override
  protected Flux<EffectiveBatchRequest> expandRequest(@NonNull EffectiveBatchRequest request) {
    if (isNull(request.getEffectiveDate())) {
      return Flux.empty();
    }

    switch (request.getDataSourceType()) {
      case SQL_DB:
        return cacheMemberEligibilityEffectiveDateRepository
            .findFromRepository(request.getEffectiveDate())
            .map(request::cloneFromMemberEligibilityEntity);
      default:
        return Flux.empty();
    }
  }

  @NonNull
  @Override
  public BatchType getBatchType() {
    return BATCH_ELIGIBILITY_EFFECTIVE;
  }
}
