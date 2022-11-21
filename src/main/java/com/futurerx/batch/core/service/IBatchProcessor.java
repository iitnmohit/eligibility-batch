package com.futurerx.batch.core.service;

import com.futurerx.batch.core.model.AbstractBatchRequest;

import java.util.function.Consumer;

public interface IBatchProcessor<R extends AbstractBatchRequest> extends Consumer<R>, IBatchApply {}
