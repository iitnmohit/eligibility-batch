package com.futurerx.batch.b1effective.hook;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.service.IBatchApply;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static com.futurerx.batch.core.constant.BatchType.BATCH_ELIGIBILITY_EFFECTIVE;

public abstract class AbstractB1EffHook implements IBatchApply {

  @NonNull
  @Override
  public boolean apply(@Nullable BatchType batchType) {
    return BATCH_ELIGIBILITY_EFFECTIVE.equals(batchType);
  }
}
