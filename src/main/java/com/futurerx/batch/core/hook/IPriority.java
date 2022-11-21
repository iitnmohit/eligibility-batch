package com.futurerx.batch.core.hook;

import org.springframework.lang.NonNull;

public interface IPriority extends Comparable<IPriority> {

  @NonNull
  default int getPriority() {
    return 1;
  }

  @NonNull
  default <P extends IPriority> int andPriority(@NonNull P priority1, @NonNull P priority2) {
    return Math.min(priority1.getPriority(), priority2.getPriority());
  }

  @Override
  default int compareTo(@NonNull IPriority iPriority) {
    return this.getPriority() - iPriority.getPriority();
  }
}
