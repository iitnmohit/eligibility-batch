package com.futurerx.batch.b1effective.hook.filter.pre;

import com.futurerx.batch.b1effective.hook.AbstractB1EffHook;
import com.futurerx.batch.b1effective.model.EffectiveBatchRequest;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class B1EValidateIdMemberInfoRequestPreFilter extends AbstractB1EffHook
    implements IRequestPreFilter<EffectiveBatchRequest> {

  public static final int PRIORITY = 1000;

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
        .map(MemberEligibilityEntity::getIdMemberInfo)
        .isPresent();
  }
}
