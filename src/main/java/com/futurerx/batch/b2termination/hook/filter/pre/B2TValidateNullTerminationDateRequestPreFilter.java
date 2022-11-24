package com.futurerx.batch.b2termination.hook.filter.pre;

import com.futurerx.batch.b1effective.model.EffectiveBatchRequest;
import com.futurerx.batch.b2termination.hook.AbstractB2TerHook;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
public class B2TValidateNullTerminationDateRequestPreFilter extends AbstractB2TerHook
    implements IRequestPreFilter<EffectiveBatchRequest> {

  public static final int PRIORITY = 1000;

  @Override
  public int getPriority() {
    return PRIORITY;
  }

  @Override
  public boolean test(@NonNull EffectiveBatchRequest request) {
    return Optional.of(request)
        .map(EffectiveBatchRequest::getMemberEligibilityEntity)
        .filter(mee -> nonNull(mee.getEffectiveDate()))
        .filter(mee -> nonNull(mee.getTerminationDate()))
        .map(this::isValidEligibility)
        .orElse(false);
  }

  @NonNull
  private boolean isValidEligibility(@NonNull MemberEligibilityEntity memberEligibilityEntity) {
    return memberEligibilityEntity
        .getEffectiveDate()
        .isBefore(memberEligibilityEntity.getTerminationDate());
  }
}
