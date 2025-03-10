package com.zerobase.SelfWash.administer_store.domain.entity;

import com.zerobase.SelfWash.member.domain.entity.BaseEntity;
import com.zerobase.SelfWash.administer_store.domain.form.MachineForm;
import com.zerobase.SelfWash.administer_store.domain.type.UsageStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Machine extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "store_id", nullable = false)
  private Store store;

  private String machineCompany;
  private String machineType;

  //사용현황
  private String usageStatus;
  //비고
  private String notes;
  //삭제예정데이터
  private boolean withdraw;


  public static Machine from(MachineForm form) {
    return Machine.builder()
        .machineType(form.getMachineType().name())
        .machineCompany(form.getMachineCompany().name())
        .notes(form.getNotes())
        .usageStatus(UsageStatus.USABLE.name())
        .withdraw(false)
        .build();
  }

}
