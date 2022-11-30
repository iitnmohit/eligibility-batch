package com.futurerx.batch.b1effective.hook.filter.pre;

import com.futurerx.batch.b1effective.hook.AbstractB1EffHook;
import com.futurerx.batch.b1effective.model.EffectiveBatchRequest;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class B1EValidateEligibilityDateRequestPreFilter extends AbstractB1EffHook
    implements IRequestPreFilter<EffectiveBatchRequest> {

  public static final int PRIORITY = B1EValidateIdMemberInfoRequestPreFilter.PRIORITY - 2;

  @NonNull
  @Override
  public int getPriority() {
    return PRIORITY;
  }

  @NonNull
  @Override
  public boolean test(@NonNull EffectiveBatchRequest request) {
    return Optional.of(request)
        .map(EffectiveBatchRequest::getMemberEligibilityEntity)
        .filter(mee -> nonNull(mee.getEffectiveDate()))
        .map(this::isValidEligibility)
        .orElse(false);
  }

  @NonNull
  private boolean isValidEligibility(@NonNull MemberEligibilityEntity memberEligibilityEntity) {
    return isNull(memberEligibilityEntity.getTerminationDate())
        || memberEligibilityEntity
            .getEffectiveDate()
            .isBefore(memberEligibilityEntity.getTerminationDate());
  }
}
