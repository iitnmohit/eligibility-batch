package com.futurerx.batch.b2termination.hook;

import com.futurerx.batch.core.constant.BatchType;
import com.futurerx.batch.core.service.IBatchApply;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static com.futurerx.batch.core.constant.BatchType.BATCH_ELIGIBILITY_TERMINATION;

public abstract class AbstractB2TerHook implements IBatchApply {

  @NonNull
  @Override
  public boolean apply(@Nullable BatchType batchType) {
    return BATCH_ELIGIBILITY_TERMINATION.equals(batchType);
  }
}
