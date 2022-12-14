package com.futurerx.batch.b2termination;

import com.futurerx.batch.b2termination.model.TerminatedBatchRequest;
import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.service.AbstractBatch;
import com.futurerx.batch.member.cache.CacheMemberEligibilityTerminationDateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

import static com.futurerx.batch.core.constant.BatchType.BATCH_ELIGIBILITY_TERMINATION;
import static com.futurerx.batch.core.constant.DataSourceType.SQL_DB;
import static com.futurerx.batch.core.constant.EnvironmentType.LOCAL;
import static java.time.LocalDate.now;
import static java.util.Objects.isNull;

@Component
@Profile("local")
public class BatchTerminationLocal extends AbstractBatch<TerminatedBatchRequest> {

  @Value("${batch.terminated-batch.terminated-date-offset:0}")
  private Integer terminatedDateOffset;

  @Autowired
  private CacheMemberEligibilityTerminationDateRepository
      cacheMemberEligibilityTerminationDateRepository;

  @Override
  @EventListener(ApplicationReadyEvent.class)
  public void trigger() {
    this.accept(
        TerminatedBatchRequest.builder()
            .dataSourceType(SQL_DB)
            .environmentType(LOCAL)
            .terminationDate(now().plusDays(terminatedDateOffset))
            .counter(new AtomicLong(0L))
            .build());
  }

  @NonNull
  @Override
  protected Flux<TerminatedBatchRequest> expandRequest(@NonNull TerminatedBatchRequest request) {
    if (!LOCAL.equals(request.getEnvironmentType())) {
      return Flux.empty();
    }

    if (isNull(request.getTerminationDate())) {
      return Flux.empty();
    }
    switch (request.getDataSourceType()) {
      case SQL_DB:
        return cacheMemberEligibilityTerminationDateRepository
            .findFromRepository(request.getTerminationDate())
            .map(request::cloneFromMemberEligibilityEntity);
      default:
        return Flux.empty();
    }
  }

  @NonNull
  @Override
  public BatchType getBatchType() {
    return BATCH_ELIGIBILITY_TERMINATION;
  }
}
