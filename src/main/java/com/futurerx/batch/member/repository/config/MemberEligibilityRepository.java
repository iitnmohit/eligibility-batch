package com.futurerx.batch.member.repository.config;

import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Repository
public interface MemberEligibilityRepository
    extends R2dbcRepository<MemberEligibilityEntity, Integer> {

  @NonNull
  Flux<MemberEligibilityEntity> findByEffectiveDate(@NonNull LocalDate effectiveDate);

  @NonNull
  Flux<MemberEligibilityEntity> findByTerminationDate(@NonNull LocalDate terminationDate);
}
