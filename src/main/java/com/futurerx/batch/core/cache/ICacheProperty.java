package com.futurerx.batch.core.cache;

public interface ICacheProperty {
  Boolean DEFAULT_CACHE_ENABLED = true;
  Long DEFAULT_CACHE_MAX_ENTRY = 1000L;
  Long DEFAULT_CACHE_EXPIRE_SECOND = 900L;

  Boolean getEnabled();

  Long getExpireSecond();

  Long getMaxEntry();
}
