package com.futurerx.batch.core.service;

import com.futurerx.batch.core.hook.request.error.IRequestErrorConsumer;
import com.futurerx.batch.core.hook.request.filter.IRequestPreFilter;
import com.futurerx.batch.core.hook.request.processor.IRequestPostProcessor;
import com.futurerx.batch.core.hook.request.processor.IRequestProcessor;
import com.futurerx.batch.core.model.AbstractBatchRequest;

import java.util.Optional;

public abstract class AbstractBatchBase<R extends AbstractBatchRequest> {

  protected Optional<IRequestPreFilter<R>> requestPreFilterOptional;

  protected Optional<IRequestProcessor<R>> requestProcessorOptional;

  protected Optional<IRequestPostProcessor<R>> requestPostProcessorOptional;

  protected Optional<IRequestErrorConsumer<R>> requestErrorConsumerOptional;
}
