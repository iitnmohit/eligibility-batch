package com.futurerx.batch.member.cache;

import com.futurerx.batch.core.cache.AbstractFluxCache;
import com.futurerx.batch.core.cache.ICacheProperty;
import com.futurerx.batch.core.cache.IRetryProperty;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import com.futurerx.batch.member.repository.config.MemberEligibilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

@Component
public class CacheMemberEligibilityTerminationDateRepository
    extends AbstractFluxCache<MemberEligibilityEntity, LocalDate> {

  @Autowired private MemberEligibilityRepository memberEligibilityRepository;

  public CacheMemberEligibilityTerminationDateRepository() {
    super(MemberEligibilityEntity.class);
  }

  @NonNull
  @Override
  public Flux<MemberEligibilityEntity> findFromRepository(@NonNull LocalDate terminationDate) {
    return memberEligibilityRepository.findByTerminationDate(terminationDate);
  }

  @Nullable
  @Override
  public ICacheProperty withCacheProperty() {
    return cacheProperty.getMemberEligibilityTerminationDate();
  }

  @Nullable
  @Override
  public IRetryProperty withRetryProperty() {
    return cacheProperty.getMemberEligibilityTerminationDate();
  }
}
