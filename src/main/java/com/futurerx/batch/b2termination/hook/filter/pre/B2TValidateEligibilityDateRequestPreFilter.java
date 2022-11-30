package com.futurerx.batch.b2termination.hook.filter.pre;

import com.futurerx.batch.b2termination.hook.AbstractB2TerHook;
import com.futurerx.batch.b2termination.model.TerminatedBatchRequest;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
public class B2TValidateEligibilityDateRequestPreFilter extends AbstractB2TerHook
    implements IRequestPreFilter<TerminatedBatchRequest> {

  public static final int PRIORITY = B2TValidateIdMemberInfoRequestPreFilter.PRIORITY - 2;

  @NonNull
  @Override
  public int getPriority() {
    return PRIORITY;
  }

  @NonNull
  @Override
  public boolean test(@NonNull TerminatedBatchRequest request) {
    return Optional.of(request)
        .map(TerminatedBatchRequest::getMemberEligibilityEntity)
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
