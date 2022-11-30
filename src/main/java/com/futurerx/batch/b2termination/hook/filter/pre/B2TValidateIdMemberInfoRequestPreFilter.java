package com.futurerx.batch.b2termination.hook.filter.pre;

import com.futurerx.batch.b2termination.hook.AbstractB2TerHook;
import com.futurerx.batch.b2termination.model.TerminatedBatchRequest;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class B2TValidateIdMemberInfoRequestPreFilter extends AbstractB2TerHook
    implements IRequestPreFilter<TerminatedBatchRequest> {

  public static final int PRIORITY = 1000;

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
        .map(MemberEligibilityEntity::getIdMemberInfo)
        .isPresent();
  }
}
