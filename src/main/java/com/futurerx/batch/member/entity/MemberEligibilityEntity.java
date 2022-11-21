package com.futurerx.batch.member.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table("mp_member_eligibility")
public final class MemberEligibilityEntity {

  @Id
  @Column("id_member_eligibility")
  private Integer idMemberEligibility;

  @Column("id_member_info")
  private Integer idMemberInfo;

  @Column("effective_date")
  private LocalDate effectiveDate;

  @Column("termination_date")
  private LocalDate terminationDate;
}
