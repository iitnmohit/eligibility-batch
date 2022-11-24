package com.futurerx.batch.b2termination.model;

import com.futurerx.batch.core.model.AbstractBatchRequest;
import com.futurerx.batch.member.entity.MemberEligibilityEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

@Setter
@Getter
@SuperBuilder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class TerminatedBatchRequest extends AbstractBatchRequest {

  private MemberEligibilityEntity memberEligibilityEntity;
  private LocalDate terminationDate;

  @NonNull
  public TerminatedBatchRequest cloneFromMemberEligibilityEntity(
      @NonNull MemberEligibilityEntity memberEligibilityEntity) {
    return TerminatedBatchRequest.builder()
        .memberEligibilityEntity(memberEligibilityEntity)
        .terminationDate(this.getTerminationDate())
        .startTime(this.getStartTime())
        .endTime(this.getEndTime())
        .dataSourceType(this.getDataSourceType())
        .environmentType(this.getEnvironmentType())
        .build();
  }
}
